package progettoAI.snakeAI.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.AI.model.ActionRegister;
import progettoAI.snakeAI.AI.tools.Tools;
import progettoAI.snakeAI.errorHandler.ArithmeticException;

public abstract class AI {
	private ArrayList<Layer> layers;
	private TypeGradientUpdate mode;
	private transient double learningRate;
	
	public AI(Layer[] layers) {
		this.layers = Arrays.stream(layers).collect(Collectors.toCollection(ArrayList::new));
	}
	
	/**
	 * if only the length of the layers is indicated, by default the structure is composed of ReLu up to the penultimate layer, the last one uses SoftMax
	 * @param lenLayer
	 */
	public AI(int[] lenLayer) {
		try {
			layers = new ArrayList<Layer>(lenLayer.length);
			for(int i=1; i<lenLayer.length-1;i++) {//create layer with the corresponding weights and bias matrix dimension
				layers.add(new LayerReLu(lenLayer[i],lenLayer[i-1]));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public void resetLayer() {
		this.layers = null;
	}

	public ArrayList<Layer> getLayer() {
		return layers;
	}

	public void setLayer(ArrayList<Layer> layer) {
		this.layers = (ArrayList<Layer>) layer.clone();	
	}
	
	public TypeGradientUpdate getMode() {
		return mode;
	}

	public void setMode(TypeGradientUpdate mode) {
		this.mode = mode;
	}
	
	/**
	 * return how many input node the AI have
	 * @return
	 */
	public int getInputLenght() {
		return layers.get(0).getWeights().columns();
	}

	/**
	 * return activation of the last layer in base of a date state, it dons't save the activation of all layers
	 * @param input
	 * @return activation of the last layer
	 * @throws ArithmeticException 
	 */
	public double[] forwarding(double[] input) throws ArithmeticException {
		if(input == null)
			return null;
		return feedForwarding(Nd4j.create(input).reshape(input.length,1),0,false).toDoubleVector();
	}
	
	/**
	 * recursively goes through all the layers until the last one is activated, 
	 * if saveActivation is set to true it also saves all the intermediate activation layers, used in the backPropagation
	 * @param input  [numberOfNeurons,minibatchSize]
	 * @param i
	 * @param saveActivation
	 * @return NXM [numberOfNeurons,minibatchSize]
	 * @throws ArithmeticException 
	 */
	private INDArray feedForwarding(INDArray input,int i,boolean saveActivation) throws ArithmeticException {
		if(i>=layers.size()) {//if we are at the last layer, return result
			return input;
		}
		//continues forwarding on all layers
		if(saveActivation) {
			return this.feedForwarding(layers.get(i).forwardPass(input),i+1,saveActivation);//in this case it's also saving the intermediates activation
		}else {
			return this.feedForwarding(layers.get(i).forwarding(input),i+1,saveActivation);//in this case it's not saving the intermediates activation
		}
		
	}
	
	/**
	 * initializate internal variable for the backPropagation
	 */
	public void initBackPropagation() {
		this.getLayer().forEach(e ->{
			e.initBackProp();
		});
	}
	
	/**
	 * optimize the parameters
	 */
	public void optimization() {
		this.getLayer().forEach(e ->{
			e.optimization();
		});
	}
	
	/**
	 * perform a step in the backPropagation
	 * @param r
	 * @return mean loss [NX1]
	 * @throws ArithmeticException 
	 */
	public double backPropagation(ActionRegister[] r,long episode) throws ArithmeticException {
		if(r == null)
		return 0.0;
		INDArray tmpR = copyStateIntoINDArray(r,r.length);
		
		// perform the forwarding saving the intermediary state used for calculate the derivates
		INDArray newProb = this.feedForwarding(tmpR,0,true);
		// set the starting derivate from loss to activation
		double loss = lossCalculation(r,newProb,episode).meanNumber().doubleValue();
		
		INDArray dLdA = layers.get(layers.size()-1).backPropagation(this.derivateLoss(r,newProb,episode),this.getMode(),r.length,learningRate);
		for(int i=layers.size()-2; i>-1; i--){//perform the backPropagation for every layer
			dLdA = layers.get(i).backPropagation(dLdA,this.getMode(),r.length,learningRate);
		}
		
		return loss;
	}
	
	/**
	 * convert subArray in r state into a matrix (lengthState X minibatchSize)
	 * @param r
	 * @param minibatchSize
	 * @return matrix (lengthState X minibatchSize)
	 */
	private INDArray copyStateIntoINDArray(ActionRegister[] r,int minibatchSize) {
		if(r == null)
			return null;
		int stateLength = r[0].state.length;
		double[] tmp = new double[stateLength * minibatchSize];
		int offset = 0;
		
		for (ActionRegister register : r) {
		    System.arraycopy(register.state, 0, tmp, offset, stateLength);
		    offset += stateLength;
		}
		
		return Nd4j.create(tmp).reshape(stateLength,minibatchSize);
	}
	
	/**
	 * return the aggregate loss function
	 * @param r
	 * @param newProb [numberOut X minibatchSize]
	 * @return NXM [numberOut X minibatchSize]
	 */
	public INDArray lossCalculation(ActionRegister[] r,INDArray newProb,long episode) {
		if(r == null)
			return null;
		INDArray l = null;
		
		for(int i=0; i<r.length; i++) {
			l = Tools.appendCol(l, singleLossCalculation(r[i],newProb.getColumn(i),episode).reshape(newProb.getColumn(i).length(),1));
		}
		
		return l;
	}
	
	/**
	 * return the aggregate derivate loss function
	 * @param r
	 * @param newProb [numberOut X minibatchSize]
	 * @return NXM [numberOut X minibatchSize]
	 */
	public INDArray derivateLoss(ActionRegister[] r,INDArray newProb,long episode) {
		if(r == null)
			return null;
		int out = newProb.rows(); 
		int batch = newProb.columns();
		INDArray l = Nd4j.create(out, batch);

	    for (int i = 0; i < batch; i++) {
	        INDArray grad = singleDerivateLoss(r[i], newProb.getColumn(i), episode)
	                .mul(1.0 / (double) batch)
	                .reshape(out, 1);

	        l.putColumn(i, grad);
	    }
		return l;
	}
	
	/**
	 * perform a single loss calculation
	 * @param r
	 * @param newProb
	 * @return
	 */
	public abstract INDArray singleLossCalculation(ActionRegister r,INDArray newProb,long episode);
	
	/**
	 * perform a single derivate loss
	 * @param r
	 * @param newProb
	 * @return
	 */
	public abstract INDArray singleDerivateLoss(ActionRegister r,INDArray newProb,long episode);
	
}

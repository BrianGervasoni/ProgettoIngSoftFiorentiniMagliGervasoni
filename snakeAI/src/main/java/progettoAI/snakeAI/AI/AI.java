package progettoAI.snakeAI.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;


import model.ActionRegister;

public abstract class AI {
	private ArrayList<Layer> layers;
	private TypeGradientUpdate mode;
	
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
			layers.add(new LayerSoftMax(lenLayer[lenLayer.length-1],lenLayer[lenLayer.length-2]));//the last layer use softMax
		}catch(Exception e) {
			e.printStackTrace();
		}
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
	 * return activation of the last layer in base of a date state, it dons't save the activation of all layers
	 * @param input
	 * @return activation of the last layer
	 */
	public double[] forwarding(double[] input) {
		return feedForwarding(Nd4j.create(input),0,false).toDoubleVector();
	}
	
	/**
	 * recursively goes through all the layers until the last one is activated, 
	 * if saveActivation is set to true it also saves all the intermediate activation layers, used in the backPropagation
	 * @param input
	 * @param i
	 * @param saveActivation
	 * @return
	 */
	private INDArray feedForwarding(INDArray input,int i,boolean saveActivation) {
		if(i<layers.size()) {//if we are at the last layer, return result
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
	 * perform a step in the backPropagation
	 * @param r
	 */
	public void backPropagation(ActionRegister[] r) {
		INDArray tmpR = copyStateIntoINDArray(r,r.length);
		
		// perform the forwarding saving the intermediary state used for calculate the derivates
		INDArray newProb = this.feedForwarding(tmpR,0,true);
		
		
		// set the starting derivate from loss to activation
		INDArray dLdA = layers.get(layers.size()-1).backPropagation(this.derivateLoss(r,newProb),this.getMode());
		
		for(int i=layers.size()-2; i>=0; i--){//perform the backPropagation for every layer
				dLdA = layers.get(i).backPropagation(dLdA,this.getMode());
		}
	}
	
	/**
	 * convert subArray in r state into a matrix (lengthState X minibatchSize)
	 * @param r
	 * @param minibatchSize
	 * @return matrix (lengthState X minibatchSize)
	 */
	private INDArray copyStateIntoINDArray(ActionRegister[] r,int minibatchSize) {
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
	 * @param newProb
	 * @return
	 */
	public INDArray lossCalculation(ActionRegister[] r,INDArray newProb) {
		INDArray l = Nd4j.zeros(newProb.rows());
		
		for(int i=0; i<newProb.columns(); i++) {
			l.addi(singleLossCalculation(r[i],newProb.getRow(i)));
		}
		
		return l.mul(1/newProb.columns());
	}
	
	/**
	 * return the aggregate derivate loss function
	 * @param r
	 * @param newProb
	 * @return
	 */
	public INDArray derivateLoss(ActionRegister[] r,INDArray newProb) {
		INDArray l = Nd4j.zeros(newProb.rows());
		for(int i=0; i<newProb.columns(); i++) {
			l.addi(singleDerivateLoss(r[i],newProb.getRow(i)));
		}
		
		return l.mul(1/newProb.columns());
	}
	
	/**
	 * perform a single loss calculation
	 * @param r
	 * @param newProb
	 * @return
	 */
	public abstract INDArray singleLossCalculation(ActionRegister r,INDArray newProb);
	
	/**
	 * perform a single derivate loss
	 * @param r
	 * @param newProb
	 * @return
	 */
	public abstract INDArray singleDerivateLoss(ActionRegister r,INDArray newProb);
	
}

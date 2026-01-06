package progettoAI.snakeAI.AI;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.activations.*;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.common.primitives.Pair;


import progettoAI.snakeAI.tools.Tools;
import progettoAI.snakeAI.hyperparameters.*;
import errorHandler.ArithmeticException;


public abstract class Layer {
	/*private RealVector bias;
	private RealVector tmpBias;
	private RealVector preActivation;
	private RealVector activation;
	private RealVector derivateFromLossToBias;
	private RealMatrix weights;
	private RealMatrix tmpWeights;
	private RealMatrix derivateFromLossToWeights;
	private RealVector derivateFromLossToActivation;*/
	private INDArray bias;
	private INDArray weights;
	
	private transient INDArray tmpBias;
	private transient INDArray tmpWeights;
	
	private transient INDArray backLayerActivation_cache;
	private transient INDArray preActivation_cache;
	
	private transient INDArray cumulativeDLdW;
	private transient INDArray cumulativeDLdB;
	
	private IActivation activation;
	
	/**
	 * W [output X input]
	 * @param bias [numberOfNodeInThisLayer]
	 * @param weights [numberOfNodeInThisLayer, numberOfNodeInTheBackLayer]
	 */
	public Layer(double[] bias , double[][] weights) {
		/*this.bias = MatrixUtils.createRealVector(bias);
		this.weights = MatrixUtils.createRealMatrix(weights);*/
		this.bias = Nd4j.create(bias).reshape(bias.length,1);//NX1
		this.weights = Nd4j.create(weights);//NXK
		this.cumulativeDLdW = Nd4j.zerosLike(this.weights);
        this.cumulativeDLdW = Nd4j.zerosLike(this.bias);
	}
	
	/**
	 * create layer with the specificate number of node and random value for bias and weights from -10 to 10
	 * W [output X input]
	 * @param lenLayer
	 * @param lenNextLayer
	 */
	public Layer(int lenLayer, int lenBackLayer) {
		double [] tmpBias = new double[lenLayer];
		double[][] tmpWeights = new double[lenLayer][lenBackLayer];
		for(int i=0;i<lenLayer;i++) {
			tmpBias[i] = Tools.pickRandom(-0.1, 0.1);
			for(int j=0;j<lenBackLayer;j++) {
				tmpWeights[i][j] = Tools.pickRandom(-0.1, 0.1);
			}
		}
		
		this.bias = Nd4j.create(tmpBias).reshape(tmpBias.length,1);//NX1
		this.weights = Nd4j.create(tmpWeights);//NXK
		this.cumulativeDLdW = Nd4j.zerosLike(this.weights);
        this.cumulativeDLdW = Nd4j.zerosLike(this.bias);
	}
	
	

	public INDArray getBias() {
		return bias;
	}

	public void setBias(INDArray bias) {
		this.bias = bias.dup();
	}

	public INDArray getWeights() {
		return weights;
	}

	public void setWeights(INDArray weights) {
		this.weights = weights.dup();
	}
	
	
	
	public INDArray getTmpBias() {
		return tmpBias;
	}

	public void setTmpBias(INDArray tmpBias) {
		this.tmpBias = tmpBias.dup();
	}

	public INDArray getTmpWeights() {
		return tmpWeights;
	}

	public void setTmpWeights(INDArray tmpWeights) {
		this.tmpWeights = tmpWeights.dup();
	}

	public INDArray getBackLayerActivation_cache() {
		return backLayerActivation_cache;
	}

	public void setBackLayerActivation_cache(INDArray backLayerActivation_cache) {
		this.backLayerActivation_cache = backLayerActivation_cache.dup();
	}

	public INDArray getPreActivation_cache() {
		return preActivation_cache;
	}

	public void setPreActivation_cache(INDArray preActivation_cache) {
		this.preActivation_cache = preActivation_cache.dup();
	}

	public INDArray getCumulativeDLdW() {
		return cumulativeDLdW;
	}

	public void setCumulativeDLdW(INDArray cumulativeDLdW) {
		this.cumulativeDLdW = cumulativeDLdW.dup();
	}

	public INDArray getCumulativeDLdB() {
		return cumulativeDLdB;
	}

	public void setCumulativeDLdB(INDArray cumulativeDLdB) {
		this.cumulativeDLdB = cumulativeDLdB.dup();;
	}

	public IActivation getActivation() {
		return activation;
	}

	public void setActivation(IActivation activation) {
		this.activation = activation;
	}

	/**
	 * calculate the activation function of this layer from the previus layer activation
	 * @param backLayerActivation: INDArray with the back layer activation value
	 * @param saveActivation: specificate if the layer save the intermediary values, usend during backPropagation
	 * @return INDArray with this layer activation value
	 * @throws ArithmeticException 
	 */
	public INDArray forwarding(INDArray backLayerActivation) throws ArithmeticException {
		//((NXK) * (KX1)) + (NX1) = (NX1) but the activation function need (1XN) so we do the transpose
		try {
			return this.getActivation().getActivation(this.getWeights().mmul(backLayerActivation).add(this.getBias()).transpose(), false).transpose();//sigma(W*A+B)
		}catch(Exception e) {
			throw new ArithmeticException(e.getMessage(),e.getCause());
		}
		
	}
	
	/**
	 * Performs Forward Propagation for an input minibatch
	 * @param backLayerActivation: The input minibatch [backLayerActivationSize, BatchSize]
	 * @return activation of this layer for the entire minibatch
	 * @throws ArithmeticException 
	 */
	public INDArray forwardPass(INDArray backLayerActivation) throws ArithmeticException {
		try {
			this.setBackLayerActivation_cache(backLayerActivation);//KXM
		
			//((NXK) * (KXM)) + (NX1) = (NXM) use broadcasting for the bias
			this.setPreActivation_cache(this.getWeights().mmul(backLayerActivation).add(this.getBias()));//W*A+B
			
			//the activation need (MXN) so we do the transpose. Duplicate the array because we don't want it to change
			return this.getActivation().getActivation(this.getPreActivation_cache().transpose().dup(), true).transpose();
		}catch(Exception e) {
			throw new ArithmeticException(e.getMessage(),e.getCause());
		}
		
	}
	
	/**
	 * calculate derivates from loss to parameters, add the cumulative derivates for the next stochastic calculus
	 * @param dLdA
	 * @param mode
	 * @return dLdA
	 * @throws ArithmeticException 
	 */
	public INDArray derivateCalculus(INDArray dLdA,TypeGradientUpdate mode,int minibatchSize) throws ArithmeticException {
		 try {
			  if (dLdA.isNaN().any()) {
		        System.err.println("INSTABILITA RILEVATA: alcune derivate sono NaN.");
			 }
			//first term dL/dZ, second term dL/dW in respect to the activation
			 Pair<INDArray, INDArray> gradientPair = this.activation.backprop(this.getPreActivation_cache().transpose(), dLdA.transpose());
			
			 INDArray dLdZ = gradientPair.getFirst().transpose();
			 
			 //(NXM) * (MXK) = (NXK)
			 INDArray dLdW = dLdZ.mmul(this.getBackLayerActivation_cache().transpose());
			 
			this.tmpOptimization(dLdW,dLdZ.sum(1).reshape(dLdZ.rows(),1),mode,minibatchSize);//si prende solo una riga per il dLdB dal dLdZ (NX1)
			 // (KXN) * (NXM) = (KXM) 
			return this.getWeights().transpose().mmul(dLdZ);
		 }catch(Exception e) {
				throw new ArithmeticException(e.getMessage(),e.getCause());
			}
		
	}
	
	/**
	 * perform a step in the backPropagation phase
	 * @param dLdA
	 * @param mode
	 * @param minibatchSize
	 * @return dLdA
	 * @throws ArithmeticException 
	 */
	public INDArray backPropagation(INDArray dLdA,TypeGradientUpdate mode,int minibatchSize) throws ArithmeticException {
		return this.derivateCalculus(dLdA, mode,minibatchSize);
	}
	
	/**
	 * initialize the copy parameters to the true value of the parameters
	 */
	public void initBackProp() {
		this.setTmpWeights(this.getWeights());
		this.setTmpBias(this.getBias());
	}
	
	/**
	 * optimize the parameters without changing the true value
	 * @param dLdW
	 * @param dLdB
	 * @param mode
	 * @throws ArithmeticException 
	 */
	public void tmpOptimization(INDArray dLdW,INDArray dLdB,TypeGradientUpdate mode,int minibatchSize) throws ArithmeticException {
		try {
			switch(mode){//add change to the tmpParameters
			case ASCEND:
				this.getTmpWeights().addi(dLdW.mul(Hyperparameters.alphaW * (1/(double) minibatchSize)));
				this.getTmpBias().addi(dLdB.mul(Hyperparameters.alphaB * (1/(double) minibatchSize)));
				break;
			case DESCEND:
				this.getTmpWeights().subi(dLdW.mul(Hyperparameters.alphaW * (1/(double) minibatchSize)));
				this.getTmpBias().subi(dLdB.mul(Hyperparameters.alphaB * (1/(double) minibatchSize)));
				break;
				default:
					break;
			}
		}catch(Exception e) {
			throw new ArithmeticException(e.getMessage(),e.getCause());
		}
		
	}
	
	/**
	 * change the parameters to the optimizated one
	 */
	public void optimization() {
		this.setWeights(this.getTmpWeights());
		this.setBias(this.getTmpBias());
	}
}

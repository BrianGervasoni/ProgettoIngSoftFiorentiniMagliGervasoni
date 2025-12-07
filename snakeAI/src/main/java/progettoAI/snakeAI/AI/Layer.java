package progettoAI.snakeAI.AI;

import org.apache.commons.math3.linear.*;

import progettoAI.snakeAI.tools.Tools;
import progettoAI.snakeAI.hyperparameters.*;


public abstract class Layer {
	private RealVector bias;
	private RealVector tmpBias;
	private RealVector preActivation;
	private RealVector activation;
	private RealVector derivateFromLossToBias;
	private RealMatrix weights;
	private RealMatrix tmpWeights;
	private RealMatrix derivateFromLossToWeights;
	private RealVector derivateFromLossToActivation;
	
	public Layer(double[] bias , double[][] weights) {
		this.bias = MatrixUtils.createRealVector(bias);
		this.weights = MatrixUtils.createRealMatrix(weights);
	}
	
	/**
	 * create layer with the specificate number of node and random value for bias and weights from -10 to 10
	 * @param lenLayer
	 * @param lenNextLayer
	 */
	public Layer(int lenLayer, int lenBackLayer) {
		double [] tmpBias = new double[lenLayer];
		double[][] tmpWeights = new double[lenLayer][lenBackLayer];
		for(int i=0;i<lenLayer;i++) {
			tmpBias[i] = Tools.pickRandom(-10, 10);
			for(int j=0;j<lenBackLayer;j++) {
				tmpWeights[i][j] = Tools.pickRandom(-10, 10);
			}
		}
		
		this.bias = MatrixUtils.createRealVector(tmpBias);
		this.weights = MatrixUtils.createRealMatrix(tmpWeights);
	}

	public RealVector getBias() {
		return bias;
	}

	public void setBias(RealVector bias) {
		this.bias = bias.copy();
	}

	public RealVector getPreActivation() {
		return preActivation;
	}

	public void setPreActivation(RealVector preActivation) {
		this.preActivation = preActivation.copy();
	}

	public RealVector getActivation() {
		return activation;
	}

	public void setActivation(RealVector activation) {
		this.activation = activation.copy();
	}

	public RealVector getDerivateFromLossToBias() {
		return derivateFromLossToBias;
	}

	public void setDerivateFromLossToBias(RealVector derivateFromLossToBias) {
		this.derivateFromLossToBias = derivateFromLossToBias.copy();
	}

	public RealMatrix getWeights() {
		return weights;
	}

	public void setWeights(RealMatrix weights) {
		this.weights = weights.copy();
	}

	public RealMatrix getDerivateFromLossToWeights() {
		return derivateFromLossToWeights;
	}

	public void setDerivateFromLossToWeights(RealMatrix derivateFromLossToWeights) {
		this.derivateFromLossToWeights = derivateFromLossToWeights.copy();
	}

	public RealVector getDerivateFromLossToActivation() {
		return derivateFromLossToActivation;
	}

	public void setDerivateFromLossToActivation(RealVector derivateFromLossToActivation) {
		this.derivateFromLossToActivation = derivateFromLossToActivation.copy();
	}

	public RealVector getTmpBias() {
		return tmpBias;
	}

	public void setTmpBias(RealVector tmpBias) {
		this.tmpBias = tmpBias.copy();
	}

	public RealMatrix getTmpWeights() {
		return tmpWeights;
	}

	public void setTmpWeights(RealMatrix tmpWeights) {
		this.tmpWeights = tmpWeights.copy();
	}

	/**
	 * calculate the activation function of this layer from the previus layer activation
	 * @param backLayerActivation: RealVector with the back layer activation value
	 * @return RealVector with this layer activation value
	 */
	public RealVector forwarding(RealVector backLayerActivation) {
		return this.activationCalculus(this.getWeights().preMultiply(backLayerActivation).add(this.bias));//sigma(W*A+B)
	}
	
	/**
	 * this method perform the same actions as the basic forwarding method but it memorize the preActivation and the activation
	 * @param backLayerActivation: RealVector with the back layer activation value
	 * @return RealVector with this layer activation value
	 */
	public RealVector backForwarding(RealVector backLayerActivation) {
		this.setPreActivation(this.getWeights().preMultiply(backLayerActivation).add(this.bias));
		this.setActivation(this.activationCalculus(this.getPreActivation()));
		return this.getActivation();//sigma(W*A+B)
	}
	
	/**
	 * get the derivate of the pre activation function in respect of the weights
	 * @return
	 */
	private RealVector derivateFromPreActivationToWeights() {
		return activation;
	}
	
	/**
	 * get the derivate of the pre activation function in respect to the activation of the below layer
	 * @return
	 */
	private RealMatrix derivateFromPreActivationToActivation() {
		return this.getWeights();
	}
	
	/**
	 * calculate derivates from loss to parameters, add the cumulative derivates for the next stochastic calculus
	 * @param backLayer
	 */
	public void derivateCalculus() {
		RealMatrix tmpDAct = this.derivateFromLossToPreActivation();
		this.derivateFromLossToWeights.add(MatrixUtils.createColumnRealMatrix(this.derivateFromPreActivationToWeights().toArray()).multiply(tmpDAct));
		
		this.derivateFromLossToBias.add(tmpDAct.operate(this.getDerivateFromLossToActivation()));//calculus derivate from loss to bias
	}
	
	/**
	 * calculate the derivate from loss to activation of the below layer
	 * @param backLayer
	 */
	public void derivateFromLossToActivationCalculus(Layer backLayer) {
		backLayer.setDerivateFromLossToActivation(
				this.derivateFromPreActivationToActivation().operate(
						this.derivateFromLossToPreActivation().operate(this.getDerivateFromLossToActivation())));
	}
	
	/**
	 * initialize the final derivate Weights and Bias to 0 and set the tmp parameters to the actual value
	 */
	public void initBackPropagation() {
		this.derivateReset();
		this.setTmpBias(this.getBias());
		this.setTmpWeights(this.getWeights());
	}
	
	/**
	 * perform a step in the backPropagation phase
	 * @param backLayer
	 */
	public void backPropagation(Layer backLayer) {
		this.derivateCalculus();
		if(backLayer != null)
			this.derivateFromLossToActivationCalculus(backLayer);
	}
	
	/**
	 * change the value of the weights and bias to the optimized one
	 */
	public void optimization() {
		this.setWeights(tmpWeights.copy());
		this.setBias(tmpBias.copy());
	}
	
	/**
	 * optimizes weight and bias parameters based on the selected mode, it dosn't change the true value used for the forwarding,
	 * only the method @Layer.optimization change the true value of weights and bias
	 * @param mode (ASCEND,DESCEND)
	 * @param minibatchSize
	 */
	public void tmpOptimization(TypeGradientUpdate mode,int minibacthSize) {
		switch(mode){//add change to the tmpParameters
		case ASCEND:
			this.setTmpWeights(this.getTmpWeights().add(this.getDerivateFromLossToWeights().scalarMultiply(Hyperparameters.alphaW).scalarMultiply(1/minibacthSize)));
			this.setTmpBias(this.getTmpBias().add(this.getDerivateFromLossToBias().mapMultiply(Hyperparameters.alphaB).mapMultiplyToSelf(1/minibacthSize)));
			break;
		case DESCEND:
			this.setTmpWeights(this.getTmpWeights().subtract(this.getDerivateFromLossToWeights().scalarMultiply(Hyperparameters.alphaW).scalarMultiply(1/minibacthSize)));
			this.setTmpBias(this.getTmpBias().subtract(this.getDerivateFromLossToBias().mapMultiplyToSelf(Hyperparameters.alphaB).mapMultiplyToSelf(1/minibacthSize)));
			break;
			default:
				break;
		}
		this.derivateReset();
	}
	
	private void derivateReset() {
		this.setDerivateFromLossToWeights(new BlockRealMatrix(this.getWeights().getRowDimension(),this.getWeights().getColumnDimension()));//reset derivates to 0
		this.setDerivateFromLossToBias(new ArrayRealVector(this.getBias().getDimension()));
	}
	
	/**
	 * calculate activation from the preActivation
	 * @param preActivation
	 * @return activation
	 */
	public abstract RealVector activationCalculus(RealVector preActivation);
	
	/**
	 * calculate derivate of the Loss function to the preActivation
	 * @return derivate matrix (1XN)
	 */
	public abstract RealMatrix derivateFromLossToPreActivation();
}

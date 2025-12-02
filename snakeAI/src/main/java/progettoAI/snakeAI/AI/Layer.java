package progettoAI.snakeAI.AI;

import java.util.ArrayList;
import org.apache.commons.math3.linear.*;

import progettoAI.snakeAI.tools.Tools;
import progettoAI.snakeAI.hyperparameters.*;


public abstract class Layer {
	private RealVector bias;
	private RealVector preActivation;
	private RealVector activation;
	private RealVector derivateFromLossToBias;
	private RealMatrix weights;
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
		this.bias = bias;
	}

	public RealVector getPreActivation() {
		return preActivation;
	}

	public void setPreActivation(RealVector preActivation) {
		this.preActivation = preActivation;
	}

	public RealVector getActivation() {
		return activation;
	}

	public void setActivation(RealVector activation) {
		this.activation = activation;
	}

	public RealVector getDerivateFromLossToBias() {
		return derivateFromLossToBias;
	}

	public void setDerivateFromLossToBias(RealVector derivateFromLossToBias) {
		this.derivateFromLossToBias = derivateFromLossToBias;
	}

	public RealMatrix getWeights() {
		return weights;
	}

	public void setWeights(RealMatrix weights) {
		this.weights = weights;
	}

	public RealMatrix getDerivateFromLossToWeights() {
		return derivateFromLossToWeights;
	}

	public void setDerivateFromLossToWeights(RealMatrix derivateFromLossToWeights) {
		this.derivateFromLossToWeights = derivateFromLossToWeights;
	}

	public RealVector getDerivateFromLossToActivation() {
		return derivateFromLossToActivation;
	}

	public void setDerivateFromLossToActivation(RealVector derivateFromLossToActivation) {
		this.derivateFromLossToActivation = derivateFromLossToActivation;
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
	public RealMatrix derivateFromPreActivationToWeightsCalculus() {
		return Tools.createMatrixFromVector(activation, this.getWeights().getRowDimension());
	}
	
	/**
	 * get the derivate of the pre activation function in respect to the activation of the below layer
	 * @return
	 */
	public RealMatrix derivateFromPreActivationToActivation() {
		return this.getWeights();
	}
	
	/**
	 * calculate derivates from loss to parameters, add the cumulative derivates for the next stochastic calculus
	 * @param backLayer
	 */
	public void derivateCalculus() {
		RealVector tmpDAct = this.derivateFromActivationToPreActivation();
		RealMatrix tmp = Tools.outerProduct(tmpDAct, this.getDerivateFromLossToActivation());//calculus derivate from loss to weigths
		this.derivateFromLossToWeights.add(this.derivateFromPreActivationToWeightsCalculus().multiply(tmp));
		
		this.derivateFromLossToBias.add(tmpDAct.ebeMultiply(this.getDerivateFromLossToActivation()));//calculus derivate from loss to bias
	}
	
	/**
	 * calculate the derivate from loss to activation of the below layer
	 * @param backLayer
	 */
	public void derivateFromLossToActivationCalculus(Layer backLayer) {//TODO
		backLayer.setDerivateFromLossToActivation(
				this.derivateFromPreActivationToActivation().operate(
						this.derivateFromActivationToPreActivation().ebeMultiply(this.getDerivateFromLossToActivation())));
	}
	
	/**
	 * initializzate the final derivate Weights and Bias to 0
	 */
	public void initBackPropagation() {
		this.setDerivateFromLossToWeights(new BlockRealMatrix(this.getWeights().getRowDimension(),this.getWeights().getColumnDimension()));
		this.setDerivateFromLossToBias(new ArrayRealVector(this.getBias().getDimension()));
	}
	/**
	 * optimizes weight and bias parameters based on the selected mode
	 * @param mode
	 */
	public void optimization(TypeGradientUpdate mode) {
		switch(mode){
		case ASCEND:
			this.setWeights(this.getWeights().add(this.getDerivateFromLossToWeights().scalarMultiply(Hyperparameters.alphaW)));
			this.setBias(this.getBias().add(this.getDerivateFromLossToBias().mapMultiply(Hyperparameters.alphaB)));
			break;
		case DESCEND:
			this.setWeights(this.getWeights().subtract(this.getDerivateFromLossToWeights().scalarMultiply(Hyperparameters.alphaW)));
			this.setBias(this.getBias().subtract(this.getDerivateFromLossToBias().mapMultiply(Hyperparameters.alphaB)));
			break;
			default:
				break;
		}
	}
	
	public abstract RealVector activationCalculus(RealVector preActivation);
	public abstract RealVector derivateFromActivationToPreActivation();
}

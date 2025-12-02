package progettoAI.snakeAI.AI;

import java.util.ArrayList;
import org.apache.commons.math3.linear.*;

import progettoAI.snakeAI.tools.Tools;


public abstract class Layer {
	private RealVector bias;
	private RealVector preActivation;
	private RealVector activation;
	private RealVector derivatoFromLossToBias;
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

	public RealVector getDerivatoFromLossToBias() {
		return derivatoFromLossToBias;
	}

	public void setDerivatoFromLossToBias(RealVector derivatoFromLossToBias) {
		this.derivatoFromLossToBias = derivatoFromLossToBias;
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
		RealMatrix tmp = Tools.outerProduct(tmpDAct, this.derivateFromLossToActivation);//calculus derivate from loss to weigths
		this.derivateFromLossToWeights.add(this.derivateFromPreActivationToWeightsCalculus().multiply(tmp));
		
		this.derivatoFromLossToBias.add(tmpDAct.ebeMultiply(this.derivateFromLossToActivation));//calculus derivate from loss to bias
	}
	
	public void derivateFromLossToActivationCalculus() {//TODO
		
	}
	
	public abstract RealVector activationCalculus(RealVector preActivation);
	public abstract RealVector derivateFromActivationToPreActivation();
}

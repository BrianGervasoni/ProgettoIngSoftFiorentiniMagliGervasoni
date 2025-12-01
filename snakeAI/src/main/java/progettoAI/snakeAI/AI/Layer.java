package progettoAI.snakeAI.AI;

import java.util.ArrayList;
import org.apache.commons.math3.linear.*;

import progettoAI.snakeAI.tools.Tools;


public abstract class Layer {
	private RealVector bias;
	private RealVector activation;
	private RealVector derivateActivation;
	private RealMatrix weights;
	private RealMatrix derivateWeights;
	
	public Layer(double[] bias , double[][] weights) {
		this.bias = MatrixUtils.createRealVector(bias);
		this.weights = MatrixUtils.createRealMatrix(weights);
	}
	
	/**
	 * create layer whit the specificate number of node and random value for bias and weights from -10 to 10
	 * @param lenLayer
	 * @param lenNextLayer
	 */
	public Layer(int lenLayer, int lenNextLayer) {
		double [] tmpBias = new double[lenLayer];
		double[][] tmpWeights = new double[lenNextLayer][lenLayer];
		for(int i=0;i<lenLayer;i++) {
			tmpBias[i] = Tools.pickRandom(-10, 10);
			for(int j=0;j<lenNextLayer;j++) {
				tmpWeights[j][i] = Tools.pickRandom(-10, 10);
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

	public RealVector getActivation() {
		return activation;
	}

	public void setActivation(RealVector activation) {
		this.activation = activation;
	}

	public RealVector getDerivateActivation() {
		return derivateActivation;
	}

	public void setDerivateActivation(RealVector derivateActivation) {
		this.derivateActivation = derivateActivation;
	}

	public RealMatrix getWeights() {
		return weights;
	}

	public void setWeights(RealMatrix weights) {
		this.weights = weights;
	}

	public RealMatrix getDerivateWeights() {
		return derivateWeights;
	}

	public void setDerivateWeights(RealMatrix derivateWeights) {
		this.derivateWeights = derivateWeights;
	}

	/**
	 * calculate the activation function of this layer from the previus layer
	 * @param backLayer
	 * @return
	 */
	public RealVector ActivationCalculus(Layer backLayer) {
		return backLayer.getWeights().preMultiply(backLayer.activation).add(this.bias);
	}
	
	
}

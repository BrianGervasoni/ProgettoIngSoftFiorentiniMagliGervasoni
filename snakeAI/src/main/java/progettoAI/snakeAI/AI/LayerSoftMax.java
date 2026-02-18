package progettoAI.snakeAI.AI;

import org.nd4j.linalg.activations.impl.ActivationSoftmax;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.errorHandler.ArithmeticException;


public class LayerSoftMax extends Layer {
	public LayerSoftMax(double[] bias, double[][] weights) {
		super(bias, weights);
		this.setActivation(new ActivationSoftmax());
	}

	public LayerSoftMax(int lenLayer, int lenBackLayer) {
		super(lenLayer, lenBackLayer);
		this.setActivation(new ActivationSoftmax());
	}
}

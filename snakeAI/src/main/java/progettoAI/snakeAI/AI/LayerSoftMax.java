package progettoAI.snakeAI.AI;

import org.nd4j.linalg.activations.impl.ActivationSoftmax;


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

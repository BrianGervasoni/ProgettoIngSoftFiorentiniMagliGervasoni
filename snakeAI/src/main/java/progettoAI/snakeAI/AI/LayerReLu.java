package progettoAI.snakeAI.AI;

import org.nd4j.linalg.activations.impl.ActivationLReLU;

public class LayerReLu extends Layer {

	public LayerReLu(double[] bias, double[][] weights) {
		super(bias, weights);
		this.setActivation(new ActivationLReLU());
		
	}

	public LayerReLu(int lenLayer, int lenNextLayer) {
		super(lenLayer, lenNextLayer);
		this.setActivation(new ActivationLReLU());
	}

}

package progettoAI.snakeAI.AI;

import org.nd4j.linalg.activations.impl.ActivationReLU;

public class LayerReLu extends Layer {

	public LayerReLu(double[] bias, double[][] weights) {
		super(bias, weights);
		this.setActivation(new ActivationReLU());
		
	}

	public LayerReLu(int lenLayer, int lenNextLayer) {
		super(lenLayer, lenNextLayer);
		this.setActivation(new ActivationReLU());
	}

}

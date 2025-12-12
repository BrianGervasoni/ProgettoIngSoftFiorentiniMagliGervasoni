package progettoAI.snakeAI.AI;

import org.nd4j.linalg.activations.impl.ActivationIdentity;

public class LayerIdentityFunction extends Layer {

	public LayerIdentityFunction(double[] bias, double[][] weights) {
		super(bias, weights);
		this.setActivation(new ActivationIdentity());
	}

	public LayerIdentityFunction(int lenLayer, int lenBackLayer) {
		super(lenLayer, lenBackLayer);
		this.setActivation(new ActivationIdentity());
	}
}

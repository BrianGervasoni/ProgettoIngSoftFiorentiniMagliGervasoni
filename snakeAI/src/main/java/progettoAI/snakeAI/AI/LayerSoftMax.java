package progettoAI.snakeAI.AI;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.util.FastMath;
import org.nd4j.linalg.activations.impl.ActivationIdentity;
import org.nd4j.linalg.activations.impl.ActivationSoftmax;

import progettoAI.snakeAI.tools.Tools;

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

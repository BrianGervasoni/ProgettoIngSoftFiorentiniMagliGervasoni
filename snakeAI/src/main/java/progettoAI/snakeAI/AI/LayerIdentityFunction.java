package progettoAI.snakeAI.AI;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class LayerIdentityFunction extends Layer {

	public LayerIdentityFunction(double[] bias, double[][] weights) {
		super(bias, weights);
		// TODO Auto-generated constructor stub
	}

	public LayerIdentityFunction(int lenLayer, int lenBackLayer) {
		super(lenLayer, lenBackLayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RealVector activationCalculus(RealVector preActivation) {
		return preActivation;
	}

	@Override
	public RealMatrix derivateFromLossToPreActivation() {
		return MatrixUtils.createRowRealMatrix(this.getDerivateFromLossToActivation().ebeMultiply(this.getPreActivation()).toArray());
	}

}

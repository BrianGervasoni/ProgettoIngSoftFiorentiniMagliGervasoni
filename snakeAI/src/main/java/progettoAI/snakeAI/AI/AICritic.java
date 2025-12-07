package progettoAI.snakeAI.AI;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import model.ActionRegister;

public class AICritic extends AI {

	public AICritic(Layer[] layers,TypeGradientUpdate mode) {
		super(layers);
		this.setMode(mode);
	}

	public AICritic(int[] lenLayer,TypeGradientUpdate mode) {
		super(lenLayer);
		this.setMode(mode);
	}

	@Override
	public RealVector lossCalculation(ActionRegister r, double[] newProb) {
		RealVector x = new ArrayRealVector();
		return x.append(Math.pow(r.vEstimated-r.vTarget, 2));
	}

	@Override
	public RealVector derivateLoss(ActionRegister r, double[] newProb) {
		RealVector x = new ArrayRealVector();
		return x.append(2*(r.vEstimated-r.vTarget));
	}

}

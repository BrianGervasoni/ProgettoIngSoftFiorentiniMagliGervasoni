package progettoAI.snakeAI.AI;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

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
	public INDArray singleLossCalculation(ActionRegister r,INDArray newProb) {
		RealVector x = new ArrayRealVector();
		return Nd4j.create(x.append(Math.pow(r.vEstimated-r.vTarget, 2)).toArray());
	}
	
	@Override
	public INDArray singleDerivateLoss(ActionRegister r,INDArray newProb) {
		RealVector x = new ArrayRealVector();
		return Nd4j.create(x.append(2*(r.vEstimated-r.vTarget)).toArray());
	}

}

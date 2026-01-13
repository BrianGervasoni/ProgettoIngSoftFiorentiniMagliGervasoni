package progettoAI.snakeAI.AI;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import model.ActionRegister;
import progettoAI.snakeAI.hyperparameters.Hyperparameters;

public class AICritic extends AI {

	public AICritic(Layer[] layers,TypeGradientUpdate mode) {
		super(layers);
		this.setLearningRate(Hyperparameters.alphaCritic);
		this.setMode(mode);
	}

	public AICritic(int[] lenLayer,TypeGradientUpdate mode) {
		super(lenLayer);
		try {
			this.getLayer().add(new LayerIdentityFunction(lenLayer[lenLayer.length-1],lenLayer[lenLayer.length-2]));//the last layer use softMax
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.setLearningRate(Hyperparameters.alphaCritic);
		this.setMode(mode);
	}
	
	@Override
	public INDArray singleLossCalculation(ActionRegister r,INDArray newProb) {
		double[] x = new double[] {Math.pow(r.vEstimated-r.vTarget, 2)};
		return Nd4j.create(x);
	}
	
	@Override
	public INDArray singleDerivateLoss(ActionRegister r,INDArray newProb) {
		double diff = r.vEstimated - r.vTarget;
		if (diff > 1.0) diff = 1.0;
		if (diff < -1.0) diff = -1.0;
		double[] x = new double[] {2*diff};
		return Nd4j.create(x);
	}

}

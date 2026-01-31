package progettoAI.snakeAI.AI;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.AI.hyperparameters.Hyperparameters;
import progettoAI.snakeAI.AI.model.ActionRegister;

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
	public INDArray singleLossCalculation(ActionRegister r,INDArray newProb,long episode) {
		double[] x = new double[] {Math.pow(r.vTarget - r.vEstimated, 2)};
		return Nd4j.create(x);
	}
	
	@Override
	public INDArray singleDerivateLoss(ActionRegister r,INDArray newProb,long episode) {
		 double diff = r.vEstimated - r.vTarget;
		    
		    double delta = 1.0; // Huber threshold
		    double derivative;
		    
		    if (Math.abs(diff) <= delta) {
		        derivative = 2 * diff; // MSE Behavior
		    } else {
		        derivative = 2 * delta * Math.signum(diff); // Linear behavior for large errors
		    }
		return Nd4j.create(new double[] {derivative});
	}

}

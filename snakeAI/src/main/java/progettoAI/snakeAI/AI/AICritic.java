package progettoAI.snakeAI.AI;

import java.util.ArrayList;

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
		try {
			this.setLayer( new ArrayList<Layer>(lenLayer.length));
			for(int i=1; i<lenLayer.length-1;i++) {//create layer with the corresponding weights and bias matrix dimension
				this.getLayer().add(new LayerReLu(lenLayer[i],lenLayer[i-1]));
			}
			this.getLayer().add(new LayerIdentityFunction(lenLayer[lenLayer.length-1],lenLayer[lenLayer.length-2]));//the last layer use softMax
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.setMode(mode);
	}
	
	@Override
	public INDArray singleLossCalculation(ActionRegister r,INDArray newProb) {
		double[] x = new double[] {Math.pow(r.vEstimated-r.vTarget, 2)};
		return Nd4j.create(x);
	}
	
	@Override
	public INDArray singleDerivateLoss(ActionRegister r,INDArray newProb) {
		double[] x = new double[] {2*(r.vEstimated-r.vTarget)};
		return Nd4j.create(x);
	}

}

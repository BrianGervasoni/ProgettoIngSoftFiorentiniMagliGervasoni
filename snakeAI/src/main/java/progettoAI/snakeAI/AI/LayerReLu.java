package progettoAI.snakeAI.AI;

import org.apache.commons.math3.linear.*;

import progettoAI.snakeAI.tools.Tools;

public class LayerReLu extends Layer {

	public LayerReLu(double[] bias, double[][] weights) {
		super(bias, weights);
		
	}

	public LayerReLu(int lenLayer, int lenNextLayer) {
		super(lenLayer, lenNextLayer);
		
	}
	
	/**
	 * set the activation of the neuron with the ReLu function, pick the max between 0 and the preActivation of the neuron
	 */
	@Override
	public RealVector activationCalculus(RealVector preActivation) {
		RealVector activation = preActivation.copy();
		for(int i=0; i<activation.getDimension(); i++) {
			activation.setEntry(i, Math.max(activation.getEntry(i), 0.0));
		}
		return activation;
	}

	@Override
	public RealMatrix derivateFromLossToPreActivation() {
		RealVector tmp = this.getActivation().copy();
		for(int i=0; i<this.getPreActivation().getDimension(); i++) {
			
			if(tmp.getEntry(i) > 0) {
				tmp.setEntry(i, 1);
			}else {
				tmp.setEntry(i, 0);
			}
			
		}
		return MatrixUtils.createRowRealMatrix(this.getDerivateFromLossToActivation().ebeMultiply(tmp).toArray());
	}

}

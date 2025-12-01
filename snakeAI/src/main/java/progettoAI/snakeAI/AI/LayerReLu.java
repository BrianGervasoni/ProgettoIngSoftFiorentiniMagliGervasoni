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
	public void activationCalculus() {
		this.setActivation(this.getPreActivation().copy());
		for(int i=0; i<this.getPreActivation().getDimension(); i++) {
			this.getActivation().setEntry(i, Math.max(this.getActivation().getEntry(i), 0.0));
		}
	}

	/**
	 * get the derivate froma activation to pre activation, 1 if the activation is greater to 0, 0 if it's below
	 */
	@Override
	public RealVector derivateFromActivationToPreActivation() {
		RealVector tmp = this.getActivation().copy();
		for(int i=0; i<this.getPreActivation().getDimension(); i++) {
			
			if(tmp.getEntry(i) > 0) {
				tmp.setEntry(i, 1);
			}else {
				tmp.setEntry(i, 0);
			}
			
		}
		return tmp;
	}

}

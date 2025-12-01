package progettoAI.snakeAI.AI;

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
	 * set the derivate froma activation to pre activation, 1 if the activation is greaten to 0, 0 if it's below
	 */
	@Override
	public void derivateFromActivationToPreActivation() {
		this.setDerivatoFromActivationToPreActivation(this.getActivation());
		for(int i=0; i<this.getPreActivation().getDimension(); i++) {
			
			if(this.getDerivatoFromActivationToPreActivation().getEntry(i) > 0) {
				this.getDerivatoFromActivationToPreActivation().setEntry(i, 1);
			}else {
				this.getDerivatoFromActivationToPreActivation().setEntry(i, 0);
			}
			
		}
	}

}

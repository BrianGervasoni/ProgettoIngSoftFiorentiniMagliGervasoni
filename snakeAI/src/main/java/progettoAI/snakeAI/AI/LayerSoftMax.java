package progettoAI.snakeAI.AI;

import org.nd4j.linalg.activations.impl.ActivationSoftmax;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.errorHandler.ArithmeticException;


public class LayerSoftMax extends Layer {

	/*private INDArray noise_cache;
	private double sigma = 0.1;*/
	
	public LayerSoftMax(double[] bias, double[][] weights) {
		super(bias, weights);
		this.setActivation(new ActivationSoftmax());
	}

	public LayerSoftMax(int lenLayer, int lenBackLayer) {
		super(lenLayer, lenBackLayer);
		this.setActivation(new ActivationSoftmax());
	}
	/*
	@Override
	public void initBackProp() {
		super.initBackProp();
		this.noise_cache = null;
	}
	
	@Override
	public INDArray forwardPass(INDArray backLayerActivation) throws ArithmeticException {
		try {
			this.setBackLayerActivation_cache(backLayerActivation);//KXM
			// 1. Calcolo Pre-Attivazione Lineare (NXM)
	        INDArray z = this.getWeights().mmul(backLayerActivation).add(this.getBias());
	        
	        if(this.noise_cache == null)
	        	this.noise_cache = Nd4j.randn(z.shape()).muli(sigma);
            z.addi(this.noise_cache);
            
	        //((NXK) * (KXM)) + (NX1) = (NXM) use broadcasting for the bias
	        INDArray z_nd4j = z.transpose();// [batch, features]
	        this.setPreActivation_cache(z_nd4j);
	        //the activation need (MXN) so we do the transpose	
	        INDArray a_nd4j = this.getActivation().getActivation(z_nd4j, true);

	        return a_nd4j.transpose();
		}catch(Exception e) {
			throw new ArithmeticException(e.getMessage(),e.getCause());
		}
	}*/
}

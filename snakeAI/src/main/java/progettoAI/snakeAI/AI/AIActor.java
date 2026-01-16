package progettoAI.snakeAI.AI;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.tools.Tools;
import progettoAI.snakeAI.hyperparameters.*;
import progettoAI.snakeAI.model.ActionRegister;

public class AIActor extends AI {

	public AIActor(Layer[] layers,TypeGradientUpdate mode) {
		super(layers);
		this.setLearningRate(Hyperparameters.alphaActor);
		this.setMode(mode);
	}

	public AIActor(int[] lenLayer,TypeGradientUpdate mode) {
		super(lenLayer);
		try {
			this.getLayer().add(new LayerSoftMax(lenLayer[lenLayer.length-1],lenLayer[lenLayer.length-2]));//the last layer use softMax
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.setLearningRate(Hyperparameters.alphaActor);
		this.setMode(mode);
	}
	
	@Override
	public INDArray singleLossCalculation(ActionRegister r,INDArray newProb,long episode) {
		if(r == null)
			return null;
		double[] probs = newProb.toDoubleVector();
	    RealVector loss = new ArrayRealVector(probs.length);

	    // PPO clip loss only to the selected action
	    loss.addToEntry(r.indexAction, lossClip(r, probs));

	    // Entropy bonus
	    double h = entropy(probs) * entropyDecay(episode);
	    for (int i = 0; i < probs.length; i++) {
	        loss.addToEntry(i, Hyperparameters.entropyContribution * h / probs.length);
	    }
		return Nd4j.create(loss.toArray());
	}
	
	@Override
	public INDArray singleDerivateLoss(ActionRegister r,INDArray newProb,long episode) {
		if(r == null)
			return null;
		double[] probs = newProb.toDoubleVector();
	    RealVector grad = new ArrayRealVector(probs.length);

	    // Gradiente PPO (choose action)
	    grad.addToEntry(r.indexAction, derivateLossClip(r, probs));

	    // Gradiente entropy (all actions)
	    RealVector entropyGrad = derivateEntropy(probs,episode)
	        .mapMultiply(Hyperparameters.entropyContribution);

	    grad = grad.add(entropyGrad);

		return Nd4j.create(grad.toArray());
	}
	
	private double entropyDecay(long episode) {
		return Math.max(0.002, 0.01 * Math.exp(-episode / 3000.0));
	}
	
	/**
	 * calculate how much likely an action have change
	 * @param newProb
	 * @param oldProb
	 * @return
	 */
	private double policyRatio(double newProb, double oldLogProb) {
	    return Math.exp(Math.log(newProb + 1e-8) - oldLogProb);
	}
	
	/**
	 * calculate the entropy of all actions
	 * @param probs
	 * @return
	 */
	private double entropy(double[] probs) {
		double x = 0.0;
	    for (double p : probs) {
	        x += -p * Math.log(p + 1e-8);
	    }
	    return x;
	}
	/**
	 * calculate the derivate of the entropy term
	 * @param probs
	 * @return
	 */
	private RealVector derivateEntropy(double[] probs,long episode) {
		if(probs == null)
			return null;
		RealVector x= new ArrayRealVector(probs.length);
		for(int i=0; i < probs.length; i++) {
			x.setEntry(i, -probs[i] * entropyDecay(episode));
		}
		return x;
	}
	
	/**
	 * calculate the clip term of the loss function
	 * @param r
	 * @param newProb
	 * @return
	 */
	private double lossClip(ActionRegister r, double[] newProb) {

	    double ratio = policyRatio(newProb[r.indexAction], Math.log(r.oldSelectAction()+ 1e-8));
	    double clipped = Tools.clip(
	        ratio,
	        1 - Hyperparameters.motivation,
	        1 + Hyperparameters.motivation
	    );

	    return Math.min(ratio * r.advantage, clipped * r.advantage);
	}
	
	/**
	 * calculate the derivate of the loss clip
	 * @param r
	 * @param newProb
	 * @return
	 */
	private double derivateLossClip(ActionRegister r, double[] newProb) {
		if(newProb == null || r == null)
			return 0;
		if(r.advantage > 0) {
			if(policyRatio(newProb[r.indexAction],r.oldSelectAction()) > 1+Hyperparameters.motivation) {
				return 0;
			}
				
		}else {
			if(policyRatio(newProb[r.indexAction],r.oldSelectAction()) < 1-Hyperparameters.motivation) {
				return 0;
			}
		}
		
		return r.advantage * policyRatio(newProb[r.indexAction],r.oldSelectAction());
	}
		
}

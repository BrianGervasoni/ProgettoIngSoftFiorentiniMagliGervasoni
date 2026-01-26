package progettoAI.snakeAI.AI;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.tools.Tools;
import progettoAI.snakeAI.hyperparameters.*;
import progettoAI.snakeAI.model.ActionRegister;

public class AIActor extends AI {

	private transient double entropyLoss=0;
	
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
	    entropyLoss = h;
	    for (int i = 0; i < probs.length; i++) {
	        loss.addToEntry(i,h);
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
	    RealVector entropyGrad = derivateEntropy(probs)
	        .mapMultiply(entropyDecay(episode));

	    grad = grad.add(entropyGrad);

		return Nd4j.create(grad.toArray());
	}
	
	public double getEntropyLoss() {
		return entropyLoss;
	}

	public void setEntropyLoss(double entropyLoss) {
		this.entropyLoss = entropyLoss;
	}

	private double entropyDecay(long episode) {
		return Math.max(0.005, Hyperparameters.entropyContribution * Math.exp(-episode / 1500.0));
	}
	
	/**
	 * calculate how much likely an action have change
	 * @param newProb
	 * @param oldProb
	 * @return
	 */
	private double policyRatio(double newProb, double oldProb) {
		return (newProb + 1e-8) / (oldProb + 1e-8);
	}
	
	private double derivatePolicyRatio(double oldProb) {
		return 1 / (oldProb + 1e-8);
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
	private RealVector derivateEntropy(double[] probs) {
		if(probs == null)
			return null;
		RealVector x= new ArrayRealVector(probs.length);
		for(int i=0; i < probs.length; i++) {
			x.setEntry(i, -(Math.log(probs[i] + 1e-8) + 1));
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

	    double ratio = policyRatio(newProb[r.indexAction], r.oldSelectAction());
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
		double ratio = policyRatio(newProb[r.indexAction], r.oldSelectAction());
		double clipped = Tools.clip(ratio, 1-Hyperparameters.motivation, 1+Hyperparameters.motivation);

		if (r.advantage > 0 && ratio > clipped) return 0;
	    if (r.advantage < 0 && ratio < clipped) return 0;
		
		return r.advantage * derivatePolicyRatio(r.oldSelectAction());
	}
		
}

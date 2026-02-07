package progettoAI.snakeAI.AI;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.AI.hyperparameters.*;
import progettoAI.snakeAI.AI.model.ActionRegister;
import progettoAI.snakeAI.AI.tools.Tools;

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

	    // Gradiente PPO (choose action)
	    INDArray grad = derivateLossClip(r, newProb.toDoubleVector());

	    // Gradiente entropy (all actions)
	   INDArray entropyGrad = derivateEntropy(probs)
	        .mul(entropyDecay(episode));

	    grad = grad.add(entropyGrad);

		return grad;
	}
	
	public double getEntropyLoss() {
		return entropyLoss;
	}

	public void setEntropyLoss(double entropyLoss) {
		this.entropyLoss = entropyLoss;
	}

	private double entropyDecay(long episode) {
		return Math.max(0.001, Hyperparameters.entropyContribution * Math.exp(-episode / 3000.0));
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
	private INDArray derivateEntropy(double[] probs) {
		if(probs == null)
			return null;
		double[] x= new double[probs.length];
		for(int i=0; i < probs.length; i++) {
			x[i] =   -(Math.log(probs[i] + 1e-8) + 1);
		}
		return Nd4j.create(x);
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
	private INDArray derivateLossClip(ActionRegister r, double[] newProb) {
		if(newProb == null || r == null)
			return null;
		INDArray grad = Nd4j.zeros(newProb.length);
		double ratio = policyRatio(newProb[r.indexAction], r.oldSelectAction());
		double clipped = Tools.clip(ratio, 1-Hyperparameters.motivation, 1+Hyperparameters.motivation);

		if (r.advantage > 0 && ratio > clipped) return grad;
	    if (r.advantage < 0 && ratio < clipped) return grad;
	    
	    for (int i = 0; i < newProb.length; i++) {
	        double g = -newProb[i];
	        if (i == r.indexAction) {
	            g += 1.0;
	        }
	        grad.putScalar(i, g);
	    }

	    grad.muli(r.advantage);
	    return grad;
	}
		
}

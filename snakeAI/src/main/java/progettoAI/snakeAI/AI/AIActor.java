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
	public INDArray singleLossCalculation(ActionRegister r,INDArray newProb) {
		if(r == null)
			return null;
		RealVector l = entropy(newProb.toDoubleVector()).mapMultiply(Hyperparameters.entropyContribution);
		l.addToEntry(r.indexAction, lossClip(r,newProb.toDoubleVector()));
		return Nd4j.create(l.toArray());
	}
	
	@Override
	public INDArray singleDerivateLoss(ActionRegister r,INDArray newProb) {
		if(r == null)
			return null;
		RealVector l = derivateEntropy(newProb.toDoubleVector()).mapMultiply(Hyperparameters.entropyContribution);
		l.addToEntry(r.indexAction, derivateLossClip(r,newProb.toDoubleVector()));
		return Nd4j.create(l.toArray());
	}
	
	/**
	 * calculate how much likely an action have change
	 * @param newProb
	 * @param oldProb
	 * @return
	 */
	private double policyRatio(double newProb, double oldProb) {
		final double epsilon = 1e-8; // for avoid n/0 problem
	    return newProb / (oldProb + epsilon);
	}
	
	/**
	 * calculate the derivate of the policy ratio
	 * @param oldProb
	 * @return
	 */
	private double derivatePolicyRatio(double oldProb) {
		final double epsilon = 1e-8;// for avoid n/0 problem
		return 1/(oldProb+epsilon);
	}
	
	/**
	 * calculate the entropy of all actions
	 * @param probs
	 * @return
	 */
	private RealVector entropy(double[] probs) {
		if(probs == null)
			return null;
		RealVector x= new ArrayRealVector(probs.length);
		for(int i=0; i < probs.length; i++) {
			x.addToEntry(i, -probs[i]*Math.log(probs[i]+ 1e-10));
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
			x.addToEntry(i, -(Math.log(probs[i]+ 1e-10)+1));
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
		if(r == null)
			return 0;
		return Math.min(policyRatio(newProb[r.indexAction],r.oldSelectAction()) * r.advantage,
				Tools.clip(policyRatio(newProb[r.indexAction],r.oldSelectAction()),1-Hyperparameters.motivation,1+Hyperparameters.motivation) 
				* r.advantage);
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
		
		return r.advantage * derivatePolicyRatio(r.oldSelectAction());
	}
		
}

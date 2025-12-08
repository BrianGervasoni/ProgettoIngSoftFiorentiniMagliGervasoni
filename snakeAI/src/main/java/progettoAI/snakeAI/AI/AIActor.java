package progettoAI.snakeAI.AI;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import model.ActionRegister;
import progettoAI.snakeAI.tools.Tools;
import progettoAI.snakeAI.hyperparameters.*;

public class AIActor extends AI {

	public AIActor(Layer[] layers,TypeGradientUpdate mode) {
		super(layers);
		this.setMode(mode);
	}

	public AIActor(int[] lenLayer,TypeGradientUpdate mode) {
		super(lenLayer);
		this.setMode(mode);
	}
	
	@Override
	public INDArray singleLossCalculation(ActionRegister r,INDArray newProb) {
		RealVector l = entropy(newProb.toDoubleVector()).mapMultiply(Hyperparameters.entropyContribution);
		l.addToEntry(r.indexAction, lossClip(r,newProb.toDoubleVector()));
		return Nd4j.create(l.toArray());
	}
	
	@Override
	public INDArray singleDerivateLoss(ActionRegister r,INDArray newProb) {
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
		RealVector x= new ArrayRealVector(probs.length);
		for(int i=0; i < probs.length; i++) {
			x.addToEntry(i, -probs[i]*Math.log(probs[i]));
		}
		return x;
	}
	/**
	 * calculate the derivate of the entropy term
	 * @param probs
	 * @return
	 */
	private RealVector derivateEntropy(double[] probs) {
		RealVector x= new ArrayRealVector(probs.length);
		for(int i=0; i < probs.length; i++) {
			x.addToEntry(i, -(Math.log(probs[i])+1));
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

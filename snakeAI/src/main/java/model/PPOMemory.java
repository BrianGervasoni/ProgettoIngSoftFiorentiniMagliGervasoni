package model;

import java.util.ArrayList;
import java.util.Collections;

import progettoAI.snakeAI.hyperparameters.Hyperparameters;

public class PPOMemory {
	private ArrayList<ActionRegister> oldR;
	private ArrayList<ActionRegister> currR;
	
	public PPOMemory() {
		oldR = new ArrayList<ActionRegister>();
		currR = new ArrayList<ActionRegister>();
	}
	
	public boolean haveData() {
		if(currR.isEmpty())
			return false;
		return true;
	}
	
	public void addNewActions(ActionRegister []actions) {
		scaleRewards(actions);
		processActions(actions);
		normalizeAdvantages(actions);
		Collections.addAll(currR,actions);
	}
	
	/**
	 * process the actions to obtain the vTarget and advantage of the batch data
	 * @param actions
	 */
	private void processActions(ActionRegister[] actions) {
	    double gae = 0;
	    double discount = Hyperparameters.discount;
	    double lambda = Hyperparameters.lambda;

	    for (int i = actions.length - 1; i >= 0; i--) {
	        
	        // Determine if there is a future value.
	    	// If the agent died at this step, the next life value is 0.
	        double nextValue;
	        double nextNonTerminal; // Mask for the GAE

	        if (actions[i].isTerminal) {
	            nextValue = 0;
	            nextNonTerminal = 0; // Reset GAE accumulation
	        } else if (i + 1 < actions.length) {
	            nextValue = actions[i + 1].vEstimated;
	            nextNonTerminal = 1; // The accumulation continues
	        } else {
	            // Edge case: batch ends but not dead
	            nextValue = 0; // Or the value of the last state if available
	            nextNonTerminal = 0;
	        }

	        // Calculating Delta (Differential Time Error)
	        // delta = r_t + gamma * V(s_t+1) - V(s_t)
	        double delta = actions[i].reward + (discount * nextValue) - actions[i].vEstimated;

	        // Calculating GAE (Generalized Advantage Estimation)
	        // If nextNonTerminal is 0, gae becomes just delta (chain reset)
	        gae = delta + (discount * lambda * nextNonTerminal * gae);

	        actions[i].advantage = gae;

	        actions[i].vTarget = actions[i].advantage + actions[i].vEstimated;
	    }
	}
	
	private void normalizeAdvantages(ActionRegister[] batch) {
	    if (batch == null || batch.length <= 1) return;

	    int n = batch.length;
	    
	    // Calculating the Average (mu) of the advantages in the batch
	    double sum = 0;
	    for (ActionRegister reg : batch) {
	        sum += reg.advantage;
	    }
	    double mean = sum / n;

	    // Calculating Variance (sigma^2)
	    double varianceSum = 0;
	    for (ActionRegister reg : batch) {
	        varianceSum += Math.pow(reg.advantage - mean, 2);
	    }
	    double variance = varianceSum / n;

	    //  Final Normalization
	    // We use epsilon 1e-8 to avoid division by zero if the advantages are all equal.
	    double stdDev = Math.sqrt(variance + 1e-8);

	    for (ActionRegister reg : batch) {
	        reg.advantage = (reg.advantage - mean) / stdDev;
	    }
	}
	
	private void scaleRewards(ActionRegister[] batch) {
	    if (batch == null || batch.length <= 1) return;
	    
	    double sum = 0;
	    for (ActionRegister reg : batch) sum += reg.reward;
	    double mean = sum / batch.length;

	    double varSum = 0;
	    for (ActionRegister reg : batch) {
	        varSum += Math.pow(reg.reward - mean, 2);
	    }
	    
	    double stdDev = Math.sqrt(varSum / batch.length) + 1e-8; 

	    // If stdDev is still too small (e.g. almost zero), do not scale
	    if (stdDev < 1e-9) return; 

	    for (ActionRegister reg : batch) {
	        reg.reward /= stdDev;
	    }
	}
	
	/**
	 * prepare the data for the backPropagation
	 */
	public void prepareData() {
		oldR = (ArrayList<ActionRegister>) currR.clone();
		currR = new ArrayList<ActionRegister>();
	}
	
	/**
	 * get a miniBatch from the old collected data
	 * @return
	 */
	public ActionRegister[] getMiniBatch() {
		if(oldR.isEmpty())
			return null;
		Collections.shuffle(oldR);
		int lastIndex = (int) (oldR.size() * Hyperparameters.minibacthSize);
		if(lastIndex > 0)
			return oldR.subList(0,lastIndex).toArray(new ActionRegister[0]);
		return oldR.subList(0,1).toArray(new ActionRegister[0]);
	}
}

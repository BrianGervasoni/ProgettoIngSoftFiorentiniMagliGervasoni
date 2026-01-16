package progettoAI.snakeAI.model;

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
	
	public void addNewActions(ActionRegister []actions,double lastEstimated) {
		processActions(actions,lastEstimated);
		Collections.addAll(currR,actions);
	}
	
	/**
	 * process the actions to obtain the vTarget and advantage of the batch data
	 * @param actions
	 */
	private void processActions(ActionRegister[] actions,double lastEstimated) {
	    double gae = 0;
	    double discount = Hyperparameters.discount;
	    double lambda = Hyperparameters.lambda;

	    for (int i = actions.length - 1; i >= 0; i--) {
	        
	        // Determine if there is a future value.
	    	// If the agent died at this step, the next life value is 0.
	        double nextValue;
	        double nextNonTerminal; // Mask for the GAE

	        if (i + 1 < actions.length) {
	        	// Normal case: we get the value of the next action in the batch
	            nextValue = actions[i + 1].vEstimated;
	            nextNonTerminal = (actions[i].isTerminal) ? 0 : 1; 
	        } else {
	        	// Last element of the batch: we use the bootstrap passed from outside
	            nextValue = lastEstimated;
	            nextNonTerminal = (actions[i].isTerminal) ? 0 : 1;
	        }

	        // If the current action is terminal, the future value is forced to 0
	        if (actions[i].isTerminal) {
	            nextValue = 0;
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
	
	private void normalizeAdvantages(ArrayList<ActionRegister> batch) {
	    if (batch == null || batch.size() <= 1) return;

	    int n = batch.size();
	    
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
	
	/**
	 * prepare the data for the backPropagation
	 */
	public void prepareData() {
		normalizeAdvantages(currR);
		oldR = (ArrayList<ActionRegister>) currR.clone();
		oldR.forEach(e ->{
			System.out.println(e.toString());
		});
		currR = new ArrayList<ActionRegister>();
	}
	
	/**
	 * get the miniBatch from the old collected data
	 * @return
	 */
	public ArrayList<ActionRegister[]> getMiniBatch() {
		if(oldR.isEmpty())
			return null;
		
		ArrayList<ActionRegister[]> result = new ArrayList<ActionRegister[]>();
		Collections.shuffle(oldR);
		int batchSize = Math.max((int) (oldR.size() * Hyperparameters.minibacthSize),1);
		for (int j = 0; j < oldR.size(); j += batchSize) {
            int end = Math.min(j + batchSize, oldR.size());
            ActionRegister[] miniBatch = oldR.subList(j, end).toArray(new ActionRegister[0]);
            
            result.add(miniBatch);
        }
		return result;
	}
}

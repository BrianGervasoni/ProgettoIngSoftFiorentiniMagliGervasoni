package progettoAI.snakeAI.model;

import java.util.ArrayList;
import java.util.Collections;

import progettoAI.snakeAI.hyperparameters.Hyperparameters;

public class PPOMemory {
	private ArrayList<ActionRegister> oldR;
	private ArrayList<ActionRegister> currR;
	private double sma = 0;
	
	public PPOMemory() {
		oldR = new ArrayList<ActionRegister>();
		currR = new ArrayList<ActionRegister>();
	}
	
	public boolean haveData() {
		if(currR.isEmpty())
			return false;
		return true;
	}
	
	/**
	 * calcola il reward medio per ogni episodio (debug)
	 * @return
	 */
	public double calculateMeanReward() {
		if (oldR == null || oldR.isEmpty()) return 0.0;
		
		double episode=0;
		double meanEpisode=0;
		int nEpisode = 0;
		
		for(ActionRegister r:oldR) {
			episode += r.reward;
			if(r.isTerminal) {
				meanEpisode += episode;
				episode = 0;
				nEpisode++;
			}
		}
		if (nEpisode == 0) return 0.0;
		
		return meanEpisode/nEpisode;
	}
	
	/**
	 * calcola meadi mobile dei reward
	 * @param newMeanR
	 * @return
	 */
	public double calculateSMA(double newMeanR) {
		sma = sma != 0 ? sma + 0.01 * (newMeanR - sma) : newMeanR;
		return sma;
	}
	
	/**
	 * calcola la media della lunghezza delle partite
	 * @return
	 */
	public double calculateMeanlength() {
		if (oldR == null || oldR.isEmpty()) return 0.0;
		
		int nEpisode = 0;
		
		for(ActionRegister r:oldR) {
			if(r.isTerminal) {
				nEpisode++;
			}
		}
		if (nEpisode == 0) return oldR.size();
		
		return oldR.size()/(double)nEpisode;
	}
	
	/**
	 * statistica che indica se la policy è collassata
	 * @return
	 */
	public double policyDominance() {
		if(oldR == null || oldR.size() <= 0) return 0.0;
		
		double sumDominance = 0.0;

        for (ActionRegister r : oldR) {
            double dominance = 0.0;

            for (double p : r.actionsProb) {
                dominance = Math.max(dominance, p);
            }

            sumDominance += dominance;
        }

	    return sumDominance / oldR.size();
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

	        if (i + 1 < actions.length ) {
	        	// Normal case: we get the value of the next action in the batch
	            nextValue = actions[i + 1].vEstimated;
	        } else {
	        	// Last element of the batch: we use the bootstrap passed from outside
	            nextValue = lastEstimated;
	        }

	        nextNonTerminal = (actions[i].isTerminal) ? 0 : 1;
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
	    //System.out.println("mean Advantage:"+mean);
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
	        reg.advantage = Math.max(-5.0, Math.min(5.0, reg.advantage));
	    }
	}
	
	/**
	 * prepare the data for the backPropagation
	 */
	public void prepareData() {
		
		oldR = (ArrayList<ActionRegister>) currR.clone();
		System.out.println("meanReward:"+calculateMeanReward() +" | meanLength:"+calculateMeanlength());
		currR = new ArrayList<ActionRegister>();
	}
	
	@Override
	public String toString() {
		if(oldR == null || oldR.isEmpty()) return "";
		String tot = "";
		for(ActionRegister r:oldR) {
			tot += r.toString() +"\n";
		}
		return tot;
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
            ArrayList<ActionRegister> mini = new ArrayList<>(oldR.subList(j, end));
            normalizeAdvantages(mini);
            
            ActionRegister[] miniBatch = mini.toArray(new ActionRegister[0]);
            
            result.add(miniBatch);
        }
		return result;
	}
}

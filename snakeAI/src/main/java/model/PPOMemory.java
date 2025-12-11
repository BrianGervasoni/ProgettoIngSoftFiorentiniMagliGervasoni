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
	
	public void addNewActions(ActionRegister []actions) {
		processActions(actions);
		Collections.addAll(currR,actions);
	}
	
	/**
	 * process the actions to obtain the vTarget and advantage of the batch data
	 * @param actions
	 */
	private void processActions(ActionRegister[] actions) {
		for(int i=0; i<actions.length; i++) {
			actions[i].vTarget = 0;
			actions[i].advantage = 0;
			for(int j=0; j<actions.length-i;j++) {
				actions[i].vTarget += Math.pow(Hyperparameters.discount,j) * actions[i+j].reward;//V_i = sommatoria(discount^j * r_j)
				
				if(i+j<actions.length-1) {//Ai = sommatoria((discount*lambda)^j * (R_j + discount * V_j+1 - V_j)
					actions[i].advantage += Math.pow(Hyperparameters.discount * Hyperparameters.lambda ,j) * 
							(actions[i+j].reward + Hyperparameters.discount * actions[j+1].vEstimated - actions[i+j].vEstimated);
				}else {//V_j+1 = 0 if it's the last
					actions[i].advantage += Math.pow(Hyperparameters.discount * Hyperparameters.lambda ,j) * 
							(actions[i+j].reward - actions[i+j].vEstimated);
				}
			}
		}
	}
	
	/**
	 * prepare the data for the backPropagation
	 */
	public void prepareData() {
		oldR = (ArrayList<ActionRegister>) currR.clone();
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
			return (ActionRegister[]) oldR.subList(0,lastIndex).toArray();
		return(ActionRegister[]) oldR.subList(0,1).toArray();
	}
}

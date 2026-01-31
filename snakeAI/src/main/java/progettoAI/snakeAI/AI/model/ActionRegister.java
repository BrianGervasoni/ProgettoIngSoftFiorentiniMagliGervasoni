package progettoAI.snakeAI.AI.model;

import java.util.ArrayList;

public class ActionRegister {

	public double[] actionsProb;
	public int indexAction;
	public double[] state;
	public double vTarget;
	public double vEstimated;
	public double advantage;
	public double reward;
	public boolean isTerminal;

	public ActionRegister() {
		actionsProb = null;
		indexAction = -1;
		state = null;
		vTarget = 0;
		vEstimated = 0;
		advantage = 0;
		reward = 0;
		isTerminal = false;
	}
	
	public double oldSelectAction() {
		return this.actionsProb[this.indexAction];
	}
	
	public double getReward() {
		return reward;
	}

	public void setReward(double reward) {
		this.reward = reward;
	}
	
	public String toString() {
		String state= "state:(";
		if(this.state !=null)
			for(double s : this.state) {
				state+= String.format("%.4f",s);
				state+="|";
			}
		state+= ")";
		String a= "porb:(";
		if(this.actionsProb !=null)
			for(double s : this.actionsProb) {
				a+= String.format("%.4f",s);
				a+="|";
			}
		a+= ")";
		return "("+state+","+a+", selected:"+indexAction+", vTarget:"+vTarget+", vEstimated:"+vEstimated+", advantage:"+advantage+", r:"+reward+", isTerminal:"+isTerminal+")";
	}
}

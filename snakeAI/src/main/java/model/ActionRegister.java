package model;

public class ActionRegister {

	public double[] actionsProb;
	public int indexAction;
	public double[] state;
	public double vTarget;
	public double vEstimated;
	public double advantage;
	public double reward;

	public ActionRegister() {
		actionsProb = null;
		indexAction = -1;
		state = null;
		vTarget = 0;
		vEstimated = 0;
		advantage = 0;
		reward = 0;
		
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
	
}

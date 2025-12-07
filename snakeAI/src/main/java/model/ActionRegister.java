package model;

public class ActionRegister {

	public double[] actionsProb;
	public int indexAction;
	public double[] state;
	public double vTarget;
	public double vEstimated;
	public double advantage;
	public float reward;
	
	public ActionRegister() {
		actionsProb = null;
		indexAction = -1;
		state = null;
		vTarget = 0;
		vEstimated = 0;
		advantage = 0;
		reward = 0;
		
	}
	
}

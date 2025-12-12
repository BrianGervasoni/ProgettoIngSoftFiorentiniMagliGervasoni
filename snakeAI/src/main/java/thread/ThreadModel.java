package thread;

import model.Model;

public class ThreadModel extends Thread{

	Model model;
	String dirFile; 
	ThreadAIManager AIManager; //DA AGG UML
	
	public ThreadModel(String dir) {
		this.dirFile = dir;
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			this.getAIManager().threadsModelWaitForThreadsAgentToFinish();
			
			this.model.initBackPropagation();
			
			this.getAIManager().threadModelReportsThatItHasFinishedBackProp();
			
			/*
			 * CONTINUA ALTRE OPERZIONI
			 */
			
		}
	}

	public ThreadAIManager getAIManager() {
		return AIManager;
	}

	public void setAIManager(ThreadAIManager aIManager) {
		AIManager = aIManager;
	}
	
	public void update() {
		
	}

	public void save() {
		
	}
	
	public void load() {
		
	}
	
	public int forwading(double[] input) {
		
		return 0;
		
	}
	
	public double backPropagation() {
		
		return 0;
		
	}
}



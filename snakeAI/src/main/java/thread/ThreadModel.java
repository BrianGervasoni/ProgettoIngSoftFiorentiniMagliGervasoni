package thread;

import model.Model;

public class ThreadModel extends Thread{

	Model model;
	String dirFile; 
	int nThreadsAgent;
	
	public ThreadModel(Model m, String dir, int nTA) { 
		this.dirFile = dir;
		this.nThreadsAgent = nTA;
		this.model = m;
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			this.model.threadModelReportsThatItHasStartedInizitBackProp();
			
			//this.model.initBackPropagation();
			
			this.model.threadModelReportsThatItHasFinishedInizitBackProp();
			
			/*
			 * CONTINUA ALTRE OPERZIONI BACK PROPAGATION
			 */
			
			this.model.threadModelReportsThatItHasStartedOptimization();
			
			/*
			 * OPERZIONI OPTIMIZATION
			 */
			
			this.model.threadModelReportsThatItHasFinishedInizitOptimization() ;
		}
	}
	
	public void update() {
		
	}

	public void save() {
		
	}
	
	public Model load() {//TODO UML 
		return null;
	}
	
	public int forwading(double[] input) {
		return 0;
	}
	
	public double backPropagation() {
		
		return 0;
		
	}
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
}



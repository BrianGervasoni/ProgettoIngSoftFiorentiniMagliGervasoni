package thread;

import model.Model;

public class ThreadModel extends Thread{

	Model model;
	String dirFile; 
	int nThreadsAgent; //TODO UML
	
	public ThreadModel(String dir, int nTA) { //TODO UML
		this.dirFile = dir;
		this.nThreadsAgent = nTA;
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			System.out.println("thread model è in attesa che gli agents finiscano");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.model.threadModelReportsThatItHasStartedInizitBackProp();
			
			System.out.println("thread model sta inizializza back propagation");
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//this.model.initBackPropagation();
			System.out.println("thread model ha finito inizializza back prop");
			
		
			this.model.threadModelReportsThatItHasFinishedInizitBackProp();
			/*
			 * CONTINUA ALTRE OPERZIONI BACK PROPAGATION
			 */
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("CONCURRENT TIME thread model sta facendo back prop");
			
			this.model.threadModelReportsThatItHasStartedOptimization();
			
			System.out.println("thread model sta iniziando optimization");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			 * OPERZIONI OPTIMIZATION
			 */
			
			System.out.println("thread model ha finito optimization");

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



package thread;

import fileManager.JsonFileManager;
import model.Model;

public class ThreadModel extends Thread{

	private Model model;
	private String dirFile; 
	private int nThreadsAgent;
	private double[] statistics;

	public ThreadModel(String dir, int nTA) {
		this.dirFile = dir;
		this.nThreadsAgent = nTA;
		this.load();
	}
	
	@Override
	public void run() {
		
		int n = 0;
		
		while(!Thread.currentThread().isInterrupted()) {
			
			this.model.threadModelReportsThatItHasStartedInizitBackProp();
			
			//this.initBackPropagation();
			System.out.println("model ha init back prop");
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("model ha finito init back prop");	
			
			this.model.threadModelReportsThatItHasFinishedInizitBackProp();
			
			System.out.println("CONCURRENT model back prop");
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("CONCURRENT model ha finito back prop");
			
			//this.backPropagation();
			
			this.model.threadModelReportsThatItHasStartedOptimization();
			
			
			System.out.println("model start opt");
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("model ha finito opt");
			//this.optimization();
			
			this.model.threadModelReportsThatItHasFinishedInizitOptimization() ;
			
			if(n == 5) {
				this.save();
				n = 0;
			}
			
			n++;
		}
	}
	
	public void saveStatistics(double[] loss) {
		this.statistics = loss;
	}
	
	public void save() {
		JsonFileManager.saveModel(model, dirFile);
	}
	
	public void load() {
		this.model = JsonFileManager.loadModel(dirFile);
	}
	
	public void initBackPropagation() { 
		this.model.initBackPropagation();
	}
	
	public void optimization() { 
		this.model.optimization();
	}
	
	public void backPropagation() {
		this.model.backPropagation(); 
	}
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	public int getnThreadsAgent() {
		return nThreadsAgent;
	}

	public void setnThreadsAgent(int nThreadsAgent) {
		this.nThreadsAgent = nThreadsAgent;
	}
	
}



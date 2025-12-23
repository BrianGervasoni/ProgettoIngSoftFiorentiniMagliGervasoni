package thread;

import fileManager.JsonFileManager;
import model.Model;

public class ThreadModel extends Thread{

	private Model model;
	private String dirFile; 
	private int nThreadsAgent;
	
	public ThreadModel(String dir, int nTA) {
		this.dirFile = dir;
		this.nThreadsAgent = nTA;
		this.load();
	}
	
	@Override
	public void run() {
		
		int n = 0;
		
		while(true) {
			
			this.model.threadModelReportsThatItHasStartedInizitBackProp();
			
			this.initBackPropagation();
			
			this.model.threadModelReportsThatItHasFinishedInizitBackProp();
			
			this.backPropagation();
			
			this.model.threadModelReportsThatItHasStartedOptimization();
			
			this.optimization();
			
			this.model.threadModelReportsThatItHasFinishedInizitOptimization() ;
			
			if(n == 5) {
				this.save();
				n = 0;
			}
			
			n++;
		}
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
	
	public double[] backPropagation() {
		return this.model.backPropagation(); //TODO POTRESTI SALVARE LE STATISTICHE DELLA BACKPROP E GESTIRLE DIVERSAMENTE !!!!!!
	}
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
}



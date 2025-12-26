package thread;

import fileManager.JsonFileManager;
import model.Model;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class ThreadModel extends Thread{

	private Model model;
	private String dirFile; 
	private int nThreadsAgent;
	private final BehaviorSubject<double[]> lossStat;

	public ThreadModel(String dir, int nTA) {
		this.dirFile = dir;
		this.nThreadsAgent = nTA;
		lossStat = BehaviorSubject.create();
		this.load();
	}
	
	@Override
	public void run() {
		
		int n = 0;
		
		while(!Thread.currentThread().isInterrupted()) {
			
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
	
	public Observable<double[]> observableLoss() {
        return lossStat.hide();
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
		lossStat.onNext(this.model.backPropagation()); 
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



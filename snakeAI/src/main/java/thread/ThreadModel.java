package thread;

import java.io.FileNotFoundException;
import java.io.IOException;

import fileManager.JsonFileManager;
import model.Model;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class ThreadModel extends Thread{

	private Model model;
	private String dirFile; 
	private int nThreadsAgent;
	private final BehaviorSubject<double[]> lossStat;

	public ThreadModel(String dir, int nTA) throws IOException {
		this.dirFile = dir;
		this.nThreadsAgent = nTA;
		lossStat = BehaviorSubject.create();
		try {
			this.load();
		} catch (FileNotFoundException e) {
			this.model = new Model();
		}
		
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
				try {
					this.save();
				}catch(IOException e) {
					throw new RuntimeException(e.getMessage(),e.getCause());
				}
				
				n = 0;
			}
			
			n++;
		}
	}
	
	public Observable<double[]> observableLoss() {
        return lossStat.hide();
    }
	
	public void save() throws IOException,FileNotFoundException{
		JsonFileManager.saveModel(model, dirFile);
	}
	
	public void load() throws IOException{
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



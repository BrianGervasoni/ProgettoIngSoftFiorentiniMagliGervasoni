package thread;

import java.io.FileNotFoundException;
import java.io.IOException;

import errorHandler.ArithmeticException;
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
		if(!checkModel())
			this.model = new Model();
	}
	
	@Override
	public void run() {
		
		int n = 0;
		
		while(!Thread.currentThread().isInterrupted()) {
			
			this.model.threadModelReportsThatItHasStartedInizitBackProp();
			System.out.println("model:"+Thread.currentThread().getName()+" inizio initBack");
			this.initBackPropagation();
			System.out.println("model:"+Thread.currentThread().getName()+" fine initBack");
			this.model.threadModelReportsThatItHasFinishedInizitBackProp();
			
			try {
				this.backPropagation();
			}catch(ArithmeticException e) {
				throw new RuntimeException(e.getMessage(),e.getCause());
			}
			
			
			this.model.threadModelReportsThatItHasStartedOptimization();
			System.out.println("model:"+Thread.currentThread().getName()+" inizio optimization");
			this.optimization();
			System.out.println("model:"+Thread.currentThread().getName()+" fine optimization");
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
	
	private boolean checkModel() {
		if(this.model == null)
			return false;
		return this.model.checkCorrectFunction();
	}
	
	public Observable<double[]> observableLoss() {
        return lossStat.hide();
    }
	
	public void save() throws IOException,FileNotFoundException{
		JsonFileManager.saveModel(model, dirFile);
	}
	
	public void load() throws IOException{
		this.model = JsonFileManager.loadModel(dirFile);
		if(this.model == null) {
			this.model = new Model();
		}
	}
	
	public void initBackPropagation() { 
		this.model.initBackPropagation();
	}
	
	public void optimization() { 
		this.model.optimization();
	}
	
	public void backPropagation() throws ArithmeticException {
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



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
	private Syncronizer coordinator;
	private String dirFile; 
	private final BehaviorSubject<double[]> lossStat;

	public ThreadModel(String dir,Syncronizer coordinator ) throws IOException {
		this.dirFile = dir;
		this.coordinator = coordinator;
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
		try {
			int n = 0;
			
			while(!Thread.currentThread().isInterrupted()) {
				
				//aspetta che gli agenti finiscano il primo caricamento
	            coordinator.waitForAgentsForInit();
				this.initBackPropagation();
	            coordinator.finishInitBack(); // SBLOCCA AGENTI
				
				try {
					this.backPropagation();
				}catch(ArithmeticException e) {
					throw new RuntimeException(e.getMessage(),e.getCause());
				}
				
				
	            coordinator.modelFinishedBackProp();
	            //sincronizzazione finale
	            coordinator.waitForAllBeforeOptimization();
				this.optimization();
				coordinator.finishOptimization();
				
				if(n == 5) {
					try {
						this.save();
						System.gc();
					}catch(IOException e) {
						throw new RuntimeException(e.getMessage(),e.getCause());
					}
					
					n = 0;
				}
				
				n++;
			}
		}catch (InterruptedException e){
			try {
				this.save();
			} catch (IOException e1) {
				throw new RuntimeException(e.getMessage(),e.getCause());
			}
			Thread.currentThread().interrupt(); // Ripristina il flag
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
}



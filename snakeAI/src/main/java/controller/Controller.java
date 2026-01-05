package controller;

import gioco.snakeAI.Map;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import thread.ThreadAIManager;
import userInterface.UserInterface;

public class Controller {

	private ThreadAIManager ai;
	private UserInterface view;
	private Disposable snakeObserver;
	private Disposable lossObserver;
	
	
	public Controller(UserInterface newUi) {
		
		view = newUi;
		snakeObserver = null;
		lossObserver = null;
	}
	
	
	public void startTraining(int threadNumber) {
		
		ai = new ThreadAIManager(threadNumber, 1);
		ai.startTraining();
		snakeObserver = ai.getObserverFromIndexAgent().subscribe(this::gestioneStreamMap);
		lossObserver = ai.getObserverFromModel().subscribe(this::gestioneStreamLoss);
	}

	
	public void startExecution() {
		
		ai = new ThreadAIManager(1, 1);
		ai.startExecution();
		snakeObserver = ai.getObserverFromIndexAgent().subscribe(this::gestioneStreamMap);
	}
	
	
	public void terminateTraining() {
		
		ai.terminateTraining();
		snakeObserver.dispose();		
		lossObserver.dispose();
	}
	
	
	public void terminateExecution() {
		
		ai.terminateExecution();
		snakeObserver.dispose();		
	}

	
	public void toggleFromExecToTrain() {
		
		ai.terminateExecution();
		ai.startTraining();
	}
	
	
	public void toggleFromTrainToExec() {
		
		ai.terminateTraining();
		ai.startExecution();
	}
	

	public void exit() {
	
		if(snakeObserver != null)
			snakeObserver.dispose();
			
		
		if(lossObserver != null)
			lossObserver.dispose();
		
		if(ai != null)
			ai.terminate();
		
	}
	
	
	/**
	 * 
	 * metodo che processa lo streaming di dati delle mappe
	 * 
	 */
	public void gestioneStreamMap(Map map){
		
		this.view.viewMap(map);
	}
	
	
	public void gestioneStreamLoss(double[] array) {
		
		this.view.setLossAgent(array[0]);
	}
	
	
	
	public void nextMap() {
		
		snakeObserver.dispose();		
		ai.selectNextMap();
		snakeObserver = ai.getObserverFromIndexAgent().subscribe(this::gestioneStreamMap);
	}
	
	
	public void previousMap() {
		
		snakeObserver.dispose();
		ai.selectPreviousMap();
		snakeObserver = ai.getObserverFromIndexAgent().subscribe(this::gestioneStreamMap);
	}
	
	
	public Map getMap() {
		
		return ai.selectMap();
	}

	
}
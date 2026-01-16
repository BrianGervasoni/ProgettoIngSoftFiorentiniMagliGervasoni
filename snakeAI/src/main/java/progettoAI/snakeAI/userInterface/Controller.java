package progettoAI.snakeAI.userInterface;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.SwingUtilities;

import io.reactivex.rxjava3.disposables.Disposable;
import progettoAI.snakeAI.errorHandler.ThreadException;
import progettoAI.snakeAI.gioco.Map;
import progettoAI.snakeAI.thread.ThreadAIManager;

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
	
	
	public void startTraining(int threadNumber,String name) throws ThreadException,IOException{
		
		ai = new ThreadAIManager(threadNumber, 1);
		ai.createIstance(name);
		ai.setAgentExceptionHandler(errore -> {
		    SwingUtilities.invokeLater(() -> {
		       view.gestisciErroreAgent(errore);
		    });
		});
		
		ai.setModelExceptionHandler(errore -> {
		    SwingUtilities.invokeLater(() -> {
		    	view.gestisciErroreModel(errore);
		    });
		});
		
		ai.startTraining();
		snakeObserver = ai.getObserverFromIndexAgent().subscribe(this::gestioneStreamMap);
		lossObserver = ai.getObserverFromModel().subscribe(this::gestioneStreamLoss);
	}

	
	public void startExecution(String name) throws ThreadException,IOException{
		
		ai = new ThreadAIManager(1, 1);
		ai.createIstance(name);
		
		ai.setAgentExceptionHandler(errore -> {
		    SwingUtilities.invokeLater(() -> {
		    	view.gestisciErroreAgent(errore);
		    });
		});
		
		ai.startExecution();
		
		snakeObserver = ai.getObserverFromIndexAgent().subscribe(this::gestioneStreamMap);
	}
	
	
	public void terminateTraining() throws FileNotFoundException, IOException {
		
		ai.terminateTraining();
		snakeObserver.dispose();		
		lossObserver.dispose();
	}
	
	
	public void terminateExecution(){
		
		ai.terminateExecution();
		snakeObserver.dispose();		
	}

	

	public void exit() throws FileNotFoundException, ThreadException, IOException {
	
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
		this.view.setLossModel(array[1]);
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
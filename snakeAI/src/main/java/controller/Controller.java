package controller;

import gioco.snakeAI.Map;
import thread.ThreadAIManager;
import userInterface.UserInterface;

public class Controller {

	private ThreadAIManager ai;
	private UserInterface view;
	
	public Controller(ThreadAIManager newTam, UserInterface newUi) {
		
		ai = newTam;
		view = newUi;
	}
	
	
	public void startTraining() {
		
		ai.startTraining();
	}

	public void startExecution() {
		
		ai.startExecution();
	}
	
	public void terminateTraining() {
		
		ai.terminateTraining();
	}
	
	public void terminateExecution() {
		
		ai.terminateExecution();
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
		
		//TODO non è chiaro come implementarlo
	}
	
	
	
	public void nextMap() {
		
		ai.selectNextMap();
	}
	
	public void previousMap() {
		ai.selectPreviousMap();
	}
	
	public Map getMap() {
		return ai.selectMap();
	}
	
	
	
}



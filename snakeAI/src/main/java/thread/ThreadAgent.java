package thread;

import boxes.Direction;
import gioco.snakeAI.GameMain;
import gioco.snakeAI.Map;
import model.*;

public class ThreadAgent extends Thread{

	Model model;
	Intermediary intermediary;
	GameMain game;
	ThreadAIManager AIManager; //DA AGG UML

	public ThreadAgent(Model model) {
		this.model = model;
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			intermediary.mapConversion(game.getMap());
			/*
			 * intermediary
			 * MAP CONVERTION
			 * MOVE CONVERTION
			 * MOVE SELECTION
			 * MOVE
			 * CALCOLO REARD
			 * ADD REWARD[]
			 * CHECK LIFE/DEATH
			 * 		IF DEAD game.finish() == true
			 * 			FINISH EPISODE
			 * 			SEND ACTIONS
			 * 		IF LIFE game.finish() == false
			 * 			REPEAT FROM MAP CONVERTION
			 */
			
			if(game.finish() == true) {
				
				intermediary.finishEpisode();
				/**
				 * SEND ACTIONS
				 */
				
				this.getAIManager().threadAgentReportThatItHasFinished();
				
			}
			
		}
	}
	
	public void finish() {
		
	}
	
	public float calculateReward(Map map) {
		
		return 0;
		
	}
	
	public void move(Direction direction) {
		
	}
	
	public void resetActionRegister() {
		
	}
	
	public void sendActions (ActionRegister actionRegister) {
		
	}
	
	public void setAIManager(ThreadAIManager aIManager) {
		AIManager = aIManager;
	}
	
	public ThreadAIManager getAIManager() {
		return AIManager;
	}
	
}

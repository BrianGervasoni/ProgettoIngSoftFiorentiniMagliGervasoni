package thread;

import boxes.Direction;
import gioco.snakeAI.GameMain;
import gioco.snakeAI.Map;
import model.*;

public class ThreadAgent extends Thread{

	Model model;
	Intermediary intermediary;
	GameMain game;
	boolean ready; //DA AGGIUNGERE A UML it tells if the thread action is ready to execute or not

	public ThreadAgent(Model model) {
		this.model = model;
		this.ready = true;
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			do {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}while(ready == false);
			
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
				ready = false;
				
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
	
	public void setStart(boolean start) {
		this.ready = start;
	}
	
}

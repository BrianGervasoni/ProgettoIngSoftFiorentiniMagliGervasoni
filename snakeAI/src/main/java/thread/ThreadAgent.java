package thread;

import boxes.Direction;
import gioco.snakeAI.GameMain;
import gioco.snakeAI.Map;
import model.*;

public class ThreadAgent extends Thread{

	Model model;
	Intermediary intermediary;
	GameMain game;
	boolean start = false;
	
	public ThreadAgent(Model model) {
		this.model = model;
	}
	
	@Override
	public void run() {
		do {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(start == false);
		
		/*
		 * intermediary
		 * MAP CONVERTION
		 * MOVE CONVERTION
		 * MOVE SELECTION
		 * MOVE
		 * CALCOLO REARD
		 * ADD REWARD[]
		 * CHECK LIFE/DEATH
		 * 		IF DEAD
		 * 			FINISH EPISODE
		 * 			SEND ACTIONS
		 * 		IF LIFE
		 * 			REPEAT FROM MAP CONVERTION
		 */
		intermediary.mapConversion(game.getMap());
		
		start = false;
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
	
}

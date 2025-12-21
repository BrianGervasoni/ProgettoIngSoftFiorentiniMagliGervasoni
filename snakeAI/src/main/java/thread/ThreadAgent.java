package thread;

import boxes.Direction;
import boxes.Food;
import boxes.SnakeBody;
import gioco.snakeAI.GameMain;
import gioco.snakeAI.Map;
import model.*;

public class ThreadAgent extends Thread implements Functions{

	Model model;
	Intermediary intermediary;
	GameMain game;

	public ThreadAgent(Model model) {
		this.model = model;
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			this.model.threadAgentReportThatItHasStarted();
			//intermediary.mapConversion(game.getMap());
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
			
			System.out.println("thread agent sta eseguendo");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//if(game.finish() == true) {
			
				//intermediary.finishEpisode();
				/**
				 * SEND ACTIONS
				 */
				System.out.println("thread agent ha finito");
			
				this.model.threadAgentReportThatItHasFinished();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			//}
			
		}
	}
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	
	public void finish() {
		
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public double calculateReward(Map map) {
		
		double rewardBasic = 4, rewardDistanceApple = 0, rewardGetApple = 50, rewardDead = -50;
		double diagonal = Math.sqrt((map.X*map.X) + (map.Y*map.Y));
		double distance;
		
		for(int i=0; i<map.X; i++) {
			for(int j=0; j<map.Y; j++) {
				
				if(map.getBox(i, j).equals(SnakeBody.HEAD)) {
					
					for(int k=0; k<map.X; k++) {
						for(int h=0; h<map.Y; h++) {
							
							if(map.getBox(k, h).equals(Food.APPLE)) {
								
								distance = this.calculateDistance(map.getBox(i, j), map.getBox(h, k));
								rewardDistanceApple = this.normalizeRewardDistanceHeadApple(distance, 0, diagonal); 
							}
							
						}
					}
					
				}
			}
		}
		
		rewardBasic = rewardBasic + rewardDistanceApple;
		
		if(map.checkAppleCollision()) {
			rewardBasic = rewardBasic + rewardGetApple;
		}
		
		if(map.checkDefeat()) {
			rewardBasic = rewardBasic + rewardDead;
		}
		
		
		return rewardBasic;
	}
	
	/**
	 * 
	 * @param direction
	 * @return use the chosen direction to make a move of the snake
	 */
	public void move(Direction direction) {
		this.getGame().giveDirections(direction);
	}
	
	public void resetActionRegister() {
		
	}
	
	public void sendActions (ActionRegister actionRegister) {
		
	}
	
	public GameMain getGame() {
		return game;
	}

	public void setGame(GameMain game) {
		this.game = game;
	}
	
}

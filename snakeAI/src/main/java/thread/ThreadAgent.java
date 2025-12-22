package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	
	final Lock lock = new ReentrantLock();
	private int number;
	static int N = 0;

	public ThreadAgent(Model model) {
		this.model = model;
		this.number = N;
		N++;
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
			 *		IF LIFE game.finish() == false
			 * 			REPEAT FROM MAP CONVERTION
			 */
			
			System.out.println("thread agent " +number +" sta eseguendo");
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//if(game.finish() == true) {
			
				//intermediary.finishEpisode();
				lock.lock();
				try {		
					//SEND ACTIONS
					System.out.println("thread agent numero " + number + " sta inviando azioni");
					System.out.println("thread agent numero " + number + " ha finito di inviare azioni");
				}finally {
					lock.unlock();
				}
				
				System.out.println("thread agent " + number + " ha finito");
			
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
	 * there are 4 type of reward :
	 * - default reward = 4 
	 * - reward based on the distance between the head and the apple which is a value between (-5 ; 5)
	 * - reward if the snake got the apple = 50
	 * - reward if the snake died = -50
	 * @return the sum of the reward values, which says if the AI is doing good or not
	 */
	public double calculateReward(Map map) {
		
		double rewardDefault = 4, rewardDistanceApple, rewardGetApple = 50, rewardDead = -50;
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
								rewardDefault = rewardDefault + rewardDistanceApple;
								
							}
							
						}
					}
					
				}
			}
		}
		
		if(map.checkAppleCollision()) {
			rewardDefault = rewardDefault + rewardGetApple;
		}
		
		if(map.checkDefeat()) {
			rewardDefault = rewardDefault + rewardDead;
		}
		
		return rewardDefault;
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

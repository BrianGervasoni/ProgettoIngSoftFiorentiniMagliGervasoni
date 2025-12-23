package thread;

import boxes.Direction;
import boxes.Food;
import boxes.SnakeBody;
import gioco.snakeAI.GameMain;
import gioco.snakeAI.Map;
import model.*;
import progettoAI.snakeAI.hyperparameters.Hyperparameters;

public class ThreadAgent extends Thread implements Functions{

	Model model;
	Intermediary intermediary;
	GameMain game;
	
	private static final Object sharedLock = new Object();

	public ThreadAgent(Model model) {
		this.model = model;
		this.intermediary = new Intermediary();
		this.game = new GameMain();
	}
	
	@Override
	public void run() {
		
		int t = 0;
		
		while(true) {
			
			/* LOCK
			 * WHILE(game.finish() == false AND T < hyper.timestep)
			 * MAP CONVERTION
			 * ADD ACTIONS
			 * MOVE CONVERTION
			 * MOVE SELECTION
			 * CALCOLO REWARD
			 * ADD REWARD
			 * END WHILE
			 * Syncronize(SEND ACTIONS)
			 * UNLOCK
			 * RESET ACTIONS
			 * IF(game.finish() == true)
			 * 		RESET MAP
			 */
			while(this.game.finish() == false && t < Hyperparameters.timeStep) {
				
				this.model.threadAgentReportThatItHasStarted();
				
				this.intermediary.addActionRegister(this.model.forwarding(this.intermediary.mapConversion(this.game.getMap(), this.model.getInputLenght())));
				
				this.move(this.intermediary.moveSelection(this.intermediary.selectLastActionRegister().actionsProb));
				
				this.intermediary.addActionReward(this.calculateReward(this.game.getMap()));
			}
			
			t++;
			
			synchronized(sharedLock) {
				this.sendActions();
				this.resetActionRegister(); 
			}
			
			if(game.finish() == true) {
				//this.game.reset(); DEVONO CREARE RESET METODO
				this.model.threadAgentReportThatItHasFinished();
			}
			
			
			
		}
	}
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
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
		this.intermediary.reset();
	}
	
	public void sendActions () {
		ActionRegister[] a = this.intermediary.actionRegister.toArray(new ActionRegister[0]);
		this.model.memorizeActions(a);
	}
	
	public GameMain getGame() {
		return game;
	}

	public void setGame(GameMain game) {
		this.game = game;
	}
}

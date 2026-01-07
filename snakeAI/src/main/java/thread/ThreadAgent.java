package thread;

import boxes.Direction;
import gioco.snakeAI.GameMain;
import gioco.snakeAI.Map;
import model.*;
import progettoAI.snakeAI.hyperparameters.Hyperparameters;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import errorHandler.ArithmeticException;

public class ThreadAgent extends Thread implements Functions{

	private Model model;
	private Intermediary intermediary;
	private GameMain game;
	private final BehaviorSubject<Map> mapStat;
	private boolean lockSpeed;
	
	public ThreadAgent(Model model) {
		this.model = model;
		this.intermediary = new Intermediary();
		this.game = new GameMain();
		mapStat = BehaviorSubject.create();
		lockSpeed = false;
	}
	
	@Override
	public void run() {
		
		int t = 0;
		long inizio;
		long fine;
		long durataEffettiva;
		long attesaNecessaria;
		
		while(!Thread.currentThread().isInterrupted()) {
			
			while(this.game.finish() == false && t < Hyperparameters.timeStep) {
				inizio = System.currentTimeMillis();
				
				this.mapStat.onNext(this.getGame().getMap());
				
				this.model.threadAgentReportThatItHasStarted();
				
				try {
					this.intermediary.addActionRegister(this.model.forwarding(this.intermediary.mapConversion(this.game.getMap(), this.model.getInputLenght())));
					this.move(this.intermediary.moveSelection(this.intermediary.selectLastActionRegister().actionsProb));
					
					this.intermediary.addActionReward(this.calculateReward());
					
					if(this.isLockSpeed()) {
						fine = System.currentTimeMillis();
					    durataEffettiva = fine - inizio;
					    attesaNecessaria = 1000 - durataEffettiva;// deve attendere almeno 1s
					    if (attesaNecessaria > 0) {
					        try {
					            Thread.sleep(attesaNecessaria);
					        } catch (InterruptedException e) {
					            e.printStackTrace();
					        }
					    }
					}
					
					t++;
				}catch(ArithmeticException e) {
					throw new RuntimeException(e.getMessage(),e.getCause());
				}catch(IllegalArgumentException e) {
					System.err.println("WARNING! Si sta cercando di aggiungere un null all'intermediario: "+e.getMessage());
				}	
			}
			
			t = 0;
			
			if(!this.isLockSpeed()) {
				this.model.startingSendActions();
				this.sendActions(); 	
				this.model.terminatingSendActions();
			}
			
			this.resetActionRegister();
			
			
			
			if(game.finish() == true) {
				this.game.reset(); 
				this.model.threadAgentReportThatItHasFinished();
			}
		}
	}
	
	public Observable<Map> observableMap() {
        return mapStat.hide();
    }
	
	
	
	public boolean isLockSpeed() {
		return lockSpeed;
	}

	public void setLockSpeed(boolean lockSpeed) {
		this.lockSpeed = lockSpeed;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	/**
	 * 
	 * there are 4 type of reward :
	 * - default reward = 4 
	 * - reward based on the distance between the head and the apple which is a value between (-5 ; 5)
	 * - reward if the snake got the apple = 50
	 * - reward if the snake died or dosn't have eaten an apple for timeStep = -50
	 * @return the sum of the reward values, which says if the AI is doing good or not
	 */
	public double calculateReward() { //TODO TESTARE
		
		double rewardDefault = 4, rewardDistanceApple, rewardGetApple = 50, rewardDead = -50;
		double diagonal = Math.sqrt((this.game.getMap().X*this.game.getMap().X) + (this.game.getMap().Y*this.game.getMap().Y));
		double distance;
		
		distance = this.calculateDistance(this.game.getMap().getSnake().getBodyPiece(0), this.game.getMap().getApple());
		rewardDistanceApple = this.normalizeRewardDistanceHeadApple(distance, 0, diagonal); 
		rewardDefault = rewardDefault + rewardDistanceApple;
								
		
		if(this.game.getMap().getAppleCollision()) {
			rewardDefault = rewardDefault + rewardGetApple;
		}
		
		if(this.game.finish()) {
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
	
	public Intermediary getIntermediary() {
		return intermediary;
	}

	public void setIntermediary(Intermediary intermediary) {
		this.intermediary = intermediary;
	}
}

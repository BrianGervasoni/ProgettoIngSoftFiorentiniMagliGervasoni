package progettoAI.snakeAI.thread;

import org.nd4j.linalg.api.memory.MemoryWorkspace;
import org.nd4j.linalg.api.memory.conf.WorkspaceConfiguration;
import org.nd4j.linalg.api.memory.enums.AllocationPolicy;
import org.nd4j.linalg.api.memory.enums.LearningPolicy;
import org.nd4j.linalg.api.memory.enums.SpillPolicy;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.boxes.Direction;
import progettoAI.snakeAI.errorHandler.ArithmeticException;
import progettoAI.snakeAI.game.GameMain;
import progettoAI.snakeAI.game.Map;
import progettoAI.snakeAI.hyperparameters.Hyperparameters;
import progettoAI.snakeAI.model.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class ThreadAgent extends Thread implements Functions{

	private Model model;
	private Syncronizer coordinator;
	private Intermediary intermediary;
	private GameMain game;
	private final BehaviorSubject<Map> mapStat;
	private boolean lockSpeed;
	private final int emptyTick = 150;
	private static WorkspaceConfiguration CONFIG = WorkspaceConfiguration.builder()
		    .policyLearning(LearningPolicy.OVER_TIME)
		    .cyclesBeforeInitialization(10) // Monitora 10 iterazioni prima di stabilizzarsi
		    .policyAllocation(AllocationPolicy.OVERALLOCATE) // Aggiunge un ~10% di margine extra
		    .policySpill(SpillPolicy.REALLOCATE) // Se supera la dimensione, rialloca invece di crashare
		    .build();
	
	public ThreadAgent(Model model,Syncronizer coordinator) {
		this.model = model;
		this.coordinator = coordinator;
		this.intermediary = new Intermediary();
		this.game = new GameMain();
		mapStat = BehaviorSubject.create();
		lockSpeed = false;
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				if(!this.isLockSpeed()) {
					coordinator.startingSendActions();
		            runAgentTraining();
		            coordinator.terminatingSendActions();
		            runAgentTraining();
		            coordinator.agentFinishedLoadingNext();
				}else {
					runAgentExecution();
				}
	            
			}
		}catch (InterruptedException e) {
	        Thread.currentThread().interrupt(); // Ripristina il flag
	    }
	}
	
	public void runAgentTraining() throws InterruptedException {
		for(int t=0; t < Hyperparameters.timeStep; t++) {
			
			this.mapStat.onNext(this.getGame().getMap());
			
			try(MemoryWorkspace ws = Nd4j.getWorkspaceManager().getAndActivateWorkspace(CONFIG, "AGENT_WORK_WS_" + Thread.currentThread().getName())) {
				this.intermediary.addActionRegister(this.model.forwarding(this.intermediary.mapConversion(this.game.getMap(), this.model.getInputLenght())));
				this.intermediary.selectLastActionRegister().indexAction = this.intermediary.moveSelectionTraining(this.intermediary.selectLastActionRegister().actionsProb);
				this.move(this.intermediary.moveConversion(this.intermediary.selectLastActionRegister().indexAction));
				
				this.intermediary.addActionReward(this.calculateReward());
				
				if(game.finish() == true || game.getTickLastApple() >= emptyTick) {
					this.game.reset(); 
					this.intermediary.selectLastActionRegister().isTerminal = true;
				}
			}catch(ArithmeticException e) {
				
			}catch(IllegalArgumentException e) {
				System.err.println("WARNING! Si sta cercando di aggiungere un null all'intermediario: "+e.getMessage());
			}	
		}
		
		try {
			this.sendActions();
		} catch (ArithmeticException e) {
			throw new RuntimeException(e.getMessage(),e.getCause());
		} 	
		
		this.resetActionRegister();
	}
	
	private void runAgentExecution()  throws InterruptedException{
		long inizio;
		long fine;
		long durataEffettiva;
		long attesaNecessaria;
		for(int t=0; t < Hyperparameters.timeStep; t++) {
			inizio = System.currentTimeMillis();
			
			this.mapStat.onNext(this.getGame().getMap());
			
			try(MemoryWorkspace ws = Nd4j.getWorkspaceManager().getAndActivateWorkspace(CONFIG, "AGENT_WORK_WS_" + Thread.currentThread().getName())) {
				this.intermediary.addActionRegister(this.model.forwarding(this.intermediary.mapConversion(this.game.getMap(), this.model.getInputLenght())));
				this.intermediary.selectLastActionRegister().indexAction = this.intermediary.moveSelectionBest(this.intermediary.selectLastActionRegister().actionsProb);
				this.move(this.intermediary.moveConversion(this.intermediary.selectLastActionRegister().indexAction));
				
				this.resetActionRegister();
				
				fine = System.currentTimeMillis();
			    durataEffettiva = fine - inizio;
			    attesaNecessaria = 500 - durataEffettiva;// deve attendere almeno 0.5s
			    if (attesaNecessaria > 0) {
			    	Thread.sleep(attesaNecessaria);
			    }
				
				if(game.finish() == true) {
					this.game.reset(); 
				}
			}catch(ArithmeticException e) {
				throw new RuntimeException(e.getMessage(),e.getCause());
			}catch(IllegalArgumentException e) {
				System.err.println("WARNING! Si sta cercando di aggiungere un null all'intermediario: "+e.getMessage());
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
	 * - default reward = 0.0
	 * - reward based on the difference of distance between the head and the apple whit the lastPosition which is a value between (-0.2 ; 0.2)
	 * - reward if the snake got the apple = 2
	 * - reward if the snake died = -1.5
	 * - reward slight negative for every tick it hasn't take any apple (r = -0.05, t=5)
	 * @return the sum of the reward values, which says if the AI is doing good or not
	 */
	public double calculateReward() {
		
		double rewardDefault = 0.0, rewardDistanceApple, rewardGetApple = 3, rewardDead = -0.3,rewardEmptyTick=-0.0005;
		//double diagonal = Math.sqrt((this.game.getMap().X*this.game.getMap().X) + (this.game.getMap().Y*this.game.getMap().Y));
		double distance;
		double lastDistance;
		
		distance = this.calculateDistance(this.game.getMap().getSnake().getBodyPiece(0), this.game.getMap().getApple());
		lastDistance = this.calculateDistance(this.game.getMap().getSnake().getBodyPiece(1), this.game.getMap().getApple());
		rewardDistanceApple = 0.1 * (lastDistance-distance);
		
		if(this.game.finish()) {
			rewardDefault += rewardDead;
		}
		
		if(this.game.getMap().getAppleCollision()) {
			rewardDefault = rewardDefault + rewardGetApple;
		}else {
			if(this.game.getTickLastApple() > 1)
				rewardDefault = rewardDefault + rewardDistanceApple;
		}
		
		if(this.game.getTickLastApple() > 1) {
			if(this.game.getTickLastApple() >= this.emptyTick) {
				rewardDefault += rewardDead/10;
			}else {
				rewardDefault += rewardEmptyTick;
			}
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
	
	public void sendActions () throws ArithmeticException {
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

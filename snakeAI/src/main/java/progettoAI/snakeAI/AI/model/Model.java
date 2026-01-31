package progettoAI.snakeAI.AI.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.nd4j.linalg.api.ndarray.INDArray;

import progettoAI.snakeAI.AI.AIActor;
import progettoAI.snakeAI.AI.AICritic;
import progettoAI.snakeAI.AI.TypeGradientUpdate;
import progettoAI.snakeAI.AI.hyperparameters.Hyperparameters;
import progettoAI.snakeAI.AI.tools.Tools;
import progettoAI.snakeAI.errorHandler.ArithmeticException;

public class Model {
	private AICritic critic;
	private AIActor actor;
	private long episode = 0;
	private transient PPOMemory memory;
	private transient final int  ratioLoss = 1;
	public transient double meanEntropy = 0;
	
	/**
	 * setup the default configuration (critic: 3X126 actor: 3X256)
	 */
	public Model() {
		memory = new PPOMemory();
		if(critic == null || actor == null) {
			critic = new AICritic(new int[] {(7*3)+4,64,32,1},TypeGradientUpdate.DESCEND);
			actor = new AIActor(new int[] {(7*3)+4,64,32,3},TypeGradientUpdate.ASCEND);
		}
	}
	
	public Model(AICritic critic, AIActor actor) {
		memory = new PPOMemory();
		this.critic = critic;
		this.actor = actor;
	}
	
	public AICritic getCritic() {
		return critic;
	}

	public void setCritic(AICritic critic) {
		this.critic = critic;
	}

	public AIActor getActor() {
		return actor;
	}

	public void setActor(AIActor actor) {
		this.actor = actor;
	}

	public PPOMemory getMemory() {
		return memory;
	}

	public void setMemory(PPOMemory memory) {
		this.memory = memory;
	}
	
	public boolean checkCorrectFunction() {
		if(critic == null || actor == null || memory == null)
			return false;
		return true;
	}
	
	/**
	 * return how many input node the model have
	 * @return
	 */
	public int getInputLenght() {
		return actor.getInputLenght();
	}

	public long getEpisode() {
		return episode;
	}
	
	/**
	 * statistica che indica se la policy è collassata
	 * @return
	 */
	public double policyDominance() {
		return this.memory.policyDominance();
	}
	
	/**
	 * calcola il reward medio per ogni episodio (debug)
	 * @return
	 */
	public double calculateMeanReward() {
		return this.memory.calculateMeanReward();
	}
	
	public double calculateSMA(double meanReward) {
		return memory.calculateSMA(meanReward);
	}
	
	/**
	 * calcola la media della lunghezza delle partite
	 * @return
	 */
	public double calculateMeanlength() {
		return this.memory.calculateMeanlength();
	}

	/**
	 * perform the forwarding for the critic and actor
	 * @param input
	 * @return ActionRegister with forwarding data, null if input data where null
	 * @throws ArithmeticException 
	 */
	public ActionRegister forwarding(double[] input) throws ArithmeticException {
		try {
			CompletableFuture<double[]> procCritic = forwardingCritic(input);
			CompletableFuture<double[]> procActor = forwardingActor(input);
			
			double[] resCritic = procCritic.join();
			double[] resActor = procActor.join();
			
			if(resCritic != null && resActor != null) {
				ActionRegister r = new ActionRegister();
				r.actionsProb = resActor;
				r.vEstimated = resCritic[0];
				r.state = input;
				return r;
			}else {
				return null;
			}
			
		}catch (RuntimeException e) {
			if (e.getCause() instanceof ArithmeticException) {
	            
	            throw (ArithmeticException) e.getCause();
	        }
	        throw e;
	    }
		
	}
	
	private CompletableFuture<double[]> forwardingCritic(double[] input){
		return CompletableFuture.supplyAsync(()->{
			try {
				return  critic.forwarding(input);
			} catch (ArithmeticException e) {
				 throw new RuntimeException(e);
			}
		});
	}
	
	private CompletableFuture<double[]> forwardingActor(double[] input){
		return CompletableFuture.supplyAsync(()->{
			try {
				return  actor.forwarding(input);
			} catch (ArithmeticException e) {
				 throw new RuntimeException(e);
			}
		});
	}
	
	/**
	 * initialize the memory for the backPorpagation
	 */
	public void initBackPropagation() {
		memory.prepareData();
		critic.initBackPropagation();
		actor.initBackPropagation();
		System.out.println("episodi:"+episode);
	}
	
	/**
	 * perform the backPropagation for the critic and actor
	 * @return [mean loss actor, mean loss critic]
	 * @throws ArithmeticException 
	 */
	public double[] backPropagation() throws ArithmeticException {
		try {
			ArrayList<ActionRegister[]> miniBatches = memory.getMiniBatch();
			CompletableFuture<Double> procCritic = backPropCritic(miniBatches,episode);
			CompletableFuture<Double> procActor = backPropActor(miniBatches,episode);
			this.episode++;
			return new double[] {procActor.join(),procCritic.join()};
		}catch (RuntimeException e) {
			if (e.getCause() instanceof ArithmeticException) {
	            
	            throw (ArithmeticException) e.getCause();
	        }
	        throw e;
	    }
		
	}
	
	private  CompletableFuture<Double> backPropCritic(ArrayList<ActionRegister[]> miniBatches, long episode){
		return CompletableFuture.supplyAsync(()->{
			try {
				double meanB = 0;
				double meanE = 0;
				if (miniBatches == null || miniBatches.isEmpty()) return 0.0;
				for(int i=0; i<Hyperparameters.epoche+2; i++) {
					for(ActionRegister[] mb : miniBatches) {
						meanB += critic.backPropagation(mb,episode);
					}
					meanE += meanB/miniBatches.size();
					meanB = 0.0;
				}
				return ratioLoss * meanE/(Hyperparameters.epoche+2);
			}catch (ArithmeticException e) {
				 throw new RuntimeException(e);
			}
		});
	}
	
	private  CompletableFuture<Double> backPropActor(ArrayList<ActionRegister[]> miniBatches, long episode){
		return CompletableFuture.supplyAsync(()->{
			try {
				double meanB = 0;
				double meanE = 0;
				double meanEntropy = 0;
				int entropyCount = 0;
				
				if (miniBatches == null || miniBatches.isEmpty()) return 0.0;
				for(int i=0; i<Hyperparameters.epoche; i++) {
					for(ActionRegister[] mb : miniBatches) {
						meanB += actor.backPropagation(mb,episode);
						meanEntropy += actor.getEntropyLoss();
						entropyCount++;
					}
					actor.setEntropyLoss(0);
					meanE += meanB/miniBatches.size();
					meanB = 0.0;
				}
				if(entropyCount > 0) {
					this.meanEntropy = meanEntropy/(entropyCount);
				}
					
				return ratioLoss * meanE/Hyperparameters.epoche;
			}catch (ArithmeticException e) {
				 throw new RuntimeException(e);
			}
		});
	}
	
	/**
	 * perform the optimization of the parameters for critic and actor
	 */
	public void optimization() {
		critic.optimization();
		actor.optimization();
	}
	
	/**
	 * memorize actions to use for training session
	 * @param r
	 * @throws ArithmeticException
	 */
	public synchronized void memorizeActions(ActionRegister[] r) throws ArithmeticException {
		if(r == null) return;
		
		ActionRegister[] trainingData = new ActionRegister[r.length - 1];
		System.arraycopy(r, 0, trainingData, 0, r.length - 1);
		memory.addNewActions(trainingData,r[r.length-1].vEstimated);
	}
}

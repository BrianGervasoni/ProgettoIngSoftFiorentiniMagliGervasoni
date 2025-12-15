package model;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.nd4j.linalg.api.ndarray.INDArray;

import progettoAI.snakeAI.AI.AIActor;
import progettoAI.snakeAI.AI.AICritic;
import progettoAI.snakeAI.AI.TypeGradientUpdate;
import progettoAI.snakeAI.hyperparameters.Hyperparameters;
import progettoAI.snakeAI.tools.Tools;

public class Model {
	private AICritic critic;
	private AIActor actor;
	private transient PPOMemory memory;
	
	//TODO
	private transient int threadsAgentRunning = 0; //DA AGGIUNGERE A UML 
	private transient int threadsModelRunning = 0; //DA AGGIUNGERE A UML 
	private transient int threadsAgentWaiting = 0; //DA AGGIUNGERE A UML 
	private transient int threadsModelWaiting= 0; //DA AGGIUNGERE A UML
	private transient boolean initBackProp = true;
	private transient boolean initOptimization = false;
	
	//TODO
	final Lock lock = new ReentrantLock();
	final Condition threadsAgent = lock.newCondition(); 
	final Condition threadsModel = lock.newCondition();
	
	/**
	 * setup the default configuration (critic: 3X126 actor: 3X256)
	 */
	public Model() {
		memory = new PPOMemory();
		if(critic == null || actor == null) {
			critic = new AICritic(new int[] {61*3,126,126,126,1},TypeGradientUpdate.DESCEND);
			actor = new AIActor(new int[] {61*3,256,256,256,256,4},TypeGradientUpdate.ASCEND);
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
	
	/**
	 * return how many input node the model have
	 * @return
	 */
	public int getInputLenght() {
		return actor.getInputLenght();
	}

	/**
	 * perform the forwarding for the critic and actor
	 * @param input
	 * @return
	 */
	public ActionRegister forwarding(double[] input) {
		CompletableFuture<double[]> procCritic = forwardingCritic(input);
		CompletableFuture<double[]> procActor = forwardingActor(input);
		
		double[] resCritic = procCritic.join();
		double[] resActor = procActor.join();
		
		ActionRegister r = new ActionRegister();
		r.actionsProb = resActor;
		r.vEstimated = resCritic[0];
		r.state = input;
		return r;
	}
	
	private CompletableFuture<double[]> forwardingCritic(double[] input){
		return CompletableFuture.supplyAsync(()->{
			return  critic.forwarding(input);
		});
	}
	
	private CompletableFuture<double[]> forwardingActor(double[] input){
		return CompletableFuture.supplyAsync(()->{
			return  actor.forwarding(input);
		});
	}
	
	/**
	 * initialize the memory for the backPorpagation
	 */
	public void initBackPropagation() {
		memory.prepareData();
		critic.initBackPropagation();
		actor.initBackPropagation();
	}
	
	/**
	 * perform the backPropagation for the critic and actor
	 * @return [mean loss actor, mean loss critic]
	 */
	public double[] backPropagation() {
		CompletableFuture<Double> procCritic = backPropCritic();
		CompletableFuture<Double> procActor = backPropActor();
		
		return new double[] {procActor.join(),procCritic.join()};
	}
	
	private  CompletableFuture<Double> backPropCritic(){
		return CompletableFuture.supplyAsync(()->{
			INDArray mean = null;
			for(int i=0; i<Hyperparameters.epoche; i++) {
				mean = Tools.appendCol(mean,critic.backPropagation(memory.getMiniBatch()));
			}
			return mean.sum(1).mul(1/(double)Hyperparameters.epoche).sum(0).toDoubleVector()[0];
		});
	}
	
	private  CompletableFuture<Double> backPropActor(){
		return CompletableFuture.supplyAsync(()->{
			INDArray mean = null;
			for(int i=0; i<Hyperparameters.epoche; i++) {
				mean = Tools.appendCol(mean,actor.backPropagation(memory.getMiniBatch()));
			}
			return mean.sum(1).mul(1/(double)Hyperparameters.epoche).sum(0).toDoubleVector()[0];
		});
	}
	
	/**
	 * perform the optimization of the parameters for critic and actor
	 */
	public void optimization() {
		critic.optimization();
		actor.optimization();
	}
	
	public void memorizeActions(ActionRegister[] r) {
		memory.addNewActions(r);
	}
	
public void threadAgentReportThatItHasStarted() {
		
		lock.lock();
        try {
        	
            // AGENTI SI METTONO IN WAITING 
            while (this.threadsModelRunning > 0 && !initOptimization) {
            	threadsAgentWaiting ++;
                threadsAgent.await();
                threadsAgentWaiting--;
            }
            
            threadsAgentRunning++;
            
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
        
	}

	public void threadAgentReportThatItHasFinished() {
		
		lock.lock();
        try {
        	
        	threadsAgentRunning--;
        	threadsModel.signalAll(); 
        	
        	while (!initBackProp) {
            	threadsAgentWaiting ++;
                threadsAgent.await();
                threadsAgentWaiting--;
            }
            
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void threadModelReportsThatItHasStartedInizitBackProp() {

		lock.lock();
		try {
			
			initBackProp = true;
			
            while(this.threadsAgentRunning > 0) {
            	threadsModelWaiting ++;
                threadsModel.await();
                threadsModelWaiting--;
            }
            
            threadsModelRunning++;
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		
	}
	
	public void threadModelReportsThatItHasFinishedInizitBackProp() {
		
		lock.lock();
        try {
        	
        	threadsModelRunning--;
        	
        	initBackProp = false;
        	initOptimization = true;
        	
        	threadsAgent.signalAll(); 
        	
        } finally {
			lock.unlock();
		}
		
	}
	
	public void threadModelReportsThatItHasStartedOptimization() {

		lock.lock();
		try {
			
            while(this.threadsAgentRunning > 0) {
            	threadsModelWaiting ++;
                threadsModel.await();
                threadsModelWaiting--;
            }
            
            threadsModelRunning++;
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		
	}
	
	public void threadModelReportsThatItHasFinishedInizitOptimization() {
		
		lock.lock();
        try {
        	
        	threadsModelRunning--;
        	
        	initOptimization = false;
        	initBackProp = true;
        	
        	threadsAgent.signalAll(); 
	
        } finally {
			lock.unlock();
		}
		
	}
}

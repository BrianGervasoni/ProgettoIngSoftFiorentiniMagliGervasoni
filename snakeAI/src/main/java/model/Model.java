package model;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.nd4j.linalg.api.ndarray.INDArray;

import errorHandler.ArithmeticException;
import progettoAI.snakeAI.AI.AIActor;
import progettoAI.snakeAI.AI.AICritic;
import progettoAI.snakeAI.AI.TypeGradientUpdate;
import progettoAI.snakeAI.hyperparameters.Hyperparameters;
import progettoAI.snakeAI.tools.Tools;

public class Model {
	private AICritic critic;
	private AIActor actor;
	private transient PPOMemory memory;
	
	private transient int threadsAgentRunning = 0; //DA AGGIUNGERE A UML 
	private transient int threadsModelRunning = 0; //DA AGGIUNGERE A UML 
	private transient int threadsAgentWaiting = 0; //DA AGGIUNGERE A UML 
	private transient int threadsModelWaiting= 0; //DA AGGIUNGERE A UML
	private transient boolean initBackProp = false;
	private transient boolean initOptimization = false;
	
	private transient final Lock lock = new ReentrantLock();
	private transient final Condition threadsAgent = lock.newCondition(); 
	private transient final Condition threadsModel = lock.newCondition();
	
	private transient final Lock lock1 = new ReentrantLock();
	private transient final Condition sendActions = lock.newCondition(); 
	
	/**
	 * setup the default configuration (critic: 3X126 actor: 3X256)
	 */
	public Model() {
		memory = new PPOMemory();
		if(critic == null || actor == null) {
			critic = new AICritic(new int[] {61*3,126,126,126,1},TypeGradientUpdate.DESCEND);
			actor = new AIActor(new int[] {61*3,256,256,256,256,3},TypeGradientUpdate.ASCEND);
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
	}
	
	/**
	 * perform the backPropagation for the critic and actor
	 * @return [mean loss actor, mean loss critic]
	 * @throws ArithmeticException 
	 */
	public double[] backPropagation() throws ArithmeticException {
		try {
			CompletableFuture<Double> procCritic = backPropCritic();
			CompletableFuture<Double> procActor = backPropActor();
			return new double[] {procActor.join(),procCritic.join()};
		}catch (RuntimeException e) {
			if (e.getCause() instanceof ArithmeticException) {
	            
	            throw (ArithmeticException) e.getCause();
	        }
	        throw e;
	    }
		
	}
	
	private  CompletableFuture<Double> backPropCritic(){
		return CompletableFuture.supplyAsync(()->{
			try {
				INDArray mean = null;
				for(int i=0; i<Hyperparameters.epoche; i++) {
					mean = Tools.appendCol(mean,critic.backPropagation(memory.getMiniBatch()));
				}
				if(mean != null)
					return mean.sum(1).mul(1/(double)Hyperparameters.epoche).sum(0).toDoubleVector()[0];
				return 0.0;
			}catch (ArithmeticException e) {
				 throw new RuntimeException(e);
			}
		});
	}
	
	private  CompletableFuture<Double> backPropActor(){
		return CompletableFuture.supplyAsync(()->{
			try {
				INDArray mean = null;
				for(int i=0; i<Hyperparameters.epoche; i++) {
					mean = Tools.appendCol(mean,actor.backPropagation(memory.getMiniBatch()));
				}
				if(mean != null)
					return mean.sum(1).mul(1/(double)Hyperparameters.epoche).sum(0).toDoubleVector()[0];
				return 0.0;
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
	
	public void memorizeActions(ActionRegister[] r) {
		memory.addNewActions(r);
	}
	
	public void startingSendActions() {
		lock1.lock();
	}
	
	public void terminatingSendActions() {
		lock1.unlock();
	}
	
	public void threadAgentReportThatItHasStarted() {
		
		lock.lock();
        try {
        	
        	
            // AGENTI SI METTONO IN WAITING 
            while (this.threadsModelRunning > 0 || initOptimization || initBackProp) {
            	threadsAgentWaiting ++;
                threadsAgent.await();
                threadsAgentWaiting--;
            }
            threadsAgentRunning++;
            
        } catch (InterruptedException e) {
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
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		
	}
	
	public void threadModelReportsThatItHasFinishedInizitBackProp() {
		
		lock.lock();
        try {
        	
        	initBackProp = false;

        	threadsModelRunning--;

        	threadsAgent.signalAll(); 
        	
        } finally {
			lock.unlock();
		}
		
	}
	
	public void threadModelReportsThatItHasStartedOptimization() {

		lock.lock();
		try {
			
			initOptimization = true;
			
            while(this.threadsAgentRunning > 0) {
            	threadsModelWaiting ++;
                threadsModel.await();
                threadsModelWaiting--;
            }
            
            threadsModelRunning++;
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		
	}
	
	public void threadModelReportsThatItHasFinishedInizitOptimization() {
		
		lock.lock();
        try {
        	
        	initOptimization = false;
        	
        	threadsModelRunning--;
        	
        	threadsAgent.signalAll(); 
	
        } finally {
			lock.unlock();
		}
		
	}
}

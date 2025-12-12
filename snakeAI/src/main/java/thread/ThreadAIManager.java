package thread;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadAIManager {

	List<ThreadAgent> threadAgents;
	List<ThreadModel> threadModels;
	int threadsAgentNumber; //number threads agent PER thread model
	int threadsModelNumber;
	
	private int threadAgentsThatHasFinished = 0; //DA AGGIUNGERE A UML it tells how many threadAgents has finished and are waiting
	private int threadsModelThatHasFinishedBackProp = 0; //DA AGGIUNGERE A UML it tells how many threadsModel have finished the back propagation
	
	private boolean threadsAgentsAreRunning = true; //DA AGG A UML
	
	final Lock lock = new ReentrantLock();
	final Condition threadsAgent = lock.newCondition(); 
	final Condition threadsModel = lock.newCondition();
	
	public ThreadAIManager(int nAgents, int nModels) {
		this.threadsAgentNumber = nAgents;
		this.threadsModelNumber = nModels;
	}
	
	public void createIstance(){
	
		String relPath = "modelli/";
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		scanner.close();
		
		for(int i = 0; i<this.threadsModelNumber; i++) {
			
			Path relativePath = Paths.get(relPath + name + i + ".json");
			Path absolutePath = relativePath.toAbsolutePath();
			ThreadModel threadModel = new ThreadModel(absolutePath.toString());
			threadModels.add(threadModel);
			threadModel.setAIManager(this);
			
			for(int j = 0; j<this.threadsAgentNumber; j++) {
				ThreadAgent threadAgent = new ThreadAgent(threadModel.model);
				threadAgents.add(threadAgent);
				threadAgent.setAIManager(this);
			}
		}
		
	}
	
	public void startIstances(){
		
		for(ThreadAgent threadAgent : threadAgents) { //remember threadsAgentNumber = number of agents PER threadsModel, while threadAgents.size() = number of total agents
			threadAgent.start();
		}
		
		for(ThreadModel threadModel : threadModels) {
			threadModel.start();
		}
		
	}
	
	//DA AGGIUNGERE A UML
	public void alternateThreads(){
		
		while (true) {
			
            lock.lock();
            try {
                while (this.threadsModelThatHasFinishedBackProp < threadModels.size()) {
                	threadsAgent.await(); 
                	//AGENTS DEVONO ASPETTARE CHE MODELS FINISCANO BACK PROPAGATION
                }
                
                this.threadsModelThatHasFinishedBackProp = 0;
                this.threadAgentsThatHasFinished = 0;
                
                threadsAgent.signalAll(); //AGENTS POSSONO RICOMINCIARE
                
            } catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} finally {
    			lock.unlock();
    		}
        }
	
	}
	
	public void threadAgentReportThatItHasFinished() {
		
		lock.lock();
        try {
        	
            this.threadAgentsThatHasFinished++;
            
            if (this.threadAgentsThatHasFinished == threadAgents.size()) {
            	
            	this.threadsAgentsAreRunning = false;
            	this.threadsModelThatHasFinishedBackProp = 0;
            	threadsModel.signalAll(); // SVEGLIA I MODELLI
            }
            
            // AGENTI SI METTONO IN WAITING DA QUANDO FANNO SEND ACTIONS E ASPETTANO CHE I THREADS MODEL FINISCANO BACKPROP 
            while (this.threadsModelThatHasFinishedBackProp < this.threadsModelNumber) {
                threadsAgent.await();
            }
            
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
        
	}

	public void threadModelReportsThatItHasFinishedBackProp() {

		lock.lock();
		try {
			
			this.threadsModelThatHasFinishedBackProp++;
			
            if (this.threadsModelThatHasFinishedBackProp == this.threadsModelNumber) {
                
                this.threadAgentsThatHasFinished = 0; 
                this.threadsAgentsAreRunning = true; 
                threadsAgent.signalAll(); 
            }
			
		} finally {
			lock.unlock();
		}
		
	}
	
	public void threadsModelWaitForThreadsAgentToFinish() {
		
		lock.lock();
        try {
        	
        	// MODELLI IN ATTESA DEGLI AGENTI CHE FINISCANO
            while (this.threadsAgentsAreRunning == true) { 
                threadsModel.await();
            }
            
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		
	}
	
	public void computeStatics(){
		
	}

}


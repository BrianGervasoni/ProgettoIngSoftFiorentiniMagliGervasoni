package thread;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import errorHandler.ThreadException;
import gioco.snakeAI.Map;
import io.reactivex.rxjava3.core.Observable;

public class ThreadAIManager{

	List<ThreadAgent> threadAgents;
	List<ThreadModel> threadModels;
	int threadsAgentNumber; //number threads agent PER thread model
	int threadsModelNumber;
	
	private int mapIndex = 0; //for first time i've arbitrary decided that it will show the first map
	
	public ThreadAIManager(int nAgents, int nModels) {
		this.threadsAgentNumber = nAgents;
		this.threadsModelNumber = nModels;
	}
	
	public void createIstance(String name) throws IOException{
	
		String relPath = "modelli/";
		
		List<ThreadAgent> threadAgents = new ArrayList<>();
		List<ThreadModel> threadModels = new ArrayList<>();
		
		this.threadAgents = threadAgents;
		this.threadModels = threadModels;
		
		for(int i = 0; i<this.threadsModelNumber; i++) {
			
			Path relativePath;
			if(i == 0) {//the first file dosn't get the index number
				relativePath = Paths.get(relPath + name + ".json");
			}else {
				relativePath = Paths.get(relPath + name + i + ".json");
			}
			Path absolutePath = relativePath.toAbsolutePath();
			Syncronizer coord = new Syncronizer(this.threadsAgentNumber);
			ThreadModel threadModel = new ThreadModel(absolutePath.toString(),coord);

			
			threadModels.add(threadModel);
			
			for(int j = 0; j<this.threadsAgentNumber; j++) {
				ThreadAgent threadAgent = new ThreadAgent(threadModel.getModel(),coord);
				threadAgents.add(threadAgent);
			}
		}
		
	}
	
	public void setAgentExceptionHandler(Consumer<Throwable> onWarning) {
		if(threadAgents == null || onWarning == null)
			return;
		for(ThreadAgent threadAgent : threadAgents) { //remember threadsAgentNumber = number of agents PER threadsModel, while threadAgents.size() = number of total agents
			threadAgent.setUncaughtExceptionHandler((t, e) ->{
				onWarning.accept(e);
			});
		}
	}
	
	public void setModelExceptionHandler(Consumer<Throwable> onWarning) {
		if(threadModels == null || onWarning == null)
			return;
		for(ThreadModel threadModel : threadModels) { //remember threadsAgentNumber = number of agents PER threadsModel, while threadAgents.size() = number of total agents
			threadModel.setUncaughtExceptionHandler((t, e) ->{
				onWarning.accept(e);
			});
		}
	}
	
	private void startThreadsAgent(boolean lockSpeed) throws ThreadException {

		try {
			for(ThreadAgent threadAgent : threadAgents) { //remember threadsAgentNumber = number of agents PER threadsModel, while threadAgents.size() = number of total agents
				threadAgent.setLockSpeed(lockSpeed);
				threadAgent.start();
			}
		}catch(NullPointerException e) {
			throw new ThreadException("fallimento nello start dei thread agent: " + e.getMessage(),e.getCause());
		}
		
	}
	
	private void startThreadsModel() throws ThreadException{
		try {
			for(ThreadModel threadModel : threadModels) {
				threadModel.start();
			}
		}catch(NullPointerException e) {
			throw new ThreadException("fallimento nello start dei thread model: "  + e.getMessage(),e.getCause());
		}
		
		
	}
	
	private void terminateThreadsAgent(){
		if(threadAgents == null)
			return;
		
		for(ThreadAgent threadAgent : threadAgents) { 
			threadAgent.interrupt();
		}
		
		 for (ThreadAgent threadAgent : threadAgents) { 
		    if (threadAgent != null) {
		        try {
		            threadAgent.join();
		        } catch (InterruptedException e) {
		            // Ripristiniamo il segnale per il thread chiamante
			        Thread.currentThread().interrupt();
			        return;
		        }
		    }
		 }
	}
	
	private void terminateThreadsModel() throws IOException,FileNotFoundException{
		if(threadModels == null)
			return;
		
		for(ThreadModel threadModel : threadModels) {
			threadModel.interrupt();
		}
		
		for(ThreadModel threadModel : threadModels) {
			if (threadModel != null) {
		        try {
		            threadModel.join();
		        } catch (InterruptedException e) {
		            // Ripristiniamo il segnale per il thread chiamante
			        Thread.currentThread().interrupt();
			        return;
		        }
		    }
		}
	}
	
	public void startTraining() throws ThreadException{
		this.startThreadsAgent(false);
		this.startThreadsModel();
	}
	
	public void startExecution() throws ThreadException{
		this.startThreadsAgent(true);
	}
	
	public void terminateTraining() throws FileNotFoundException, IOException {
		this.terminateThreadsAgent();
		this.terminateThreadsModel();
	}
	
	public void terminateExecution(){
		this.terminateThreadsAgent();
	}
	
	/**
	 * terminate all threads
	 */
	public void terminate() throws ThreadException,FileNotFoundException,IOException{
		if(threadAgents != null) {
			this.terminateThreadsAgent();
		}
		
		if(threadModels != null) {
			this.terminateThreadsModel();
		}
	}
	
	private Map selectFirstMap() {
		return this.threadAgents.get(0).getGame().getMap();
	}
	
	private Map selectIndexMap(int index) {
		return this.threadAgents.get(index).getGame().getMap();
	}
	
	public ThreadAgent selectIndexAgent(int index) {
		return this.threadAgents.get(index);
	}
	
	private Map selectLastMap() {
		return this.threadAgents.get(this.threadAgents.size() - 1).getGame().getMap();
	}
	
	/**
	 * 
	 * @return the next threads agent's map
	 */
	public void selectNextMap() {
		
		if(mapIndex == this.threadsAgentNumber-1) {
			mapIndex = 0;
		}else {
			mapIndex ++;
		}
	}
	
	/**
	 * 
	 * @return the previous threads agent'map
	 */
	public void selectPreviousMap() {
		
		if(mapIndex == 0) {
			mapIndex = this.threadsAgentNumber-1;
		}else {
			mapIndex --;
		}
	}
	
	/**
	 * 
	 * @return the actual map selected
	 */
	public Map selectMap() {
		
		if(mapIndex == 0) {
			return this.selectFirstMap();
		}else if(mapIndex == this.threadsAgentNumber) {
			return this.selectLastMap();
		}
		else {
			return this.selectIndexMap(mapIndex);
		}
	}
	
	/**
	 * get the observable from the selected agent to start get a stream of data
	 * @return
	 */
	public Observable<Map> getObserverFromIndexAgent(){
		return this.threadAgents.get(mapIndex).observableMap();
	}
	
	/**
	 * get the observable from the first model to start get a stream of data
	 * @return
	 */
	public Observable<double[]> getObserverFromModel(){
		return this.threadModels.get(0).observableLoss();
	}
}


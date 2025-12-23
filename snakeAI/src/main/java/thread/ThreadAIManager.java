package thread;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import gioco.snakeAI.Map;

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
	
	public void createIstance(String name){
	
		String relPath = "modelli/";
		
		List<ThreadAgent> threadAgents = new ArrayList<>();
		List<ThreadModel> threadModels = new ArrayList<>();
		
		this.threadAgents = threadAgents;
		this.threadModels = threadModels;
		
		for(int i = 0; i<this.threadsModelNumber; i++) {
			
			Path relativePath = Paths.get(relPath + name + i + ".json");
			Path absolutePath = relativePath.toAbsolutePath();
			ThreadModel threadModel = new ThreadModel(absolutePath.toString(), this.threadsAgentNumber);
			threadModels.add(threadModel);
			
			for(int j = 0; j<this.threadsAgentNumber; j++) {
				ThreadAgent threadAgent = new ThreadAgent(threadModel.getModel());
				threadAgents.add(threadAgent);
			}
		}
		
	}
	
	private void startThreadsAgent() {
		for(ThreadAgent threadAgent : threadAgents) { //remember threadsAgentNumber = number of agents PER threadsModel, while threadAgents.size() = number of total agents
			threadAgent.start();
		}
	}
	
	private void startThreadsModel() {
		for(ThreadModel threadModel : threadModels) {
			threadModel.start();
		}
	}
	
	private void terminateThreadsAgent() {
		for(ThreadAgent threadAgent : threadAgents) { 
			threadAgent.interrupt();
		}
	}
	
	private void terminateThreadsModel() {
		for(ThreadModel threadModel : threadModels) {
			threadModel.save();
			threadModel.interrupt();
		}
	}
	
	public void startTraining(){
		this.startThreadsAgent();
		this.startThreadsModel();
	}
	
	public void startExecution() {
		this.startThreadsAgent();
	}
	
	public void terminateTraining() {
		this.terminateThreadsAgent();
		this.terminateThreadsModel();
	}
	
	public void terminateExecution() {
		this.terminateThreadsAgent();
	}
	
	public void computeStatics(){
		/*
		 * TODO STATISTICHE DELLA FASE DI ESECUZIONE E ALLENAMENTO
		 */
	}
	
	private Map selectFirstMap() {
		return this.threadAgents.get(0).getGame().getMap();
	}
	
	private Map selectIndexMap(int index) {
		return this.threadAgents.get(index).getGame().getMap();
	}
	
	private Map selectLastMap() {
		return this.threadAgents.get(this.threadAgents.size() - 1).getGame().getMap();
	}
	
	/**
	 * 
	 * @return the next threads agent's map
	 */
	public void selectNextMap() {
		
		if(mapIndex == this.threadsAgentNumber) {
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
			mapIndex = this.threadsAgentNumber;
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

}


package thread;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	public void createIstance(){
	
		String relPath = "modelli/";
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		scanner.close();
		
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
				ThreadAgent threadAgent = new ThreadAgent(threadModel.model);
				threadAgents.add(threadAgent);
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
	
	public void computeStatics(){
		/*
		 * STATISTICHE DELLA FASE DI ESECUZIONE E ALLENAMENTO
		 */
	}
	
	private Map selectFirstMap() {
		return this.threadAgents.get(0).game.getMap();
	}
	
	private Map selectNextMap(int index) {
		return this.threadAgents.get(index).game.getMap();
	}
	
	private Map selectLastMap() {
		return this.threadAgents.get(this.threadAgents.size() - 1).game.getMap();
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
			return this.selectNextMap(mapIndex);
		}
	}

}


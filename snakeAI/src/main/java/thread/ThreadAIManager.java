package thread;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreadAIManager{

	List<ThreadAgent> threadAgents;
	List<ThreadModel> threadModels;
	int threadsAgentNumber; //number threads agent PER thread model
	int threadsModelNumber;
	
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
		
	}

}


package thread;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ThreadAIManager {

	List<ThreadAgent> threadAgents;
	List<ThreadModel> threadModels;
	int threadsAgentNumber;
	int threadsModelNumber;
	
	public ThreadAIManager(int nAgents, int nModels) {
		this.threadsAgentNumber = nAgents;
		this.threadsModelNumber = nModels;
	}
	
	public void createIstance(){
	
		String relPath = "target/modelli/";
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		scanner.close();
		
		for(int i = 0; i<this.threadsModelNumber; i++) {
			
			Path relativePath = Paths.get(relPath + name + i + ".json");
			Path absolutePath = relativePath.toAbsolutePath();
			ThreadModel threadModel = new ThreadModel(absolutePath.toString());
			threadModels.add(threadModel);
			
			for(int j = 0; j<this.threadsAgentNumber; j++) {
				ThreadAgent threadAgent = new ThreadAgent(threadModel.model);
				threadAgents.add(threadAgent);
			}
		}
		
	}
	
	public void startIstances(){
		
		for(int i = 0; i<this.threadsAgentNumber; i++) {
			threadAgents.get(i).start();
		}
		
		//MODELS DEVONO ATTIVARSI DOPO CHE TUTTI AGENTS HANNO FATTO SENDS ACTIONS
		for(int i = 0; i<this.threadsModelNumber; i++) {
			threadModels.get(i).start();
		}
		
	}
	
	public void computeStatics(){
		
	}
}


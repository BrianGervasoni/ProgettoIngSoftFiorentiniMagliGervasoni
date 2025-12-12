package thread;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import fileManager.JsonFileManager;

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
		Path relativePath = Paths.get(relPath);
		Path absolutePath = relativePath.toAbsolutePath();
		
		for(int i = 0; i<this.threadsModelNumber; i++) {
			
			ThreadModel threadModel = new ThreadModel(absolutePath.toString());
			threadModels.add(threadModel);
			
			for(int j = 0; j<this.threadsAgentNumber; j++) {
				ThreadAgent threadAgent = new ThreadAgent(threadModel.model);
				threadAgents.add(threadAgent);
			}
		}
		
		
	
	}
	
	public void startIstances(){
		
	}
	
	public void computeStatics(){
		
	}
}


package thread;

import java.util.List;

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
		
		for(int i = 0; i<this.threadsModelNumber; i++) {
			
			ThreadModel threadModel = new ThreadModel("../modelli/modello.Json");
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


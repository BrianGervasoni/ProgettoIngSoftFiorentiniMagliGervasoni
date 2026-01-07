package thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import errorHandler.ThreadException;
import model.Model;

public class Main {

	public static void main(String[] args) {
		
		//SOLO PER QUESTIONI DI TEST !!!!
		//ADESSO è OBSOLETO PERCHE CI SONO OGGETTI VUOTI QUA DENTRO
		Model model = new Model();
		ThreadAIManager manager = new ThreadAIManager(2,1);
		
		List<ThreadAgent> threadAgents = new ArrayList<>();
		List<ThreadModel> threadModels = new ArrayList<>();
		
		for(int i = 0; i<1; i++) {
			ThreadModel threadModel = null;
			try {
				threadModel = new ThreadModel("hello",2);
			}catch(IOException e) {
				e.printStackTrace();
			}
			
			threadModels.add(threadModel);
			threadModel.setModel(model);
			
			for(int j = 0; j<2; j++) {
				ThreadAgent threadAgent = new ThreadAgent(threadModel.getModel());
				threadAgents.add(threadAgent);
				threadAgent.setModel(model);
			}
		}
		
		manager.threadAgents = threadAgents;
		manager.threadModels = threadModels;
		try {
			manager.startTraining();
		}catch(ThreadException e) {
			System.err.println(e.getMessage());
		}
		

	}

}

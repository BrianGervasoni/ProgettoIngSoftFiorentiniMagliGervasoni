package ai;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.nd4j.linalg.factory.Nd4j;

import errorHandler.ArithmeticException;
import fileManager.JsonFileManager;
import model.ActionRegister;
import model.Model;
import progettoAI.snakeAI.AI.*;
import progettoAI.snakeAI.hyperparameters.Hyperparameters;
public class ModelTest {

	@Test
	void testSaveAndLoad() {
		try {
			//AICritic critic = new AICritic(new int[] {4,4,4,1},TypeGradientUpdate.DESCEND);
			//AIActor actor = new AIActor(new int[] {4,5,5,4},TypeGradientUpdate.ASCEND);
			//Model mSave = new Model(critic,actor);
			Model m = null;
			
			String relPath = "modelli/modelloTest.json";
			Path relativePath = Paths.get(relPath);
			Path absolutePath = relativePath.toAbsolutePath();
			//JsonFileManager.saveModel(mSave, absolutePath.toString());
			try {
				m = JsonFileManager.loadModel(absolutePath.toString());
			}catch(IOException e) {
				e.printStackTrace();
			}
			
			ActionRegister r= m.forwarding(new double[] {1,2,3,4});
			r.reward = 4;
			r.indexAction = 2;
			
			m.memorizeActions(new ActionRegister[] {r});
			m.initBackPropagation();
			
			double[] test =  m.backPropagation();
			
			double[] trueLoss = new double[] {4.652735277428321,15.682484034489768};
			assertArrayEquals(trueLoss,test);
			assertNotNull(m.getMemory());
		}catch(ArithmeticException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void testSaveAndLoadHyperparameters() {
		String relPath = "iperparametri/iperparametriTest.json";
		Path relativePath = Paths.get(relPath);
		Path absolutePath = relativePath.toAbsolutePath();
		Hyperparameters.alphaCritic = 0.1;
		try {
			JsonFileManager.saveHyperparameters(absolutePath.toString());
		}catch(IOException e) {
			e.printStackTrace();
		}
		Hyperparameters.alphaCritic = 0.3;
		try {
			JsonFileManager.loadHyperparameters(absolutePath.toString());
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(0.1,Hyperparameters.alphaCritic);
	}
	@Test
	void testBackProp() {
		Model ai = new Model();
		try {
			double[] e = new double[61*3];
			for(double f: e) {
				f = 1;
			}
			ActionRegister r = ai.forwarding(e);
			r.indexAction = 0;
			r.reward = 4;
			ai.memorizeActions(new ActionRegister[] {r});
			
			ai.initBackPropagation();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		assertEquals(3,ai.getActor().getLayer().get(ai.getActor().getLayer().size()-1).getWeights().getColumn(0).length());
		assertEquals(1,ai.getCritic().getLayer().get(ai.getCritic().getLayer().size()-1).getWeights().getColumn(0).length());
		
	}
}

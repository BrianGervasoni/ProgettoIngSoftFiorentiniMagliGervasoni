package ai;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.nd4j.linalg.factory.Nd4j;

import fileManager.JsonFileManager;
import model.ActionRegister;
import model.Model;
import progettoAI.snakeAI.AI.*;
public class ModelTest {

	@Test
	void testSaveAndLoad() {
		//AICritic critic = new AICritic(new int[] {4,4,4,1},TypeGradientUpdate.DESCEND);
		//AIActor actor = new AIActor(new int[] {4,5,5,4},TypeGradientUpdate.ASCEND);
		//Model mSave = new Model(critic,actor);
		Model m = null;
		
		String relPath = "modelli/modelloTest.json";
		Path relativePath = Paths.get(relPath);
		Path absolutePath = relativePath.toAbsolutePath();
		//JsonFileManager.saveModel(mSave, absolutePath.toString());
		m = JsonFileManager.loadModel(absolutePath.toString());
		ActionRegister r= m.forwarding(new double[] {1,2,3,4});
		r.reward = 4;
		r.indexAction = 2;
		
		m.memorizeActions(new ActionRegister[] {r});
		m.initBackPropagation();
		
		double[] test =  m.backPropagation();
		
		double[] trueLoss = new double[] {4.652735277428321,15.682484034489768};
		assertArrayEquals(trueLoss,test);
		assertNotNull(m.getMemory());
	}
}

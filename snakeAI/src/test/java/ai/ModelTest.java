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
		AICritic critic = new AICritic(new int[] {4,4,4,1},TypeGradientUpdate.DESCEND);
		AIActor actor = new AIActor(new int[] {4,5,5,4},TypeGradientUpdate.ASCEND);
		Model m = new Model(critic,actor);
		String relPath = "target/modelli/modello.json";
		Path relativePath = Paths.get(relPath);
		Path absolutePath = relativePath.toAbsolutePath();
		JsonFileManager.saveModel(m, absolutePath.toString());
		JsonFileManager.loadModel(absolutePath.toString());
		ActionRegister r= m.forwarding(new double[] {1,2,3,4});
		r.reward = 4;
		r.indexAction = 2;
		for(double s: r.actionsProb)
			System.out.print(s+"|");
		m.memorizeActions(new ActionRegister[] {r});
		m.initBackPropagation();
		
		m.backPropagation();
	}
}

package ai;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.nd4j.linalg.factory.Nd4j;

import fileManager.JsonFileManager;
import model.ActionRegister;
import model.Model;
import progettoAI.snakeAI.AI.*;
public class ModelTest {

	@Test
	void testSaveAndLoad() {
		Model m = new Model();
		String relPath = "modelli";
		URL path = getClass().getClassLoader().getResource(relPath);
		String resource = "/modello.json";
		String filePath = path.toString()+resource;
		File file = new File(filePath);
        File parentDir = file.getParentFile();
        System.out.print(filePath);
        if(!parentDir.exists()) {
        	System.out.print("ssssssssssssssssssss");
        }
		//JsonFileManager.saveModel(m, filePath);
	}
}

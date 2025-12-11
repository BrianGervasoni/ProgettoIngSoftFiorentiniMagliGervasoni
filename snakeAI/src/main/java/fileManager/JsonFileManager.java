package fileManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.nd4j.linalg.activations.IActivation;
import org.nd4j.linalg.api.ndarray.INDArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Model;

public class JsonFileManager {
	// GsonBuilder is used to format JSON in a readable way (pretty printing)
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(INDArray.class, new INDArrayAdapter())
    		.registerTypeAdapter(IActivation.class, new IActivationAdapter()).setPrettyPrinting().create();
    
	public static void saveModel(Model m,String dirFile) {
		try (FileWriter writer = new FileWriter(dirFile)) {
            // Serializes the model objects into a JSON string
            GSON.toJson(m, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static Model loadModel(String dirFile) {
		 try (FileReader reader = new FileReader(dirFile)) {
	            return GSON.fromJson(reader, Model.class);
	        } catch (FileNotFoundException e) {
	           return new Model();
	        } catch (IOException e) {
	            System.err.println("Errore durante la lettura del file: " + e.getMessage());
	            e.printStackTrace();
	            return null;
	        }
	}
}

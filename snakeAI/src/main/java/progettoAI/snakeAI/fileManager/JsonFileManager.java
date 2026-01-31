package progettoAI.snakeAI.fileManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.nd4j.linalg.activations.IActivation;
import org.nd4j.linalg.api.ndarray.INDArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import progettoAI.snakeAI.AI.AI;
import progettoAI.snakeAI.AI.Layer;
import progettoAI.snakeAI.AI.hyperparameters.Hyperparameters;
import progettoAI.snakeAI.AI.model.Model;

public class JsonFileManager {
	// GsonBuilder is used to format JSON in a readable way (pretty printing)
    private static final Gson GSON = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
    		.registerTypeHierarchyAdapter(INDArray.class, new INDArrayAdapter())
    		.registerTypeAdapter(IActivation.class, new IActivationAdapter()).
    		registerTypeAdapter(Layer.class, new LayerAdapter())
    		.registerTypeHierarchyAdapter(AI.class, new AIAdapter()).setPrettyPrinting().create();
    
	public static void saveModel(Model m,String dirFile) throws IOException,FileNotFoundException {
		try (FileWriter writer = new FileWriter(dirFile)) {
            // Serializes the model objects into a JSON string
            GSON.toJson(m, writer);
        }catch (FileNotFoundException e) {
        	System.err.println("file non trovato: " + e.getMessage());
        	throw new FileNotFoundException(e.getMessage());
        }
		catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static Model loadModel(String dirFile) throws IOException,FileNotFoundException {
		 try (FileReader reader = new FileReader(dirFile)) {
	            return GSON.fromJson(reader, Model.class);
	        } catch (FileNotFoundException e) {
	        	System.err.println("file non trovato: " + e.getMessage());
	        	throw new FileNotFoundException(e.getMessage());
	        } catch (IOException e) {
	            System.err.println("Errore durante la lettura del file: " + e.getMessage());
	            e.printStackTrace();
	            throw new IOException(e.getMessage(),e.getCause());
	        }
	}
	
	public static void saveHyperparameters(String dirFile) throws IOException,FileNotFoundException {
		try (FileWriter writer = new FileWriter(dirFile)) {
            GSON.toJson(new Hyperparameters(), writer);
        } catch (FileNotFoundException e) {
        	System.err.println("file non trovato: " + e.getMessage());
        	throw new FileNotFoundException(e.getMessage());
        }catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage(),e.getCause());
        }
	}
	
	public static void loadHyperparameters(String dirFile) throws IOException,FileNotFoundException {
		 try (FileReader reader = new FileReader(dirFile)) {
	            GSON.fromJson(reader, Hyperparameters.class);
	        } catch (FileNotFoundException e) {
	        	System.err.println("file con iperparametri non trovato: " + e.getMessage());
	        	throw new FileNotFoundException(e.getMessage());
	        } catch (IOException e) {
	            System.err.println("Errore durante la lettura del file dei iperparametri: " + e.getMessage());
	            throw new IOException(e.getMessage(),e.getCause());
	        }
	}
}

package fileManager;
import com.google.gson.*;

import progettoAI.snakeAI.AI.AI;
import progettoAI.snakeAI.AI.AIActor;
import progettoAI.snakeAI.AI.AICritic;
import progettoAI.snakeAI.AI.Layer;
import progettoAI.snakeAI.hyperparameters.Hyperparameters;

import java.lang.reflect.Type;

import org.nd4j.linalg.activations.IActivation;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.lang.reflect.Modifier;

public class AIAdapter implements JsonDeserializer<AI> {
	 private static final Gson AI_GSON = new GsonBuilder()
	            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
	            .registerTypeAdapter(Layer.class, new LayerAdapter()) 
	            .registerTypeHierarchyAdapter(INDArray.class, new INDArrayAdapter())
	            .registerTypeAdapter(IActivation.class, new IActivationAdapter()).setPrettyPrinting().create();
    @Override
    public AI deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    	
        AI ai = AI_GSON.fromJson(json, typeOfT);

        if (ai instanceof AICritic) {
            ai.setLearningRate(Hyperparameters.alphaCritic);
        } else if (ai instanceof AIActor) {
            ai.setLearningRate(Hyperparameters.alphaActor);
        } else {
            ai.setLearningRate(Hyperparameters.alphaCritic);
        }

        return ai;
    }
}
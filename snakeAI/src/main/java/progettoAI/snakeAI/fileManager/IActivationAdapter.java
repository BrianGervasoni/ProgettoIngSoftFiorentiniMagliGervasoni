package progettoAI.snakeAI.fileManager;
import com.google.gson.*;

import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.activations.IActivation;

import java.lang.reflect.Type;

public class IActivationAdapter implements JsonSerializer<IActivation>, JsonDeserializer<IActivation> {

    @Override
    public JsonElement serialize(IActivation src, Type typeOfSrc, JsonSerializationContext context) {
        // save only the name (es. "relu", "sigmoid")
        String activationName = src.toString(); 
        return new JsonPrimitive(activationName);
    }

    @Override
    public IActivation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String activationName = json.getAsString();
        
        // Use the DL4J utility class 'Activation' to map the string to the correct object
        try {
            return Activation.fromString(activationName).getActivationFunction();
        } catch (IllegalArgumentException e) {
            throw new JsonParseException("Nome di attivazione sconosciuto: " + activationName, e);
        }
    }
}

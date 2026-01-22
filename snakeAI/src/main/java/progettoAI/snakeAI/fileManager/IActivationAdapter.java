package progettoAI.snakeAI.fileManager;
import com.google.gson.*;

import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.activations.IActivation;
import org.nd4j.linalg.activations.impl.ActivationLReLU;

import java.lang.reflect.Type;

public class IActivationAdapter implements JsonSerializer<IActivation>, JsonDeserializer<IActivation> {

    @Override
    public JsonElement serialize(IActivation src, Type typeOfSrc, JsonSerializationContext context) {
    	if (src instanceof ActivationLReLU) {
            ActivationLReLU act = (ActivationLReLU) src;
            return new JsonPrimitive("leakyrelu:" + act.getAlpha());
        }
        // save only the name (es. "relu", "sigmoid")
        String activationName = src.toString(); 
        return new JsonPrimitive(activationName);
    }

    @Override
    public IActivation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String activationName = json.getAsString();
        // gestione LeakyReLU con alpha
        if (activationName.startsWith("leakyrelu:")) {
            double alpha = Double.parseDouble(activationName.split(":")[1]);
            return new ActivationLReLU(alpha);
        }
        // Use the DL4J utility class 'Activation' to map the string to the correct object
        try {
            return Activation.fromString(activationName).getActivationFunction();
        } catch (IllegalArgumentException e) {
            throw new JsonParseException("Nome di attivazione sconosciuto: " + activationName, e);
        }
    }
}

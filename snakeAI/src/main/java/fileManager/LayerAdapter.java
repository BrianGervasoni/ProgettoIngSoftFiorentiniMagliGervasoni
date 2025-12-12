package fileManager;

import com.google.gson.*;

import progettoAI.snakeAI.AI.*;

import java.lang.reflect.Type;

public class LayerAdapter implements JsonSerializer<Layer>, JsonDeserializer<Layer> {

    private static final String TYPE_FIELD_NAME = "layerType";

    @Override
    public JsonElement serialize(Layer src, Type typeOfSrc, JsonSerializationContext context) {
        
        // Determines the name of the concrete class we are serializing.
        String className = src.getClass().getSimpleName(); 
        
        // Gson serialize the Layer object normally to get all its fields
        JsonObject jsonObject = context.serialize(src).getAsJsonObject();
        
        // Add the extra "layerType" field to the resulting JSON object
        jsonObject.addProperty(TYPE_FIELD_NAME, className);
        
        return jsonObject;
    }

    @Override
    public Layer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        
        // Read the "layerType" field to decide which class to instantiate
        JsonElement typeElement = jsonObject.get(TYPE_FIELD_NAME);
        if (typeElement == null) {
            throw new JsonParseException("Il JSON del Layer non specifica il campo '" + TYPE_FIELD_NAME + "'");
        }

        String typeName = typeElement.getAsString();
        
        // Delegate deserialization to the correct concrete class
        switch (typeName) {
            case "LayerReLu":
                return context.deserialize(jsonObject, LayerReLu.class);
                
            case "LayerIdentityFunction": 
                return context.deserialize(jsonObject, LayerIdentityFunction.class);
                
            case "LayerSoftMax": 
                return context.deserialize(jsonObject, LayerSoftMax.class);
                
            default:
                throw new JsonParseException("Tipo di layer sconosciuto: " + typeName);
        }
    }
}
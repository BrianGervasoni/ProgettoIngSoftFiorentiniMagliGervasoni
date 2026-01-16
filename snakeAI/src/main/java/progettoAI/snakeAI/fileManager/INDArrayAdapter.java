package progettoAI.snakeAI.fileManager;
import com.google.gson.*;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.lang.reflect.Type;

public class INDArrayAdapter implements JsonSerializer<INDArray>, JsonDeserializer<INDArray> {

	@Override
    public JsonElement serialize(INDArray src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // 1. Estraiamo i dati grezzi in un array primitivo Java
        // Usiamo toDoubleVector() se è 1D o un flatten per ND
        double[] data = src.ravel().toDoubleVector(); 
        
        // Convertiamo manualmente in JsonArray per evitare riflessione su oggetti ND4J
        JsonArray dataArray = new JsonArray();
        for (double d : data) {
            dataArray.add(new JsonPrimitive(d));
        }
        jsonObject.add("data", dataArray);

        // 2. Salviamo la shape
        long[] shape = src.shape();
        JsonArray shapeArray = new JsonArray();
        for (long s : shape) {
            shapeArray.add(new JsonPrimitive(s));
        }
        jsonObject.add("shape", shapeArray);

        return jsonObject;
    }

    @Override
    public INDArray deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject jsonObject = json.getAsJsonObject();

            JsonArray dataArray = jsonObject.getAsJsonArray("data");
            double[] data = new double[dataArray.size()];
            for (int i = 0; i < dataArray.size(); i++) {
                data[i] = dataArray.get(i).getAsDouble();
            }

            JsonArray shapeArray = jsonObject.getAsJsonArray("shape");
            long[] shape = new long[shapeArray.size()];
            for (int i = 0; i < shapeArray.size(); i++) {
                shape[i] = shapeArray.get(i).getAsLong();
            }

            return Nd4j.create(data, shape, 'c');
        } catch (Exception e) {
            throw new JsonParseException("Errore deserializzazione INDArray: " + e.getMessage());
        }
    }
}
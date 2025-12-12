package fileManager;
import com.google.gson.*;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.lang.reflect.Type;

public class INDArrayAdapter implements JsonSerializer<INDArray>, JsonDeserializer<INDArray> {

    @Override
    public JsonElement serialize(INDArray src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // 1. Save the raw data as an array of doubles
        double[] data = src.data().asDouble();
        jsonObject.add("data", context.serialize(data));

        // 2. Save the shape of the matrix
        long[] shape = src.shape();
        jsonObject.add("shape", context.serialize(shape));

        return jsonObject;
    }

    @Override
    public INDArray deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // 1. Retrieve raw data (array of doubles)
        double[] data = context.deserialize(jsonObject.get("data"), double[].class);

        // 2. Get the shape
        long[] shape = context.deserialize(jsonObject.get("shape"), long[].class);

        // 3. Rebuild the INDArray
        return Nd4j.create(data, shape);
    }
}
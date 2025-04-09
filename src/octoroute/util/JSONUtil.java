package octoroute.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public final class JSONUtil {

    public static String toString(Object object) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
//        Gson gson = new Gson();
        return gson.toJson(object);
    }
//    public static String toString(Object object) {
//        Gson gson = new Gson();
//        return gson.toJson(object);
//    }

    public static String toStringPretty(Object object) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
//        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T toObject(String json, Class<T> c) {
        Gson gson = new Gson();
        return gson.fromJson(json, c);
    }

    public static String prettify(String s) {
        JsonElement jsonElement = JsonParser.parseString(s);
        // Create a Gson instance with pretty printing enabled
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convert JSON element to a pretty-printed JSON string
        return gson.toJson(jsonElement);
    }
}

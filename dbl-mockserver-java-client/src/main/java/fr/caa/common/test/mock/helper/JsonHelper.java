package fr.caa.common.test.mock.helper;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import org.apache.log4j.Logger;
import org.mockserver.model.HttpRequest;

public abstract class JsonHelper {
    
    private static Logger LOGGER = Logger.getLogger(JsonHelper.class);
    
    public static <T> T fromString(String jsonString, Type runtimeType) {
        return JsonbBuilder.create(new JsonbConfig()
                .withAdapters(new NotTableStringAdapter())).fromJson(jsonString, runtimeType);
    }
    // JsonHelper.fromString(checks, new ArrayList<UICheck>(){}.getClass().getGenericSuperclass());
    public static void write(String filepath, String jsonStream) {
        Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
        try (FileWriter writer = new FileWriter(filepath)) {
            jsonb.toJson(jsonStream, writer);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalStateException(ex);
        }
    }

    public static <K, V> Map<K, V> toMap(String jsonString) {
        LOGGER.debug("== Map json input : " + jsonString);
        return JsonbBuilder.create(new JsonbConfig()
                .withAdapters(new NotTableStringAdapter())).fromJson(jsonString, new HashMap<K, V>() {}.getClass().getGenericSuperclass());
    }
}

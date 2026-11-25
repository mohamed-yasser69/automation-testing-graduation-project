package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/** Loads testdata.json once and maps a named array in it to a list of records (Jackson). */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final JsonNode ROOT;

    static {
        try (InputStream in = JsonUtils.class.getClassLoader().getResourceAsStream("testdata.json")) {
            if (in == null) {
                throw new IllegalStateException("testdata.json not found on the classpath");
            }
            ROOT = MAPPER.readTree(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read testdata.json", e);
        }
    }

    private JsonUtils() {
    }

    public static <T> List<T> readList(String key, Class<T> type) {
        JsonNode array = ROOT.get(key);
        if (array == null || !array.isArray()) {
            throw new IllegalArgumentException("No JSON array named '" + key + "' in testdata.json");
        }
        List<T> result = new ArrayList<>();
        try {
            for (JsonNode node : array) {
                result.add(MAPPER.treeToValue(node, type));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not map '" + key + "' to " + type.getSimpleName(), e);
        }
        return result;
    }
}

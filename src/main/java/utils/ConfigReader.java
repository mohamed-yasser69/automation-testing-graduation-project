package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads config.properties (src/test/resources) with java.util.Properties.
 * A -Dkey=value on the command line always wins over the file, e.g. mvn test -Dheadless=true.
 */
public final class ConfigReader {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on the classpath");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String override = System.getProperty(key);
        return override != null ? override.trim() : PROPS.getProperty(key, "").trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static String baseUrl() {
        return get("base.url");
    }
}

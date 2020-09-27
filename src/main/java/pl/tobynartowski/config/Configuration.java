package pl.tobynartowski.config;

import java.util.List;

public interface Configuration {

    enum Key {
        DATABASE_URL,
        DATABASE_USERNAME,
        DATABASE_PASSWORD,
        FILE_INPUT_TYPES;

        static String getProperty(final Key key) {
            return key != null ? key.name().toLowerCase().replace("_", ".") : "";
        }
    }

    void load();
    String getDatabaseUrl();
    String getDatabaseUsername();
    String getDatabasePassword();
    List<String> getSupportedInputFiles();
}

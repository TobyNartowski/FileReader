package pl.tobynartowski.config;

public interface Configuration {

    enum Key {
        DATABASE_URL,
        DATABASE_USERNAME,
        DATABASE_PASSWORD;

        static String getProperty(final Key key) {
            return key != null ? key.name().toLowerCase().replace("_", ".") : "";
        }
    }

    void load();
    String getDatabaseUrl();
    String getDatabaseUsername();
    String getDatabasePassword();
}

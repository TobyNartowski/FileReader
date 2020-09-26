package pl.tobynartowski.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

public class ProdConfiguration implements Configuration {

    private static final String CONFIGURATION_FILE = "prod-config.properties";
    private final Properties properties = new Properties();

    @Override
    public void load() {
        try {
            final URL url = Optional.ofNullable(getClass().getClassLoader().getResource(CONFIGURATION_FILE))
                    .orElseThrow(() -> new IllegalStateException("No configuration file found"));
            properties.load(new FileInputStream(url.getFile()));
        } catch (IOException e) {
            System.err.println("Failed to open configuration file");
            e.printStackTrace();
        }
    }

    @Override
    public String getDatabaseUrl() {
        return properties.getProperty(Configuration.Key.getProperty(Key.DATABASE_URL));
    }

    @Override
    public String getDatabaseUsername() {
        return properties.getProperty(Configuration.Key.getProperty(Key.DATABASE_USERNAME));
    }

    @Override
    public String getDatabasePassword() {
        return properties.getProperty(Configuration.Key.getProperty(Key.DATABASE_PASSWORD));
    }
}

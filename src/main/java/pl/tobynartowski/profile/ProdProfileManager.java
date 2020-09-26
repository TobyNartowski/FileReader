package pl.tobynartowski.profile;

import pl.tobynartowski.config.Configuration;
import pl.tobynartowski.config.ProdConfiguration;
import pl.tobynartowski.database.DatabaseConnector;
import pl.tobynartowski.database.MySQLDatabaseConnector;

public class ProdProfileManager extends ProfileManager {

    final Configuration configuration = new ProdConfiguration();

    ProdProfileManager() {
        configuration.load();
    }

    @Override
    public DatabaseConnector getDatabaseConnector() {
        final DatabaseConnector connector = new MySQLDatabaseConnector();
        connector.initialize(configuration.getDatabaseUrl(), configuration.getDatabaseUsername(), configuration.getDatabasePassword());

        return connector;
    }
}

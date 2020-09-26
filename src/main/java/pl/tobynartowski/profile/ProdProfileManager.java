package pl.tobynartowski.profile;

import pl.tobynartowski.config.Configuration;
import pl.tobynartowski.config.ProdConfiguration;
import pl.tobynartowski.database.DatabaseConnector;
import pl.tobynartowski.database.MySQLDatabaseConnector;
import pl.tobynartowski.database.executor.Executor;
import pl.tobynartowski.database.executor.MySQLExecutor;

public class ProdProfileManager extends ProfileManager {

    final Configuration configuration = new ProdConfiguration();

    ProdProfileManager() {
        configuration.load();
    }

    @Override
    public DatabaseConnector getDatabaseConnector() {
        final DatabaseConnector connector = new MySQLDatabaseConnector();
        if (connector.getConnection() == null) {
            connector.initialize(configuration.getDatabaseUrl(), configuration.getDatabaseUsername(), configuration.getDatabasePassword());
        }

        return connector;
    }

    @Override
    public Executor getExecutor() {
        return new MySQLExecutor(getDatabaseConnector().getConnection());
    }
}

package pl.tobynartowski.database;

import java.sql.Connection;

public interface DatabaseConnector {

    void initialize(String url, String username, String password);
    Connection getConnection();
    void closeConnection();
}

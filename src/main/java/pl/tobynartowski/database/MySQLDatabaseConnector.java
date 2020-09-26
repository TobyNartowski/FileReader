package pl.tobynartowski.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseConnector implements DatabaseConnector {

    private Connection connection;

    @Override
    public void initialize(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Connection with database failed");
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            System.err.println("Failed to close database connection");
            throwables.printStackTrace();
        }
    }
}

package pl.tobynartowski.database.executor;


import pl.tobynartowski.database.query.create.CreateQuery;
import pl.tobynartowski.utils.TopologicalSorter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLExecutor implements Executor {

    private final Connection connection;

    public MySQLExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTables() {
        final TopologicalSorter<CreateQuery> sorter = new TopologicalSorter<>();

        sorter.sortData(CreateQuery.definedTables, CreateQuery::getDependencyTables).forEach(this::createTable);
    }

    private void createTable(final CreateQuery createQuery) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createQuery.getQuery());
        } catch (SQLException throwables) {
            System.err.println("Cannot create table with query: " + createQuery.getQuery());
            throwables.printStackTrace();
        }
    }
}
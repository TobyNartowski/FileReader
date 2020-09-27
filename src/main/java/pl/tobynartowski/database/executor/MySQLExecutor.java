package pl.tobynartowski.database.executor;


import pl.tobynartowski.database.query.create.CreateQuery;
import pl.tobynartowski.utils.TopologicalSorter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MySQLExecutor implements Executor {

    private final Connection connection;

    public MySQLExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTablesIfNeeded() {
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

    @Override
    public void insertData(List<PreparedStatement> statements) {
        try {
            for (PreparedStatement s : statements) {
                s.executeUpdate();
            }
        } catch (SQLException throwables) {
            System.err.println("Cannot insert data");
            throwables.printStackTrace();
        }
    }
}
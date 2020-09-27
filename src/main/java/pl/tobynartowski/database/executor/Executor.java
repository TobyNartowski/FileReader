package pl.tobynartowski.database.executor;

import java.sql.PreparedStatement;
import java.util.List;

public interface Executor {

    void createTablesIfNeeded();

    void insertData(List<PreparedStatement> statements);
}

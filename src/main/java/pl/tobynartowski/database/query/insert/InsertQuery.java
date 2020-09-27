package pl.tobynartowski.database.query.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public interface InsertQuery<T> {

    List<PreparedStatement> getQuery(T entity, Connection connection);
}

package pl.tobynartowski.database.query.insert;

import pl.tobynartowski.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerInsertQuery implements InsertQuery<Customer> {

    private final static String QUERY = "INSERT INTO CUSTOMERS (ID, NAME, SURNAME, AGE) VALUES (?, ?, ?, ?)";

    @Override
    public List<PreparedStatement> getQuery(final Customer entity, final Connection connection) {
        final List<PreparedStatement> statements = new ArrayList<>();

        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1, entity.getId().toString());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getSurname());
            if (entity.getAge() != null) {
                preparedStatement.setInt(4, entity.getAge());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }

            statements.add(preparedStatement);

            if (entity.getContactList() != null && !entity.getContactList().isEmpty()) {
                statements.addAll(entity.getContactList().stream()
                        .flatMap(contact -> new ContactInsertQuery().getQuery(contact, connection).stream())
                        .collect(Collectors.toList()));
            }
        } catch (SQLException throwables) {
            System.err.println("Failed to create Customer insert");
            throwables.printStackTrace();
        }

        return statements;
    }
}

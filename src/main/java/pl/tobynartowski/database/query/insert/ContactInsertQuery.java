package pl.tobynartowski.database.query.insert;

import pl.tobynartowski.model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ContactInsertQuery implements InsertQuery<Contact> {

    private final static String QUERY = "INSERT INTO CONTACTS (ID, ID_CUSTOMER, TYPE, CONTACT) VALUES (?, ?, ?, ?)";

    @Override
    public List<PreparedStatement> getQuery(final Contact entity, final Connection connection) {
        final List<PreparedStatement> statements = new ArrayList<>();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1, entity.getId().toString());
            preparedStatement.setString(2, entity.getCustomerId().toString());
            preparedStatement.setInt(3, entity.getType().ordinal());
            preparedStatement.setString(4, entity.getContact());

            statements.add(preparedStatement);
        } catch (SQLException throwables) {
            System.err.println("Failed to create Contact insert");
            throwables.printStackTrace();
        }

        return statements;
    }
}

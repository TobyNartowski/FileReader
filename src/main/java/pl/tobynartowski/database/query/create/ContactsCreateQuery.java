package pl.tobynartowski.database.query.create;

import java.util.Collections;
import java.util.List;

public class ContactsCreateQuery implements CreateQuery {

    private static final String QUERY =
            "CREATE TABLE IF NOT EXISTS CONTACTS (\n" +
            "    ID VARCHAR(36) PRIMARY KEY,\n" +
            "    ID_CUSTOMER VARCHAR(36) NOT NULL,\n" +
            "    TYPE TINYINT NOT NULL,\n" +
            "    CONTACT VARCHAR(256) NOT NULL,\n" +
            "    FOREIGN KEY (ID_CUSTOMER) REFERENCES CUSTOMERS(ID)\n" +
            ")";

    @Override
    public String getQuery() {
        return QUERY;
    }

    @Override
    public List<Class<? extends CreateQuery>> getDependencyTables() {
        return Collections.singletonList(CustomersCreateQuery.class);
    }
}

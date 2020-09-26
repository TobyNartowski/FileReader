package pl.tobynartowski.database.query.create;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface CreateQuery {

    List<CreateQuery> definedTables = Arrays.asList(
            new ContactsCreateQuery(),
            new CustomersCreateQuery()
    );

    String getQuery();

    default List<Class<? extends CreateQuery>> getDependencyTables() {
        return Collections.emptyList();
    }
}

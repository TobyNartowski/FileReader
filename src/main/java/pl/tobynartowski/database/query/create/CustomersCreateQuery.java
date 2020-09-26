package pl.tobynartowski.database.query.create;

public class CustomersCreateQuery implements CreateQuery {

    private static final String QUERY =
            "CREATE TABLE IF NOT EXISTS CUSTOMERS (\n" +
            "    ID INT PRIMARY KEY,\n" +
            "    NAME VARCHAR(256) NOT NULL,\n" +
            "    SURNAME VARCHAR(256) NOT NULL,\n" +
            "    AGE TINYINT\n" +
            ")";

    @Override
    public String getQuery() {
        return QUERY;
    }
}

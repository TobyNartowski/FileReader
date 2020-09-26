package pl.tobynartowski.config;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProdConfigurationTest {

    private final Configuration configuration = new ProdConfiguration();

    @Before
    public void initialize() {
        configuration.load();
    }

    @Test
    public void should_get_proper_database_credentials() {
        // given
        final String expectedUsername = "toby";
        final String expectedPassword = "database123";

        // when
        final String actualUsername = configuration.getDatabaseUsername();
        final String actualPassword = configuration.getDatabasePassword();

        // then
        assertEquals(expectedUsername, actualUsername);
        assertEquals(expectedPassword, actualPassword);
    }
}

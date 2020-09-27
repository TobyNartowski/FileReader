package pl.tobynartowski.config;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ProdConfigurationTest {

    private final Configuration configuration = new ProdConfiguration();

    @Before
    public void initialize() {
        configuration.load();
    }

    @Test
    public void should_get_properties_from_file() {
        // given

        // when
        final String actualUsername = configuration.getDatabaseUsername();
        final String actualPassword = configuration.getDatabasePassword();

        // then
        assertNotNull(actualUsername);
        assertNotNull(actualPassword);
    }
}

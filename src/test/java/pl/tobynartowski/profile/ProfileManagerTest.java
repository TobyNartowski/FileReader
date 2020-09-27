package pl.tobynartowski.profile;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProfileManagerTest {

    @Test
    public void should_load_prod_profile_when_correct_parameter_passed() {
        // given
        final String[] args = {"path/to/input/project", "prod"};

        // when
        ProfileManager profileManager = ProfileManager.loadProfile(args);

        // then
        assertTrue(profileManager instanceof ProdProfileManager);
        assertNotNull(ProfileManager.getInputPath());
    }

    @Test
    public void should_load_prod_profile_when_no_parameter_passed() {
        // given
        final String[] args = {"path/to/input/project"};

        // when
        ProfileManager profileManager = ProfileManager.loadProfile(args);

        // then
        assertTrue(profileManager instanceof ProdProfileManager);
    }
}

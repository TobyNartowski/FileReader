package pl.tobynartowski.profile;

import pl.tobynartowski.database.DatabaseConnector;
import pl.tobynartowski.exception.UnknownProfileException;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ProfileManager {

    public enum Profile {
        PROD(ProdProfileManager::new);

        private final Supplier<ProfileManager> instantiator;

        Profile(Supplier<ProfileManager> instantiator) {
            this.instantiator = instantiator;
        }

        public Supplier<ProfileManager> getInstantiator() {
            return instantiator;
        }

        static String getAvailableProfiles() {
            return Stream.of(Profile.values())
                    .map(profile -> profile.name().toLowerCase())
                    .collect(Collectors.joining(","));
        }
    }

    public abstract DatabaseConnector getDatabaseConnector();

    public static ProfileManager loadProfile(final String[] args) {
        if (args.length < 1) {
            return Profile.PROD.getInstantiator().get();
        }

        final Profile foundProfile = Stream.of(Profile.values())
                .filter(profile -> profile.name().equals(args[0].toUpperCase()))
                .findAny()
                .orElseThrow(() -> new UnknownProfileException("Unknown profile passed: " + args[0] +
                        ", available profiles: " + Profile.getAvailableProfiles()));

        return foundProfile.getInstantiator().get();
    }
}

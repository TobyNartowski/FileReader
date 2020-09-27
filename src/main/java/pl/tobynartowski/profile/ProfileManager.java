package pl.tobynartowski.profile;

import pl.tobynartowski.database.DatabaseConnector;
import pl.tobynartowski.database.executor.Executor;
import pl.tobynartowski.exception.UnknownProfileException;
import pl.tobynartowski.utils.InputFileUtils;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ProfileManager {

    private static String inputPath;

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

    public static ProfileManager loadProfile(final String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Input directory not provided");
        }

        inputPath = args[0].replace("\\", "/");

        if (args.length < 2) {
            return Profile.PROD.getInstantiator().get();
        }

        final Profile foundProfile = Stream.of(Profile.values())
                .filter(profile -> profile.name().equals(args[1].toUpperCase()))
                .findAny()
                .orElseThrow(() -> new UnknownProfileException("Unknown profile passed: " + args[1] +
                        ", available profiles: " + Profile.getAvailableProfiles()));

        return foundProfile.getInstantiator().get();
    }

    public static String getInputPath() {
        return inputPath;
    }

    public abstract DatabaseConnector getDatabaseConnector();

    public abstract Executor getExecutor();

    public abstract InputFileUtils getInputFileUtils();
}

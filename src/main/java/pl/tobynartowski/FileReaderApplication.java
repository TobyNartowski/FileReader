package pl.tobynartowski;

import pl.tobynartowski.database.executor.Executor;
import pl.tobynartowski.profile.ProfileManager;

public class FileReaderApplication {

    public static void main(String[] args) {
        final ProfileManager profileManager = ProfileManager.loadProfile(args);
        final Executor executor = profileManager.getExecutor();

        executor.createTables();
    }
}

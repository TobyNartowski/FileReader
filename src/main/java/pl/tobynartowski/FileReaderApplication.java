package pl.tobynartowski;

import pl.tobynartowski.database.DatabaseConnector;
import pl.tobynartowski.profile.ProfileManager;

public class FileReaderApplication {

    public static void main(String[] args) {
        final ProfileManager profileManager = ProfileManager.loadProfile(args);
        final DatabaseConnector databaseConnector = profileManager.getDatabaseConnector();
    }
}

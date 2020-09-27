package pl.tobynartowski;

import pl.tobynartowski.database.executor.Executor;
import pl.tobynartowski.model.Customer;
import pl.tobynartowski.profile.ProfileManager;
import pl.tobynartowski.reader.CustomerXmlFileReader;
import pl.tobynartowski.reader.FileReader;
import pl.tobynartowski.utils.InputFileUtils;

import java.util.List;

public class FileReaderApplication {

    public static void main(String[] args) {
        final ProfileManager profileManager = ProfileManager.loadProfile(args);

        final Executor executor = profileManager.getExecutor();
        executor.createTablesIfNeeded();

        final InputFileUtils inputFileUtils = profileManager.getInputFileUtils();
        final List<String> resources = inputFileUtils.getAllSupportedResources(ProfileManager.getInputPath());

        final FileReader<Customer> reader = new CustomerXmlFileReader();
        reader.openStream(resources.get(1));
        while (reader.hasNextElement()) {
            Customer customer = reader.getNextElement();
        }

        reader.closeStream();
    }
}

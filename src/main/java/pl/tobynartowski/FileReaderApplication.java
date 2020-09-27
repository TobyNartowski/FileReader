package pl.tobynartowski;

import pl.tobynartowski.database.executor.Executor;
import pl.tobynartowski.database.query.insert.CustomerInsertQuery;
import pl.tobynartowski.model.Customer;
import pl.tobynartowski.profile.ProfileManager;
import pl.tobynartowski.reader.FileReader;
import pl.tobynartowski.utils.CustomerExtensionResolver;
import pl.tobynartowski.utils.InputFileUtils;

import java.util.List;
import java.util.stream.Collectors;

public class FileReaderApplication {

    public static void main(String[] args) {
        final ProfileManager profileManager = ProfileManager.loadProfile(args);

        final Executor executor = profileManager.getExecutor();
        executor.createTablesIfNeeded();

        final InputFileUtils inputFileUtils = profileManager.getInputFileUtils();
        final List<String> resources = inputFileUtils.getAllSupportedResources(ProfileManager.getInputPath());
        final List<FileReader<Customer>> fileReaders = resources.stream()
                .map(file -> CustomerExtensionResolver.getFileReader(file).apply(file))
                .collect(Collectors.toList());
        fileReaders.forEach(FileReader::openStream);

        fileReaders.forEach(reader -> {
            while (reader.hasNextElement()) {
                final Customer customer = reader.getNextElement();
                executor.insertData(new CustomerInsertQuery().getQuery(customer,
                        profileManager.getDatabaseConnector().getConnection()));
            }
        });

        fileReaders.forEach(FileReader::closeStream);
        System.out.println("Database updated correctly!");
    }
}

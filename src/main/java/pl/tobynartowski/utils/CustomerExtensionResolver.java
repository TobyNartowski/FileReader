package pl.tobynartowski.utils;

import pl.tobynartowski.model.Customer;
import pl.tobynartowski.reader.CustomerTxtFileReader;
import pl.tobynartowski.reader.CustomerXmlFileReader;
import pl.tobynartowski.reader.FileReader;

import java.util.Map;
import java.util.function.Function;

public class CustomerExtensionResolver {

    private final static Map<String, Function<String, ? extends FileReader<Customer>>> configuration = Map.of(
            "txt", CustomerTxtFileReader::new,
            "xml", CustomerXmlFileReader::new
    );

    public static Function<String, ? extends FileReader<Customer>> getFileReader(final String file) {
        return configuration.entrySet().stream()
                .filter(key -> file.endsWith(key.getKey()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("No declared file extension found"))
                .getValue();
    }
}

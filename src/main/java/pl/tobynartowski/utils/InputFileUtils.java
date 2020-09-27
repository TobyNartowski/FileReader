package pl.tobynartowski.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InputFileUtils {

    private final List<String> supportedTypes;

    public InputFileUtils(List<String> supportedTypes) {
        this.supportedTypes = supportedTypes;
    }

    public List<String> getAllSupportedResources(final String absolutePath) {
        final List<String> files;

        try {
            if (new File(absolutePath).isDirectory()) {
                files = Files.walk(Paths.get(absolutePath))
                        .filter(Files::isRegularFile)
                        .filter(this::isSupported)
                        .map(path -> absolutePath + "/" + path.getFileName())
                        .collect(Collectors.toList());
            } else {
                files = Collections.singletonList(absolutePath);
            }
        } catch (IOException e) {
            System.err.println("Failed to open input directory");
            throw new IllegalStateException(e);
        }

        return files;
    }

    private boolean isSupported(final Path file) {
        return supportedTypes.stream()
                .anyMatch(type -> file.getFileName().toString().endsWith(type));
    }
}

package pl.tobynartowski.reader;

public interface FileReader<T> {

    void openStream(String path);

    void closeStream();

    boolean hasNextElement();

    T getNextElement();
}

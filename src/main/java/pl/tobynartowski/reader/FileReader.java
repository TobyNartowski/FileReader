package pl.tobynartowski.reader;

public interface FileReader<T> {

    void openStream();

    void closeStream();

    boolean hasNextElement();

    T getNextElement();
}

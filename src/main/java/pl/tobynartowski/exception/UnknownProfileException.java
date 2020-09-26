package pl.tobynartowski.exception;

public class UnknownProfileException extends IllegalArgumentException {

    public UnknownProfileException(String s) {
        super(s);
    }
}

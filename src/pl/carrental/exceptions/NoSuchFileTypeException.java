package pl.carrental.exceptions;

public class NoSuchFileTypeException extends RuntimeException {
    public NoSuchFileTypeException(String message) {
        super(message);
    }
}

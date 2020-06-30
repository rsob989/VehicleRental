package pl.carrental.exceptions;

public class VehicleAlreadyExistsException extends RuntimeException {
    public VehicleAlreadyExistsException(String message) {
        super(message);
    }
}

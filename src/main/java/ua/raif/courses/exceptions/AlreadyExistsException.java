package ua.raif.courses.exceptions;

public class AlreadyExistsException extends ConflictException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}

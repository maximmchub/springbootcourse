package ua.raif.courses.exceptions;

public class NotExistsException extends InvalidRequestException {
    public NotExistsException(String message) {
        super(message);
    }
}

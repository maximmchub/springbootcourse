package ua.raif.courses.exceptions;

public class DateValidationException extends InvalidRequestException {
    public DateValidationException(String message) {
        super(message);
    }
}

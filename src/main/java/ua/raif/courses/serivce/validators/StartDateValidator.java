package ua.raif.courses.serivce.validators;

import ua.raif.courses.exceptions.DateValidationException;

import java.time.LocalDate;

public class StartDateValidator {
    public static void validate(LocalDate startDate) {
        LocalDate deadLineDate = startDate.minusMonths(1);
        if (LocalDate.now().isAfter(deadLineDate)) {
            throw new DateValidationException("Date is after deadline.");
        }

    }
}

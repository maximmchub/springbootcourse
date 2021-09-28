package ua.raif.courses.serivce.validators;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.raif.courses.dao.entity.ConferenceEntity;
import ua.raif.courses.domain.ConferenceDates;
import ua.raif.courses.exceptions.DateValidationException;
import ua.raif.courses.serivce.ConferenceService;

import java.time.LocalDate;

@AllArgsConstructor
@Service
@Slf4j
public class ConferenceStartDateValidator implements Validator {

    private final ConferenceService conferenceService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ConferenceDates.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ConferenceDates dates = (ConferenceDates) target;

        LocalDate deadLineDate = dates.getStartDate().minusMonths(1);
        if (LocalDate.now().isAfter(deadLineDate)) {
            LOG.info("Date is after deadline.");
            //errors.rejectValue("ConferenceDates", "Date is after deadline.");
            throw new DateValidationException("Date is after deadline.");
        }

    }
}





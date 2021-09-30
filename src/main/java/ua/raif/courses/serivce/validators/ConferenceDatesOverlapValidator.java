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

@Slf4j
@AllArgsConstructor
@Service
public class ConferenceDatesOverlapValidator implements Validator {

    private final ConferenceService conferenceService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ConferenceDates.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var newConferenceDates = (ConferenceDates) target;
        var conferences = conferenceService.getAll();

        RangeSet<LocalDate> dateRangeSet = TreeRangeSet.create();
        for (ConferenceEntity conference : conferences) {
            dateRangeSet.add(Range.closed(conference.getDateStart(), conference.getDateEnd()));
        }
        if (dateRangeSet.intersects(Range.closed(newConferenceDates.getStartDate(), newConferenceDates.getEndDate()))) {
            LOG.info("Date of conferences is overlaps.");
            //errors.rejectValue("ConferenceDates", "Date of conferences is overlaps.");
            throw new DateValidationException("Date of conferences is overlaps.");
        }

    }
}





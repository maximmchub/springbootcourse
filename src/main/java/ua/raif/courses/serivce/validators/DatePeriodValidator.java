package ua.raif.courses.serivce.validators;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import ua.raif.courses.dao.entity.ConferenceEntity;
import ua.raif.courses.domain.ConferenceDates;
import ua.raif.courses.exceptions.DateValidationException;

import java.time.LocalDate;
import java.util.List;

public class DatePeriodValidator {
   public static void validate(List<ConferenceEntity> conferences, ConferenceEntity newConference) {
        RangeSet<LocalDate> dateRangeSet = TreeRangeSet.create();
        for (ConferenceEntity conference : conferences) {
            dateRangeSet.add(Range.closed(conference.getDateStart(), conference.getDateEnd()));
        }
        if (dateRangeSet.intersects(Range.closed(newConference.getDateStart(), newConference.getDateEnd()))) {
            throw new DateValidationException("Date of conferences is overlaps.");
        }
    }
}

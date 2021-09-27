package ua.raif.courses.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ConferenceDates {
    private LocalDate startDate;
    private LocalDate endDate;
}

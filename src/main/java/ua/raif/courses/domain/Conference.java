package ua.raif.courses.domain;

import lombok.Builder;
import lombok.Data;
import ua.raif.courses.api.dto.ConferenceCreateDto;
import ua.raif.courses.api.dto.ConferenceViewDto;
import ua.raif.courses.dao.entity.ConferenceEntity;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class Conference {
    Long id;
    @NotBlank
    String caption;
    @NotBlank
    String description;
    ConferenceDates dates;
    int capacity;

    public static Conference fromDto(ConferenceCreateDto conferenceDto) {
        return Conference.builder()
                .capacity(conferenceDto.getCapacity())
                .caption(conferenceDto.getCaption())
                .dates(new ConferenceDates(
                        LocalDate.parse(conferenceDto.getDateStart(), DateTimeFormatter.ISO_DATE),
                        LocalDate.parse(conferenceDto.getDateEnd(), DateTimeFormatter.ISO_DATE)))
                .description(conferenceDto.getDescription())
                .build();
    }

    public static Conference fromDto(ConferenceViewDto conferenceDto) {
        return Conference.builder()
                .capacity(conferenceDto.getCapacity())
                .caption(conferenceDto.getCaption())
                .dates(new ConferenceDates(
                        LocalDate.parse(conferenceDto.getDateStart(), DateTimeFormatter.ISO_DATE),
                        LocalDate.parse(conferenceDto.getDateEnd(), DateTimeFormatter.ISO_DATE)))
                .description(conferenceDto.getDescription())
                .id(conferenceDto.getId())
                .build();
    }

    public static Conference fromEntity(ConferenceEntity conferenceEntity) {
        return Conference.builder()
                .capacity(conferenceEntity.getCapacity())
                .caption(conferenceEntity.getCaption())
                .dates(new ConferenceDates(
                        conferenceEntity.getDateStart(),
                        conferenceEntity.getDateEnd()))
                .description(conferenceEntity.getDescription())
                .id(conferenceEntity.getId())
                .build();
    }


    public ConferenceEntity asEntity() {
        return ConferenceEntity.builder()
                .capacity(capacity)
                .caption(caption)
                .dateEnd(dates.getEndDate())
                .dateStart(dates.getStartDate())
                .description(description)
                .id(id)
                .build();
    }

    public ConferenceViewDto asDto() {
        return ConferenceViewDto.builder()
                .id(id)
                .capacity(capacity)
                .caption(caption)
                .description(description)
                .dateEnd(dates.getEndDate().format(DateTimeFormatter.ISO_DATE))
                .dateStart(dates.getStartDate().format(DateTimeFormatter.ISO_DATE))
                .build();

    }
}
package ua.raif.courses.api.dto;

import lombok.*;

@ToString
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ConferenceViewDto {
    Long id;
    String caption;
    String description;
    String dateStart;
    String dateEnd;
    int capacity;
}

package ua.raif.courses.api.dto;

import lombok.*;

import javax.swing.text.TabableView;
import java.util.Set;
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

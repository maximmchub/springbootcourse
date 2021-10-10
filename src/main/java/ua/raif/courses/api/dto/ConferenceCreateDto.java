package ua.raif.courses.api.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ConferenceCreateDto {
    @NotBlank
    String caption;
    @NotBlank
    String description;
    @NotBlank
    String dateStart;
    @NotBlank
    String dateEnd;
    @NotNull
    @Min(101)
    int capacity;
}

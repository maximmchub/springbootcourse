package ua.raif.courses.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TalkCreateDto {
    @NotBlank
    String caption;
    @NotBlank
    String description;
    @NotBlank
    String speaker;
    @NotBlank
    String talkType;
}

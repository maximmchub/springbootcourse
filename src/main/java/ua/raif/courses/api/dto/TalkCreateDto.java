package ua.raif.courses.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@AllArgsConstructor
@Getter
@Setter
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

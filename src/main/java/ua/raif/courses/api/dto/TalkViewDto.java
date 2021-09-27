package ua.raif.courses.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class TalkViewDto {
    String id;
    String caption;
    String description;
    String speaker;
    String talkType;
}

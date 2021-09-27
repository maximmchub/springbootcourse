package ua.raif.courses.domain;

import lombok.Builder;
import lombok.Data;
import ua.raif.courses.api.dto.ConferenceViewDto;
import ua.raif.courses.api.dto.TalkCreateDto;
import ua.raif.courses.api.dto.TalkViewDto;
import ua.raif.courses.dao.entity.ConferenceEntity;
import ua.raif.courses.dao.entity.TalkEntity;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class Talk {

    Long id;
    @NotBlank
    String caption;
    @NotBlank
    String description;
    @NotBlank
    String speaker;
    TalkType type;
    Conference conference;

    public static Talk fromDto(TalkCreateDto talk, ConferenceEntity conference) {
        return Talk.builder()
                .caption(talk.getCaption())
                .description(talk.getDescription())
                .speaker(talk.getSpeaker())
                .conference(Conference.fromEntity(conference))
                .type(TalkType.valueOf(talk.getTalkType()))
                .build();
    }

    public static Talk fromEntity(TalkEntity talk) {
        return Talk.builder()
                .caption(talk.getCaption())
                .description(talk.getDescription())
                .speaker(talk.getSpeaker())
                .conference(Conference.fromEntity(talk.getConference()))
                .type(TalkType.valueOf(talk.getTalkType().toUpperCase()))
                .id(talk.getId())
                .build();
    }

    public static TalkEntity withConferenceFromDto(ConferenceViewDto conference) {
        TalkEntity talkEntity = new TalkEntity();
        talkEntity.setConference(Conference.fromDto(conference).asEntity());
        return talkEntity;
    }

    public TalkEntity asEntity() {
        return TalkEntity.builder()
                .caption(caption)
                .description(description)
                .speaker(speaker)
                .conference(conference.asEntity())
                .talkType(type.getId())
                .build();
    }

    public TalkViewDto asDto() {
        return TalkViewDto.builder()
                .caption(caption)
                .description(description)
                .speaker(speaker)
                .talkType(type.getId())
                .id(String.valueOf(id))
                .build();
    }
}

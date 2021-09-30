package ua.raif.courses.serivce;

import org.springframework.validation.annotation.Validated;
import ua.raif.courses.api.dto.TalkCreateDto;
import ua.raif.courses.api.dto.TalkViewDto;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface TalkService {

    TalkViewDto addTalk(@Valid TalkCreateDto talk, Long conferenceId);

    List<TalkViewDto> findAllTalksInConference(Long conferenceId);
}

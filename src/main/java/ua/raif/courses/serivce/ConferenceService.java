package ua.raif.courses.serivce;

import org.springframework.validation.annotation.Validated;
import ua.raif.courses.api.dto.ConferenceCreateDto;
import ua.raif.courses.api.dto.ConferenceViewDto;
import ua.raif.courses.dao.entity.ConferenceEntity;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ConferenceService {
    Long addConference(@Valid ConferenceCreateDto conference);

    Long updateConference(@Valid ConferenceCreateDto conference, Long conferenceId);

    List<ConferenceViewDto> findAllConferences();

    ConferenceViewDto getConferenceById(Long id);

    List<ConferenceEntity> getAll();

    ConferenceEntity getById(Long id);
}

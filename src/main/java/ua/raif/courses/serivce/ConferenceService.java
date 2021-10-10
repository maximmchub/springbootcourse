package ua.raif.courses.serivce;

import org.springframework.validation.annotation.Validated;
import ua.raif.courses.api.dto.ConferenceCreateDto;
import ua.raif.courses.api.dto.ConferenceViewDto;
import ua.raif.courses.dao.entity.ConferenceEntity;

import javax.validation.Valid;
import java.util.List;

public interface ConferenceService {
    ConferenceViewDto addConference(ConferenceCreateDto conference);

    ConferenceViewDto updateConference(ConferenceCreateDto conference, Long conferenceId);

    List<ConferenceViewDto> findAllConferences();

    ConferenceEntity getById(Long id);

}

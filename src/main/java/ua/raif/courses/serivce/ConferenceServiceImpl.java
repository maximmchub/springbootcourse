package ua.raif.courses.serivce;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ua.raif.courses.api.dto.ConferenceCreateDto;
import ua.raif.courses.api.dto.ConferenceViewDto;
import ua.raif.courses.dao.ConferenceDao;
import ua.raif.courses.dao.entity.ConferenceEntity;
import ua.raif.courses.domain.Conference;
import ua.raif.courses.exceptions.AlreadyExistsException;
import ua.raif.courses.exceptions.NotExistsException;
import ua.raif.courses.serivce.validators.ConferenceDatesOverlapValidator;
import ua.raif.courses.serivce.validators.ConferenceExistValidator;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceDao conferenceDao;

    @Override
    public Long addConference(ConferenceCreateDto conferenceDto) {
        Conference conference = Conference.fromDto(conferenceDto);

        new ConferenceExistValidator(this).validate(conference, null);
        new ConferenceDatesOverlapValidator(this).validate(conference.getDates(), null);

        return conferenceDao.save(conference.asEntity()).getId();
    }

    @Override
    public Long updateConference(ConferenceCreateDto conferenceDto, Long conferenceId) {
        if (!conferenceDao.existsById(conferenceId)) {
            throw new NotExistsException("Falid to update conference #" + conferenceId + ". Confrerence not exists.");
        }
        ConferenceEntity dbConference = Conference.fromDto(conferenceDto).asEntity();
        dbConference.setId(conferenceId);

        return conferenceDao.save(dbConference).getId();
    }

    @Override
    public List<ConferenceViewDto> findAllConferences() {

        List<ConferenceEntity> conferences = getAll();
        List<ConferenceViewDto> conferenceViewDtos = new ArrayList<>();

        for (ConferenceEntity conference : conferences) {
            conferenceViewDtos.add(Conference.fromEntity(conference).asDto());
        }
        return conferenceViewDtos;
    }

    @Override
    public List<ConferenceEntity> getAll() {
        return conferenceDao.findAll();
    }

    @Override
    public ConferenceEntity getById(Long id) {
        return conferenceDao.findById(id).orElseThrow(() -> new NotExistsException("Falid to cet conference #" + id + ". Confrerence not exists."));
    }


    @Override
    public ConferenceViewDto getConferenceById(Long id) {
        ConferenceEntity conference = getById(id);
        return Conference.fromEntity(conference).asDto();
    }


    public boolean isConferenceExist(String caption) {
        ConferenceEntity confereseExample = new ConferenceEntity();
        confereseExample.setCaption(caption);
        return conferenceDao.exists(Example.of(confereseExample));
    }

}

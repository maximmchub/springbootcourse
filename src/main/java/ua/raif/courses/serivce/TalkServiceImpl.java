package ua.raif.courses.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ua.raif.courses.api.dto.ConferenceViewDto;
import ua.raif.courses.api.dto.TalkCreateDto;
import ua.raif.courses.api.dto.TalkViewDto;
import ua.raif.courses.dao.TalkDao;
import ua.raif.courses.dao.entity.ConferenceEntity;
import ua.raif.courses.dao.entity.TalkEntity;
import ua.raif.courses.domain.ConferenceDates;
import ua.raif.courses.domain.Talk;
import ua.raif.courses.exceptions.AlreadyExistsException;
import ua.raif.courses.exceptions.SpeakerException;
import ua.raif.courses.serivce.validators.DatePeriodValidator;
import ua.raif.courses.serivce.validators.StartDateValidator;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class TalkServiceImpl implements TalkService {
    private final TalkDao talkDao;
    private final ConferenceService conferenceService;

    public TalkServiceImpl(TalkDao talkDao, ConferenceService conferenceService) {
        this.talkDao = talkDao;
        this.conferenceService = conferenceService;
    }

    @Override
    public Long addTalk(TalkCreateDto talk, Long conferenceId) {
        if (isTalkExist(talk.getCaption())) {
            throw new AlreadyExistsException("Talk with caption <" + talk.getCaption() + "> already exists.");
        }

        if (talksOfSpeaker(talk.getSpeaker()) > 2) {
            throw new SpeakerException("Speaker <" + talk.getSpeaker() + "> has too many talks.");
        }

        ConferenceEntity conference = conferenceService.getById(conferenceId);
        StartDateValidator.validate(conference.getDateStart());

        TalkEntity dbTalk = Talk.fromDto(talk, conference).asEntity();
        return talkDao.save(dbTalk).getId();
    }

    @Override
    public List<TalkViewDto> findAllTalksInConference(Long conferenceId) {
        ConferenceViewDto conference = conferenceService.getConferenceById(conferenceId);

        Example<TalkEntity> talkExample = Example.of(Talk.withConferenceFromDto(conference));
        List<TalkEntity> talks = talkDao.findAll(talkExample);

        List<TalkViewDto> talkViewDtos = new ArrayList<>();

        for (TalkEntity talk : talks) {
            talkViewDtos.add(Talk.fromEntity(talk).asDto());
        }
        return talkViewDtos;
    }

    public boolean isTalkExist(String caption) {
        TalkEntity talkExample = new TalkEntity();
        talkExample.setCaption(caption);
        return talkDao.exists(Example.of(talkExample));
    }

    public int talksOfSpeaker(String speaker) {
        TalkEntity talkExample = new TalkEntity();
        talkExample.setSpeaker(speaker);
        return talkDao.findAll(Example.of(talkExample)).size();
    }
}

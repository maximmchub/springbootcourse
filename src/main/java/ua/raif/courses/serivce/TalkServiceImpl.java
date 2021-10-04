package ua.raif.courses.serivce;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.raif.courses.api.dto.TalkCreateDto;
import ua.raif.courses.api.dto.TalkViewDto;
import ua.raif.courses.dao.TalkDao;
import ua.raif.courses.dao.entity.TalkEntity;
import ua.raif.courses.domain.Conference;
import ua.raif.courses.domain.Talk;
import ua.raif.courses.exceptions.AlreadyExistsException;
import ua.raif.courses.exceptions.DateValidationException;
import ua.raif.courses.exceptions.SpeakerException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class TalkServiceImpl implements TalkService {
    private final TalkDao talkDao;
    private final ConferenceService conferenceService;

    @Override
    public TalkViewDto addTalk(TalkCreateDto talk, Long conferenceId) {
        if (talkDao.existsAllByCaption(talk.getCaption())) {
            throw new AlreadyExistsException("Talk with caption <" + talk.getCaption() + "> already exists.");
        }

        if (talkDao.countAllBySpeaker(talk.getSpeaker()) > 2) {
            throw new SpeakerException("Speaker <" + talk.getSpeaker() + "> has too many talks.");
        }

        Conference conference = Conference.fromEntity(conferenceService.getById(conferenceId));
        validateConferenceStartdate(conference);

        TalkEntity dbTalk = Talk.fromDto(talk, conference.asEntity()).asEntity();
        return Talk.fromEntity(talkDao.save(dbTalk)).asDto();
    }

    @Override
    public List<TalkViewDto> findAllTalksInConference(Long id) {
        List<TalkEntity> talks = talkDao.findAllByConferenceId(id);

        List<TalkViewDto> talkViewDtos = new ArrayList<>();
        talks.forEach((c) -> talkViewDtos.add(Talk.fromEntity(c).asDto()));

        return talkViewDtos;
    }

    public void validateConferenceStartdate(Conference conference) {
        LocalDate deadLineDate = conference.getDates().getStartDate().minusMonths(1);
        if (LocalDate.now().isAfter(deadLineDate)) {
            LOG.info("Date is after deadline.");
            throw new DateValidationException("Date is after deadline.");
        }

    }
}

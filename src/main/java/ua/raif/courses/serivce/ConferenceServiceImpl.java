package ua.raif.courses.serivce;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.raif.courses.api.dto.ConferenceCreateDto;
import ua.raif.courses.api.dto.ConferenceViewDto;
import ua.raif.courses.dao.ConferenceDao;
import ua.raif.courses.dao.entity.ConferenceEntity;
import ua.raif.courses.domain.Conference;
import ua.raif.courses.exceptions.AlreadyExistsException;
import ua.raif.courses.exceptions.DateValidationException;
import ua.raif.courses.exceptions.NotExistsException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceDao conferenceDao;

    @Override
    public ConferenceViewDto addConference(ConferenceCreateDto conferenceDto) {
        Conference conference = Conference.fromDto(conferenceDto);

        validateConferenceExists(conference);
        validateConferenceDatesOverlaps(conference);

        return Conference.fromEntity(conferenceDao.save(conference.asEntity())).asDto();
    }

    @Override
    public ConferenceViewDto updateConference(ConferenceCreateDto conferenceDto, Long conferenceId) {
        if (!conferenceDao.existsById(conferenceId)) {
            throw new NotExistsException("Falid to update conference #" + conferenceId + ". Confrerence not exists.");
        }
        ConferenceEntity dbConference = Conference.fromDto(conferenceDto).asEntity();
        dbConference.setId(conferenceId);

        return Conference.fromEntity(conferenceDao.save(dbConference)).asDto();
    }

    @Override
    public List<ConferenceViewDto> findAllConferences() {
        List<ConferenceEntity> conferences = getAll();
        List<ConferenceViewDto> conferenceViewDtos = new ArrayList<>();

        conferences.forEach((c) -> conferenceViewDtos.add(Conference.fromEntity(c).asDto()));
        return conferenceViewDtos;
    }

    public List<ConferenceEntity> getAll() {
        return conferenceDao.findAll();
    }

    @Override
    public ConferenceEntity getById(Long id) {
        return conferenceDao.findById(id).orElseThrow(() -> new NotExistsException("Falid to cet conference #" + id + ". Confrerence not exists."));
    }

    void validateConferenceExists(Conference conference) {
        if (conferenceDao.existsByCaption(conference.getCaption())) {
            LOG.info("Conference with caption <" + conference.getCaption() + "> already exists.");
            throw new AlreadyExistsException("Conference with caption <" + conference.getCaption() + "> already exists.");
        }
    }

    void validateConferenceDatesOverlaps(Conference newConference) {
        var conferences = getAll();

        RangeSet<LocalDate> dateRangeSet = TreeRangeSet.create();
        conferences.forEach((c) -> dateRangeSet.add(Range.closed(c.getDateStart(), c.getDateEnd())));

        if (dateRangeSet.intersects(Range.closed(newConference.getDates().getStartDate(), newConference.getDates().getEndDate()))) {
            LOG.info("Date of conferences is overlaps.");
            throw new DateValidationException("Date of conferences is overlaps.");
        }
    }

}

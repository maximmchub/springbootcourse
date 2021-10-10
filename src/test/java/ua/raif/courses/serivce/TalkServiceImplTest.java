package ua.raif.courses.serivce;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.raif.courses.api.dto.TalkCreateDto;
import ua.raif.courses.dao.TalkDao;
import ua.raif.courses.dao.entity.ConferenceEntity;
import ua.raif.courses.dao.entity.TalkEntity;
import ua.raif.courses.domain.Conference;
import ua.raif.courses.domain.ConferenceDates;
import ua.raif.courses.domain.Talk;
import ua.raif.courses.exceptions.AlreadyExistsException;
import ua.raif.courses.exceptions.DateValidationException;
import ua.raif.courses.exceptions.SpeakerException;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TalkServiceImplTest {

    @Mock
    private TalkDao talkDao;


    @Mock
    private ConferenceService conferenceService;

    private TalkServiceImpl talkService;

    private TalkCreateDto talkDto;


    @BeforeEach
    void setUp() {
        talkService = Mockito.spy(new TalkServiceImpl(talkDao, conferenceService));
        talkDto = TalkCreateDto.builder()
                .caption("Talk Caption")
                .description("Talk Description")
                .talkType("LECTURE")
                .speaker("Bond")
                .build();
    }

    @Test
    void addTalk() {
        doReturn(getConference()).when(conferenceService).getById(anyLong());
        doReturn(Talk.fromDto(talkDto, getConference()).asEntity()).when(talkDao).save(any(TalkEntity.class));
        talkService.addTalk(talkDto, 2L);

        verify(talkDao, times(1)).save(any(TalkEntity.class));
    }

    @Test
    void findAllTalksInConference() {
        doReturn(Collections.singletonList(Talk.fromDto(talkDto, getConference()).asEntity())).when(talkDao).findAllByConferenceId(anyLong());
        var talks = talkService.findAllTalksInConference(2L);

        Assertions.assertEquals(1, talks.size());
    }

    @Test
    void validateConferenceStartDate() {
        talkService.validateConferenceStartdate(Conference.fromEntity(getConference()));
    }

    @Test
    void invalidConferenceStartDate() {
        var conference = Conference.fromEntity(getConference());
        conference.setDates(new ConferenceDates(LocalDate.of(2020, 2, 2), LocalDate.of(2120, 2, 2)));

        Assertions.assertThrows(DateValidationException.class, () -> talkService.validateConferenceStartdate(conference));
    }

    @Test
    void validateSpeakerDuplication() {
        doReturn(1).when(talkDao).countAllBySpeaker(anyString());
        talkService.validateSpeakerDuplication(talkDto);
    }

    @Test
    void speakerDuplicates() {
        doReturn(3).when(talkDao).countAllBySpeaker(anyString());
        Assertions.assertThrows(SpeakerException.class, () -> talkService.validateSpeakerDuplication(talkDto));
    }

    @Test
    void validateCaptionDuplication() {
        doReturn(false).when(talkDao).existsAllByCaption(anyString());
        talkService.validateCaptionDuplication(talkDto);
    }

    @Test
    void captionDuplicates() {
        doReturn(true).when(talkDao).existsAllByCaption(anyString());
        Assertions.assertThrows(AlreadyExistsException.class, () -> talkService.validateCaptionDuplication(talkDto));
    }


    ConferenceEntity getConference() {
        return ConferenceEntity.builder()
                .capacity(300)
                .caption("Conference Caption")
                .description("Conference Despcription")
                .dateStart(LocalDate.of(2120, 2, 2))
                .dateEnd(LocalDate.of(2120, 2, 2))
                .build();
    }
}
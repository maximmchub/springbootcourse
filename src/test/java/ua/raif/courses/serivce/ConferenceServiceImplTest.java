package ua.raif.courses.serivce;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.raif.courses.api.dto.ConferenceCreateDto;
import ua.raif.courses.dao.ConferenceDao;
import ua.raif.courses.dao.entity.ConferenceEntity;
import ua.raif.courses.domain.Conference;
import ua.raif.courses.exceptions.AlreadyExistsException;
import ua.raif.courses.exceptions.DateValidationException;
import ua.raif.courses.exceptions.NotExistsException;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConferenceServiceImplTest {

    @Mock
    private ConferenceDao conferenceDao;

    private ConferenceServiceImpl conferenceService;

    private ConferenceCreateDto conferenceDto;

    @BeforeEach
    void setUp() {
        conferenceService = Mockito.spy(new ConferenceServiceImpl(conferenceDao));
        conferenceDto = ConferenceCreateDto.builder()
                .capacity(300)
                .caption("Conference Caption")
                .description("Conference Despcription")
                .dateStart("2020-02-02")
                .dateEnd("2020-02-02")
                .build();
    }

    @Test
    void addConferencePositive() {
        doReturn(getEnitiy()).when(conferenceDao).save(any(ConferenceEntity.class));

        conferenceService.addConference(conferenceDto);

        verify(conferenceDao, times(1)).save(any(ConferenceEntity.class));
    }

    @Test
    void validatorDatesOverlaps() {
        doReturn(Collections.singletonList(getEnitiy())).when(conferenceDao).findAll();
        Assert.assertThrows(DateValidationException.class, () -> conferenceService.validateConferenceDatesOverlaps(Conference.fromDto(conferenceDto)));
    }

    @Test
    void validatorCaptionDuplicates() {
        doReturn(true).when(conferenceDao).existsByCaption(anyString());

        Assert.assertThrows(AlreadyExistsException.class, () -> conferenceService.validateConferenceExists(Conference.fromDto(conferenceDto)));
    }

    @Test
    void updateConferencePositive() {
        doReturn(true).when(conferenceDao).existsById(anyLong());
        doReturn(getEnitiy()).when(conferenceDao).save(any(ConferenceEntity.class));

        conferenceService.updateConference(conferenceDto, 2L);

        verify(conferenceDao, times(1)).save(any(ConferenceEntity.class));
    }

    @Test
    void updateConferenceWrongId() {
        doReturn(false).when(conferenceDao).existsById(anyLong());

        Assert.assertThrows(NotExistsException.class, () -> conferenceService.updateConference(conferenceDto, 2L));
    }

    @Test
    void findAllConferences() {
        doReturn(Collections.singletonList(getEnitiy())).when(conferenceDao).findAll();

        var conferences = conferenceService.findAllConferences();

        Assert.assertEquals(1, conferences.size());
    }


    @Test
    void getByIdPositive() {
        doReturn(Optional.of(getEnitiy())).when(conferenceDao).findById(anyLong());
        var conference = conferenceService.getById(2L);

        Assert.assertEquals("Conference Despcription", conference.getDescription());
    }

    private ConferenceEntity getEnitiy() {
        return Conference.fromDto(conferenceDto).asEntity();
    }

    @Test
    void getByIdNotExists() {
        Assert.assertThrows(NotExistsException.class, () -> conferenceService.getById(2L));
    }

}
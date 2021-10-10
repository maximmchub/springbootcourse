package ua.raif.courses.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.raif.courses.api.dto.ConferenceCreateDto;
import ua.raif.courses.api.dto.ConferenceViewDto;
import ua.raif.courses.api.dto.TalkCreateDto;
import ua.raif.courses.api.dto.TalkViewDto;
import ua.raif.courses.exceptions.AlreadyExistsException;
import ua.raif.courses.exceptions.InvalidRequestException;
import ua.raif.courses.serivce.ConferenceService;
import ua.raif.courses.serivce.TalkService;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ConferenceRestController.class)
@ActiveProfiles("test")
class ConferenceRestControllerTest {

    private static final long CONFERENCE_ID = 4L;
    private static final long TALK_ID = 2L;
    private ConferenceCreateDto conference;
    private TalkCreateDto talk;

    @MockBean
    private ConferenceService conferenceService;
    @MockBean
    private TalkService talkService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        conference = buildValidConferenceCreate();
        talk = buildValidTalkCreate();
    }

    @Test
    void addConference() throws Exception {
        doReturn(buildValidConferenceView()).when(conferenceService).addConference(refEq(conference));
        mockMvc.perform(post("/conferences")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(conference)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void conferenceHasDataConflict() throws Exception {
        doThrow(AlreadyExistsException.class).when(conferenceService).addConference(refEq(conference));
        mockMvc.perform(post("/conferences")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(conference)))
                .andExpect(status().isConflict());
    }

    @Test
    public void conferenceHasInvalidData() throws Exception {
        doThrow(InvalidRequestException.class).when(conferenceService).addConference(refEq(conference));
        mockMvc.perform(post("/conferences")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(conference)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void conferenceHasEmptyCaption() throws Exception {
        //doReturn(buildValidConferenceView()).when(conferenceService).addConference(refEq(conference));
        conference.setCaption("");
        mockMvc.perform(post("/conferences")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(conference)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void conferenceHasEmptyDescription() throws Exception {
        //doReturn(buildValidConferenceView()).when(conferenceService).addConference(refEq(conference));
        conference.setCaption("");
        mockMvc.perform(post("/conferences")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(conference)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void conferenceHasLowCapacity() throws Exception {
        //doReturn(buildValidConferenceView()).when(conferenceService).addConference(refEq(conference));
        conference.setCapacity(50);
        mockMvc.perform(post("/conferences")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(conference)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void conferenceHasEmptyDateStart() throws Exception {
        //doReturn(buildValidConferenceView()).when(conferenceService).addConference(refEq(conference));
        conference.setDateStart("");
        mockMvc.perform(post("/conferences")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(conference)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void conferenceHasEmptyDateEnd() throws Exception {
        //doReturn(buildValidConferenceView()).when(conferenceService).addConference(refEq(conference));
        conference.setDateEnd("");
        mockMvc.perform(post("/conferences")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(conference)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void updateConference() throws Exception {

        doReturn(buildValidConferenceView()).when(conferenceService).updateConference(refEq(conference), eq(CONFERENCE_ID));
        mockMvc.perform(put("/conferences/" + CONFERENCE_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(conference)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void findConference() throws Exception {

        doReturn(List.of(buildValidConferenceView())).when(conferenceService).findAllConferences();
        mockMvc.perform(get("/conferences")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].caption").value("Conference Caption"))
                .andExpect(jsonPath("$[0].description").value("Conference Despcription"))
                .andExpect(jsonPath("$[0].dateStart").value("2020-02-02"))
                .andExpect(jsonPath("$[0].dateEnd").value("2020-02-02"))
                .andExpect(jsonPath("$[0].id").value(CONFERENCE_ID));
    }

    @Test
    void addTalk() throws Exception {
        doReturn(buildValidTalkView()).when(talkService).addTalk(refEq(talk), eq(CONFERENCE_ID));
        mockMvc.perform(post("/conferences/" + CONFERENCE_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(talk)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(TALK_ID));
    }

    @Test
    void findAllTalksInConference() throws Exception {
        doReturn(List.of(buildValidTalkView())).when(talkService).findAllTalksInConference(eq(CONFERENCE_ID));
        mockMvc.perform(get("/conferences/" + CONFERENCE_ID + "/talks")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].caption").value("Talk Caption"))
                .andExpect(jsonPath("$[0].description").value("Talk Despcription"))
                .andExpect(jsonPath("$[0].speaker").value("Bill Bootstrap"))
                .andExpect(jsonPath("$[0].id").value(TALK_ID));
    }

    private ConferenceViewDto buildValidConferenceView() {
        return ConferenceViewDto.builder()
                .capacity(300)
                .caption("Conference Caption")
                .description("Conference Despcription")
                .dateStart("2020-02-02")
                .dateEnd("2020-02-02")
                .id(CONFERENCE_ID)
                .build();
    }

    private ConferenceCreateDto buildValidConferenceCreate() {
        return ConferenceCreateDto.builder()
                .capacity(300)
                .caption("Conference Caption")
                .description("Conference Despcription")
                .dateStart("2020-02-02")
                .dateEnd("2020-02-02")
                .build();
    }

    private TalkViewDto buildValidTalkView() {
        return TalkViewDto.builder()
                .caption("Talk Caption")
                .description("Talk Despcription")
                .speaker("Bill Bootstrap")
                .talkType("LECTURE")
                .id(TALK_ID)
                .build();
    }

    private TalkCreateDto buildValidTalkCreate() {
        return TalkCreateDto.builder()
                .caption("Talk Caption")
                .description("Talk Despcription")
                .speaker("Bill Bootstrap")
                .talkType("LECTURE")
                .build();
    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

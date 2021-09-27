package ua.raif.courses.api;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.raif.courses.api.dto.ConferenceCreateDto;
import ua.raif.courses.api.dto.ConferenceViewDto;
import ua.raif.courses.api.dto.TalkCreateDto;
import ua.raif.courses.api.dto.TalkViewDto;
import ua.raif.courses.exceptions.ConflictException;
import ua.raif.courses.exceptions.InvalidRequestException;
import ua.raif.courses.serivce.ConferenceService;
import ua.raif.courses.serivce.TalkService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/conferences")
public class ConferenceRestController {
    private final ConferenceService conferenceService;
    private final TalkService talkService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Long addConference(@RequestBody ConferenceCreateDto conference) {
        return conferenceService.addConference(conference);
    }

    @PutMapping(path = "/{conferenceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Long updateConference(@PathVariable("conferenceId") Long conferenceId, @RequestBody @Valid ConferenceCreateDto conference) {
        return conferenceService.updateConference(conference, conferenceId);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<ConferenceViewDto> findConferences() {
        return conferenceService.findAllConferences();
    }

    @PostMapping(path = "/{conferenceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Long addTalk(@PathVariable("conferenceId") Long conferenceId, @RequestBody @Valid TalkCreateDto talk) {
        return talkService.addTalk(talk, conferenceId);
    }

    @GetMapping(path = "/{conferenceId}/talks", produces = MediaType.APPLICATION_JSON_VALUE)
    List<TalkViewDto> findAllTalksIn(@PathVariable("conferenceId") Long conferenceId) {
        return talkService.findAllTalksInConference(conferenceId);
    }


    @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class, InvalidRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request data")
    void onSaveError() {
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Conflict in request data")
    void onSaveConflict() {
    }

}

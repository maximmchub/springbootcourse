package ua.raif.courses.serivce.validators;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.raif.courses.domain.Conference;
import ua.raif.courses.exceptions.AlreadyExistsException;
import ua.raif.courses.serivce.ConferenceService;

@Slf4j
@AllArgsConstructor
@Service
public class ConferenceExistValidator implements Validator {

    private final ConferenceService conferenceService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Conference.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Conference conference = (Conference) target;
        if (conferenceService.isConferenceExist(conference.getCaption())) {
            LOG.info("Conference with caption <" + conference.getCaption() + "> already exists.");
            throw new AlreadyExistsException("Conference with caption <" + conference.getCaption() + "> already exists.");
        }

    }
}





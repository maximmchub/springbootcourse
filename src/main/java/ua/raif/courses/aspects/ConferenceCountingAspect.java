package ua.raif.courses.aspects;

import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ConferenceCountingAspect {
    @Autowired
    @Qualifier("conferenceCounterPos")
    private final Counter conferenceCounterPos;

    @Autowired
    @Qualifier("conferenceCounterNeg")
    private final Counter conferenceCounterNeg;

    @Autowired
    @Qualifier("talksCounterPos")
    private final Counter talksCounterPos;

    @Autowired
    @Qualifier("talksCounterNeg")
    private final Counter talksCounterNeg;

    @Pointcut("execution(* ua.raif.courses.serivce.ConferenceService.addConference(..))")
    public void addingConference() {
    }

    @Pointcut("execution(* ua.raif.courses.serivce.TalkService.addTalk(..))")
    public void addingTalk() {
    }

    @AfterReturning("addingConference()")
    public void countConferencePositive() {
        conferenceCounterPos.increment();
    }

    @AfterThrowing("addingConference()")
    public void countConferenceNegative() {
        conferenceCounterNeg.increment();
    }

    @AfterReturning("addingTalk()")
    public void countTalkPositive() {
        talksCounterPos.increment();
    }

    @AfterThrowing("addingTalk()")
    public void countTalkNegative() {
        talksCounterNeg.increment();
    }

}

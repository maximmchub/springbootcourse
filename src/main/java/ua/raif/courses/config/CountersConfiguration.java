package ua.raif.courses.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableAspectJAutoProxy
@RequiredArgsConstructor
@Configuration
public class CountersConfiguration {
    private final MeterRegistry meterRegistry;

    @Bean
    public Counter conferenceCounterPos() {
        return meterRegistry.counter("conferences.created.count", "result", "positive");
    }

    @Bean
    public Counter conferenceCounterNeg() {
        return meterRegistry.counter("conferences.created.count", "result", "negative");
    }

    @Bean
    public Counter talksCounterPos() {
        return meterRegistry.counter("talks.created.count", "result", "positive");
    }

    @Bean
    public Counter talksCounterNeg() {
        return meterRegistry.counter("talks.created.count", "result", "negative");
    }
}



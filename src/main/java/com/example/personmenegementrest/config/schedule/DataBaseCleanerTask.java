package com.example.personmenegementrest.config.schedule;

import com.example.personmenegementrest.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class DataBaseCleanerTask {
    private final PersonRepository personRepository;

    @Scheduled(cron = "${cron.expression}")
    public void deletePersonTask() {
        personRepository.deletePersonOldTask();
    }
}

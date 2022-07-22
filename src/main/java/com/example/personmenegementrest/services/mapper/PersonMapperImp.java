package com.example.personmenegementrest.services.mapper;

import com.example.personmenegementrest.api.PersonMapper;
import com.example.personmenegementrest.api.TaskMapper;
import com.example.personmenegementrest.dto.Person;
import com.example.personmenegementrest.dto.Task;
import com.example.personmenegementrest.entity.PersonEntity;
import com.example.personmenegementrest.entity.TaskEntity;
import com.example.personmenegementrest.types.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonMapperImp implements PersonMapper {

    private final TaskMapper taskMapper;

    public Person personEntityToPerson(PersonEntity personEntity) {
        log.info("Was calling personEntityToPerson. Input personEntity: " + personEntity.toString());
        return Person.builder()
                .id(String.valueOf(personEntity.getId()))
                .name(personEntity.getName())
                .age(String.valueOf(personEntity.getAge()))
                .email(personEntity.getEmail())
                .salary(String.valueOf(personEntity.getSalary()))
                .position(personEntity.getPosition().toString())
                .experience(personEntity.getExperience().toString())
                .tasks(getTasks(personEntity.getTasks()))
                .build();
    }

    public PersonEntity personToPersonEntity(Person person) {
        log.info("Was calling personToPersonEntity. Input id: " + person.toString());
        return PersonEntity.builder()
                .id(getId(person))
                .name(person.getName())
                .age(Integer.valueOf(person.getAge()))
                .email(person.getEmail())
                .salary(new BigDecimal(person.getSalary()))
                .position(Position.getDefine(person.getPosition()))
                .experience(Double.valueOf(person.getExperience()))
                .build();
    }

    private Long getId(Person person) {
        log.debug("Was calling getId. Input id: " + person.toString());
        if (person.getId() == null) {
            return null;
        }
        return Long.valueOf(person.getId());
    }

    private List<Task> getTasks(List<TaskEntity> taskEntities) {
        log.debug("Was calling getTasks. Input id: " + taskEntities.toString());
        return taskEntities
                .stream()
                .map(taskMapper::taskEntityToTask)
                .collect(Collectors.toList());
    }
}

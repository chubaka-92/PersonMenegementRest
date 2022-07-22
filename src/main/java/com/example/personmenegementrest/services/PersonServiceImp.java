package com.example.personmenegementrest.services;


import com.example.personmenegementrest.api.PersonMapper;
import com.example.personmenegementrest.api.PersonService;
import com.example.personmenegementrest.api.PersonValidation;
import com.example.personmenegementrest.dao.PersonDAO;
import com.example.personmenegementrest.dto.Person;
import com.example.personmenegementrest.entity.PersonEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImp implements PersonService {
    private static final String MESSAGE = "message";
    private static final String PERSON_NOT_FOUND = "personNotFound";
    private static final String PERSONS_NOT_FOUND = "personsNotFound";
    private final PersonDAO personDao;
    private final PersonMapper personMapper;
    private final PersonValidation personValidation;
    private final ResourceBundle errorMsg = ResourceBundle.getBundle(MESSAGE);

    public ResponseEntity getPersonById(Long id) {
        log.info("Was calling getPersonById. Input id: " + id);
        PersonEntity personEntity = personDao.findPersonById(id);

        if (personEntity == null) {
            log.error(MessageFormat.format(errorMsg.getString(PERSON_NOT_FOUND), id));
            return ResponseEntity.badRequest().body(MessageFormat.format(errorMsg.getString(PERSON_NOT_FOUND), id));
        }
        return ResponseEntity.ok(personMapper.personEntityToPerson(personEntity));

    }

    public ResponseEntity getPersons() {
        log.info("Was calling getPersons.");
        List<PersonEntity> persons = personDao.findPersons();

        if (persons == null) {
            log.error(errorMsg.getString(PERSONS_NOT_FOUND));
            return ResponseEntity.badRequest().body(errorMsg.getString(PERSONS_NOT_FOUND));
        }
        return ResponseEntity.ok(persons.stream()
                .map(personMapper::personEntityToPerson)
                .collect(Collectors.toList()));
    }

    public ResponseEntity addNewPerson(Person person) {
        log.info("Was calling addNewPerson. Input person: " + person);
        personValidation.addPersonValidator(person);

        if (!person.isValid()) {
            log.error(person.toString());
            return ResponseEntity.badRequest().body(person);
        }

        return ResponseEntity.ok(personMapper
                .personEntityToPerson(personDao
                        .addPerson(personMapper.personToPersonEntity(person))));
    }

    public ResponseEntity updatePerson(Person person) {
        log.info("Was calling updatePerson. Input person: " + person);
        personValidation.updatePersonValidator(person);

        if (!person.isValid()) {
            log.error(person.toString());
            return ResponseEntity.badRequest().body(person);
        }
        return ResponseEntity.ok(personMapper
                .personEntityToPerson(personDao
                        .updatePerson(personMapper.personToPersonEntity(person))));
    }


    public ResponseEntity deletePerson(Long id) {
        log.info("Was calling deletePerson. Input id: " + id);
        if (personDao.findPersonById(id) == null) {
            log.error(MessageFormat.format(errorMsg.getString(PERSON_NOT_FOUND), id));
            return ResponseEntity.badRequest().body(MessageFormat.format(errorMsg.getString(PERSON_NOT_FOUND), id));
        }
        personDao.deletePersonById(id);
        return ResponseEntity.ok(id);
    }


    public ResponseEntity addNewPersons(List<Person> persons) {
        log.info("Was calling addNewPersons. Input persons: " + persons);
        List<Person> response = new ArrayList<>();
        for (Person person : persons) {
            personValidation.addPersonValidator(person);
            if (person.isValid()) {
                response.add(personMapper
                        .personEntityToPerson(personDao
                                .addPerson(personMapper.personToPersonEntity(person))));
            } else {
                log.error(person.toString());
                response.add(person);
            }
        }
        return ResponseEntity.ok(response);
    }
}

package com.example.personmenegementrest.services;

import com.example.personmenegementrest.config.api.PersonService;
import com.example.personmenegementrest.config.api.PersonValidation;
import com.example.personmenegementrest.dao.PersonDAO;
import com.example.personmenegementrest.dto.Person;
import com.example.personmenegementrest.entity.PersonEntity;
import com.example.personmenegementrest.services.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImp implements PersonService {
    public static final String MESSAGE = "message";
    public static final String PERSON_NOT_FOUND = "personNotFound";
    public static final String PERSONS_NOT_FOUND = "personsNotFound";
    private final PersonDAO personDao;
    private final PersonMapper personMapper;
    private final PersonValidation personValidation;
    private final ResourceBundle errorMsg = ResourceBundle.getBundle(MESSAGE);

    public ResponseEntity getPersonById(Long id) {

        PersonEntity personEntity = personDao.findPersonById(id);

        if (personEntity == null) {
            return ResponseEntity.badRequest().body(MessageFormat.format(errorMsg.getString(PERSON_NOT_FOUND), id));
        }
        return ResponseEntity.ok(personMapper.personEntityToPerson(personEntity));

    }

    public ResponseEntity getPersons() {

        List<PersonEntity> person = personDao.findPersons();

        if (person == null) {
            return ResponseEntity.badRequest().body(errorMsg.getString(PERSONS_NOT_FOUND));
        }
        return ResponseEntity.ok(person.stream()
                .map(personMapper::personEntityToPerson)
                .collect(Collectors.toList()));

    }

    public ResponseEntity addNewPerson(Person person) {
        personValidation.addPersonValidator(person);

        if (!person.isValid()) {
            return ResponseEntity.badRequest().body(person);
        }

        return ResponseEntity.ok(personMapper
                .personEntityToPerson(personDao
                        .addPerson(personMapper.personToPersonEntity(person))));
    }

    public ResponseEntity updatePerson(Person person) {
        personValidation.updatePersonValidator(person);

        if (!person.isValid()) {
            return ResponseEntity.badRequest().body(person);
        }
        return ResponseEntity.ok(personMapper
                .personEntityToPerson(personDao
                        .updatePerson(personMapper.personToPersonEntity(person))));
    }

    public ResponseEntity deletePerson(long id) {

        if (personDao.findPersonById(id) == null) {
            return ResponseEntity.badRequest().body(MessageFormat.format(errorMsg.getString(PERSON_NOT_FOUND), id));
        }
        personDao.deletePersonById(id);
        return ResponseEntity.ok(id);
    }


    public ResponseEntity addNewPersons(List<Person> persons) {
        List<Person> response = new ArrayList<>();
        for (Person person : persons) {
            personValidation.addPersonValidator(person);
            if (person.isValid()) {
                response.add(personMapper
                        .personEntityToPerson(personDao
                                .addPerson(personMapper.personToPersonEntity(person))));
            } else {
                response.add(person);
            }
        }
        return ResponseEntity.ok(response);
    }
}

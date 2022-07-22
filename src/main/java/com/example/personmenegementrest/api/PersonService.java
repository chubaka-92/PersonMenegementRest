package com.example.personmenegementrest.api;

import com.example.personmenegementrest.dto.Person;
import com.example.personmenegementrest.dto.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonService {

    ResponseEntity getPersonById(Long id);

    ResponseEntity getPersons();

    ResponseEntity addNewPerson(Person person);

    ResponseEntity addNewPersons(List<Person> person);

    ResponseEntity updatePerson(Person person);

    ResponseEntity deletePerson(Long id);
}

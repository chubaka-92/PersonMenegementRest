package com.example.personmenegementrest.controller;

import com.example.personmenegementrest.config.api.PersonService;
import com.example.personmenegementrest.dto.Person;
import com.example.personmenegementrest.entity.PersonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity getPerson(@PathVariable("id") Long id) {
        return personService.getPersonById(Long.valueOf(id));
    }

    @GetMapping()
    public ResponseEntity getPersons() {
        return personService.getPersons();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable("id") Long id) {
        return personService.deletePerson(Long.valueOf(id));
    }

    @PostMapping("/person")
    public ResponseEntity createPerson(@RequestBody Person person) {
        return personService.addNewPerson(person);
    }

    @PutMapping()
    public ResponseEntity updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @PostMapping()
    public ResponseEntity createPersons(@RequestBody List<Person> persons) {
        return personService.addNewPersons(persons);
    }
}

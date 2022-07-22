package com.example.personmenegementrest.dao;

import com.example.personmenegementrest.entity.PersonEntity;
import com.example.personmenegementrest.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PersonDAO {
    private final PersonRepository personRepository;

    public PersonEntity findPersonById(Long id) {
        log.info("Was calling findPersonById. Input id: "+ id);
        return personRepository.findById(id).orElse(null);
    }

    public List<PersonEntity> findPersons() {
        log.info("Was calling findPersons.");
        return (List<PersonEntity>) personRepository.findAll();
    }

    @Transactional
    public PersonEntity addPerson(PersonEntity personEntity) {
        log.info("Was calling addPerson. Input id: "+ personEntity.toString());
        return personRepository.save(personEntity);
    }

    @Transactional
    public PersonEntity updatePerson(PersonEntity personEntity) {
        log.info("Was calling updatePerson. Input id: "+ personEntity.toString());
        return personRepository.save(personEntity);
    }

    @Transactional
    public void deletePersonById(long personId) {
        log.info("Was calling deletePersonById. Input id: "+ personId);
        personRepository.deleteById(personId);
    }
}

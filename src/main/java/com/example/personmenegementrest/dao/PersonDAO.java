package com.example.personmenegementrest.dao;

import com.example.personmenegementrest.entity.PersonEntity;
import com.example.personmenegementrest.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonDAO {
    private final PersonRepository personRepository;

    public PersonEntity findPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }


    public List<PersonEntity> findPersons() {
        return (List<PersonEntity>) personRepository.findAll();
    }

    @Transactional
    public PersonEntity addPerson(PersonEntity personEntity) {
        return personRepository.save(personEntity);
    }

    @Transactional
    public PersonEntity updatePerson(PersonEntity personEntity) {
        return personRepository.save(personEntity);
    }

    @Transactional
    public void deletePersonById(long personId) {
        personRepository.deleteById(personId);
    }

}

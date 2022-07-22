package com.example.personmenegementrest.api;

import com.example.personmenegementrest.dto.Person;
import com.example.personmenegementrest.entity.PersonEntity;

public interface PersonMapper {

    PersonEntity personToPersonEntity(Person person);

    Person personEntityToPerson(PersonEntity personEntity);
}

package com.example.personmenegementrest.api;

import com.example.personmenegementrest.dto.Person;

public interface PersonValidation {

    void addPersonValidator(Person person);

    void updatePersonValidator(Person person);
}

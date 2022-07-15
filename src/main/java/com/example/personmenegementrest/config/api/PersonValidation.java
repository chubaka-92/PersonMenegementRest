package com.example.personmenegementrest.config.api;

import com.example.personmenegementrest.dto.Person;

public interface PersonValidation {

    void addPersonValidator(Person person);

    void updatePersonValidator(Person person);
}

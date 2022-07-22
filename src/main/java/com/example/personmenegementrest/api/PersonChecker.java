package com.example.personmenegementrest.api;

import com.example.personmenegementrest.dto.Person;

public interface PersonChecker {

    void checkPersonRequiredFields(Person person);

    void checkPersonSalary(Person person);

    void checkPersonExperienceForPosition(Person person);
}

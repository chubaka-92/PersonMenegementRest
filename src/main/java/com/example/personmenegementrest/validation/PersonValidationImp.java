package com.example.personmenegementrest.validation;

import com.example.personmenegementrest.config.api.PersonChecker;
import com.example.personmenegementrest.config.api.PersonValidation;
import com.example.personmenegementrest.dto.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonValidationImp implements PersonValidation {

    private final PersonChecker personChecker;

    public void addPersonValidator(Person person) {
        personChecker.checkPersonRequiredFields(person);
        if (!person.isValid()) {
            return;
        }
        personChecker.checkPersonSalary(person);
        if (!person.isValid()) {
            return;
        }

        personChecker.checkPersonExperienceForPosition(person);

    }

    public void updatePersonValidator(Person person) {
        personChecker.checkPersonRequiredFields(person);
        if (!person.isValid()) {
            return;
        }
        personChecker.checkPersonSalary(person);
        if (!person.isValid()) {
            return;
        }

        personChecker.checkPersonExperienceForPosition(person);
    }

}

package com.example.personmenegementrest.validation;


import com.example.personmenegementrest.config.api.PersonChecker;
import com.example.personmenegementrest.dto.Person;
import com.example.personmenegementrest.types.Position;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@Service
public class PersonCheckerImp implements PersonChecker {
    public static final String EMPTY_FIELD = "emptyField";
    public static final String INCORRECT_AGE = "incorrectAge";
    public static final String INCORRECT_POSITION = "incorrectPosition";
    public static final String INCORRECT_SALARY = "incorrectSalary";
    public static final String LITTLE_WORK_EXPERIENCE = "littleWorkExperience";
    private final ResourceBundle errorMsg = ResourceBundle.getBundle("message");

    public void checkPersonRequiredFields(Person person) {

        if (person.getName() == null || person.getName().trim().equals("")) {
            person.setName(errorMsg.getString(EMPTY_FIELD));
            person.setValid(false);
        }
        if (person.getSalary() == null || person.getSalary().equals("")) {
            person.setSalary(errorMsg.getString(EMPTY_FIELD));
            person.setValid(false);
        }
        if (person.getAge() == null || person.getAge().equals("")) {
            person.setAge(errorMsg.getString(EMPTY_FIELD));
            person.setValid(false);
        }
        if (person.getAge() != null && Integer.parseInt(person.getAge()) < 16) {
            person.setAge(errorMsg.getString(INCORRECT_AGE));
            person.setValid(false);
        }
        if (person.getPosition() == null || person.getPosition().equals("")) {
            person.setPosition(errorMsg.getString(EMPTY_FIELD));
            person.setValid(false);
        }
        if (person.getPosition() != null && Position.getDefine(person.getPosition()) == Position.UNDEFINED) {
            person.setPosition(errorMsg.getString(INCORRECT_POSITION));
            person.setValid(false);
        }
        if (person.getExperience() == null || person.getExperience().equals("")) {
            person.setExperience(errorMsg.getString(EMPTY_FIELD));
            person.setValid(false);
        }
    }


    public void checkPersonSalary(Person person) {
        Position positionPers = Position.getDefine(person.getPosition());
        if (!checkSalaryMatchingPosition(positionPers, new BigDecimal(person.getSalary()))) {
            person.setValid(false);
            person.setSalary(MessageFormat.format(errorMsg.getString(INCORRECT_SALARY),
                    positionPers,
                    positionPers.getSalaryMin(),
                    positionPers.getSalaryMax()));
        }
    }

    public void checkPersonExperienceForPosition(Person person) {
        Position positionPerson = Position.getDefine(person.getPosition());
        if (!checkExperienceMatchingPosition(positionPerson, person.getExperience())) {
            person.setValid(false);
            person.setExperience(MessageFormat.format(errorMsg.getString(LITTLE_WORK_EXPERIENCE),
                    positionPerson.getWorkExperience(),
                    positionPerson.toString()));
        }
    }

    private boolean checkSalaryMatchingPosition(Position positionPerson, BigDecimal salaryPerson) {
        if (positionPerson.getSalaryMin().compareTo(salaryPerson) > 0
                || positionPerson.getSalaryMax().compareTo(salaryPerson) < 0) {
            return false;
        }
        return true;
    }

    private boolean checkExperienceMatchingPosition(Position positionPerson, String experience) {
        if (positionPerson.getWorkExperience().compareTo(Double.valueOf(experience)) > 0) {
            return false;
        }
        return true;
    }
}

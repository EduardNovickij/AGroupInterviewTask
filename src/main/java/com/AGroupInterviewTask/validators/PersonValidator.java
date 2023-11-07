package com.AGroupInterviewTask.validators;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.repositories.PersonRepository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Date;

public record PersonValidator() {
    private static final DateValidator dateValidator = new DateValidator();

    public void checkPersonInput(Person person) throws Exception {
        dateValidator.checkDateFormat(person.getBirthDate());

        int maxGivenNameLength = 50;
        int minGivenNameLength = 1;

        int maxFamilyNameLength = 50;
        int minFamilyNameLength = 1;

        int genderLength = 1;

        String errorMessageStart = "Incorrect input: \n";
        String errorMessageEnd = "";

        if(person.getGivenName().length() > maxGivenNameLength) errorMessageEnd += "givenName is too long\n";
        if(person.getGivenName().length() < minGivenNameLength) errorMessageEnd += "givenName is too short\n";

        if(person.getFamilyName().length() > maxFamilyNameLength) errorMessageEnd += "familyName is too long\n";
        if(person.getFamilyName().length() < minFamilyNameLength) errorMessageEnd += "familyName is too short\n";

        if(person.getGender().length() != genderLength) errorMessageEnd += "gender must be of length " + genderLength + "\n";

        if(person.getBirthDate().length() == 0) errorMessageEnd += "birthDate can't be empty\n";

        if(!errorMessageEnd.equals("")) throw new Exception(errorMessageStart + errorMessageEnd);
    }

    public void checkIfPersonExists(
            Integer personId, PersonRepository personRepository)
            throws Exception {
        try {
            personRepository.findOne(new Date(System.currentTimeMillis()).toString(), personId);
        }
        catch (EmptyResultDataAccessException ignored) { throw new Exception(
                "Person with this personId does not exist."); }
    }
}

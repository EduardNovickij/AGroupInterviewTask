package com.AGroupInterviewTask.validators;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.repositories.PersonRepository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Date;

public record PersonValidator() {
    private static final DateValidator dateValidator = new DateValidator();

    public void checkPersonInput(Person person) throws Exception {

        int maxGivenNameLength = 50;

        int maxFamilyNameLength = 50;

        int genderLength = 1;

        String errorMessageStart = "Incorrect input: \n";
        String errorMessageEnd = "";

        if(person.getBirthDate() == null) throw new Exception(errorMessageStart + "birthDate can't be null.");
        dateValidator.checkDateFormat(person.getBirthDate());

        if(person.getGivenName() == null) errorMessageEnd += "givenName can't be null.\n";
        else if(person.getGivenName().length() > maxGivenNameLength) errorMessageEnd += "givenName is too long.\n";

        if(person.getFamilyName() == null) errorMessageEnd += "familyName can't be null.\n";
        else if(person.getFamilyName().length() > maxFamilyNameLength) errorMessageEnd += "familyName is too long.\n";

        if(person.getGender() == null) errorMessageEnd += "gender can't be null.\n";
        else if(person.getGender().length() != genderLength) errorMessageEnd += "gender must be of length " + genderLength + ".\n";

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

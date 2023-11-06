package com.AGroupInterviewTask.validators;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.repositories.PersonRepository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public record PersonValidator() {
    private static boolean isDateFormatValid(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);
            formatter.parse(date);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    public void checkPersonInput(Person person) throws Exception {
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
        if(!isDateFormatValid(person.getBirthDate())) errorMessageEnd += "birthDate must be in YYYY-MM-DD format\n";

        if(!errorMessageEnd.equals("")) throw new Exception(errorMessageStart + errorMessageEnd);
    }

    public void dateValidator(String asOfDate) throws Exception {
        String errorMessageStart = "Incorrect input: \n";
        String errorMessageEnd = "";

        if(asOfDate.length() == 0) errorMessageEnd += "asOfDate can't be empty\n";
        if(!isDateFormatValid(asOfDate)) errorMessageEnd += "asOfDate must be in YYYY-MM-DD format\n";

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

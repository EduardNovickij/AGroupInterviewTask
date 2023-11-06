package com.AGroupInterviewTask.validators;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.repositories.PersonAddressRepository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public record PersonAddressValidator() {

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

    public void personValidator(Person person) throws Exception {
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

    public void checkPersonAddressInput(PersonAddress personAddress) throws Exception {
        int cityMaxLength = 50;
        int cityMinLength = 1;

        int streetMaxLength = 50;
        int streetMinLength = 1;

        int appartmentMaxLength = 50;
        int appartmentMinLength = 0;

        String errorMessageStart = "Incorrect input: \n";
        String errorMessageEnd = "";

        if(personAddress.getCity().length() > cityMaxLength) errorMessageEnd += "city is too long\n";
        if(personAddress.getCity().length() < cityMinLength) errorMessageEnd += "city is too short\n";

        if(personAddress.getStreet().length() > streetMaxLength) errorMessageEnd += "street is too long\n";
        if(personAddress.getStreet().length() < streetMinLength) errorMessageEnd += "street is too short\n";

        if(personAddress.getAppartment().length() > appartmentMaxLength) errorMessageEnd += "appartment is too long\n";
        if(personAddress.getAppartment().length() < appartmentMinLength) errorMessageEnd += "appartment is too short\n";

        if(!errorMessageEnd.equals("")) throw new Exception(errorMessageStart + errorMessageEnd);
    }

    public void checkIfPersonAddressAlreadyExists(
            Integer personId, String addressType, PersonAddressRepository personAddressRepository)
    throws Exception {
        try {
            personAddressRepository.findOne(new Date(System.currentTimeMillis()).toString(), personId, addressType);
            throw new Exception("PersonAddress with this personId and addressType already exists.");
        }
        catch (EmptyResultDataAccessException ignored) {}
    }

    public void checkIfPersonAddressExists(
            Integer personId, String addressType, PersonAddressRepository personAddressRepository)
            throws Exception {
        try {
            personAddressRepository.findOne(new Date(System.currentTimeMillis()).toString(), personId, addressType);
        }
        catch (EmptyResultDataAccessException ignored) { throw new Exception(
                "Person Address with this personId and addressType does not exist."); }
    }
}

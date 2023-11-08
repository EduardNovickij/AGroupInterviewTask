package com.AGroupInterviewTask.validators;

import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.repositories.PersonAddressRepository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Date;

public record PersonAddressValidator() {

    public void checkPersonAddressInput(PersonAddress personAddress) throws Exception {
        int cityMaxLength = 50;

        int streetMaxLength = 50;

        int appartmentMaxLength = 50;

        String errorMessageStart = "Incorrect input: \n";
        String errorMessageEnd = "";

        if(personAddress.getCity() == null) errorMessageEnd += "city can't be null.\n";
        else if(personAddress.getCity().length() > cityMaxLength) errorMessageEnd += "city is too long.\n";

        if(personAddress.getStreet() == null) errorMessageEnd += "street can't be null.\n";
        else if(personAddress.getStreet().length() > streetMaxLength) errorMessageEnd += "street is too long.\n";

        if(personAddress.getAppartment().length() > appartmentMaxLength) errorMessageEnd += "appartment is too long.\n";

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
                "PersonAddress with this personId and addressType does not exist."); }
    }
}

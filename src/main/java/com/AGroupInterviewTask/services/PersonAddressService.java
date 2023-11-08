package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.repositories.PersonAddressRepository;
import com.AGroupInterviewTask.repositories.PersonRepository;
import com.AGroupInterviewTask.services.interfaces.IAddressTypesService;
import com.AGroupInterviewTask.services.interfaces.IPersonAddressService;
import com.AGroupInterviewTask.validators.DateValidator;
import com.AGroupInterviewTask.validators.PersonAddressValidator;
import com.AGroupInterviewTask.validators.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("personAddressService")
public class PersonAddressService implements IPersonAddressService {
    @Autowired
    private PersonAddressRepository personAddressRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private IAddressTypesService addressTypesService;

    //Method for inserting new PersonAddress into database.
    @Override
    public ResponseEntity save(PersonAddress personAddress) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Validate PersonAddress information.
            PersonAddressValidator.checkPersonAddressInput(personAddress);

            //Check if provided addressType is valid.
            addressTypesService.findOne(personAddress.getAddressType());

            //Check if Person with provided personId exists.
            PersonValidator.checkIfPersonExists(personAddress.getPersonId(), personRepository);

            //Check if PersonAddress with provided personId and addressType already exists.
            PersonAddressValidator.checkIfPersonAddressAlreadyExists(
                    personAddress.getPersonId(),
                    personAddress.getAddressType(),
                    personAddressRepository);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to insert new PersonAddress into database.
        try {
            personAddressRepository.save(personAddress);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("New PersonAddress successfully created.");
        }
        //Return error message in case something went wrong.
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    //Method for retrieving one PersonAddress with provided personId and addressType from database.
    @Override
    public ResponseEntity findOne(String asOfDate, Integer personId, String addressType) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Check if provided date is valid.
            DateValidator.checkDateFormat(asOfDate);

            //Check if provided addressType is valid.
            addressTypesService.findOne(addressType);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to find PersonAddress with provided personId and addressType.
        try {
            PersonAddress result = personAddressRepository.findOne(asOfDate, personId, addressType);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        }
        //Return error message in case nothing was found.
        catch (EmptyResultDataAccessException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("PersonAddress with this personId and addressType does not exist."); }
    }

    @Override
    public ResponseEntity findAll(String asOfDate) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Check if provided date is valid.
            DateValidator.checkDateFormat(asOfDate);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to find all PersonAddresses.
        List<PersonAddress> result = personAddressRepository.findAll(asOfDate);

        //Return error message in case nothing was found.
        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("No data found.");

        //Else return PersonAddresses.
        else return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @Override
    public ResponseEntity findAll(String asOfDate, Integer personId) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Check if provided date is valid.
            DateValidator.checkDateFormat(asOfDate);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to find all PersonAddresses with provided personId.
        List<PersonAddress> result = personAddressRepository.findAll(asOfDate, personId);

        //Return error message in case nothing was found.
        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("No data found.");

        //Else return PersonAddresses.
        else return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @Override
    public ResponseEntity findAll(String asOfDate, String addressType) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Check if provided date is valid.
            DateValidator.checkDateFormat(asOfDate);

            //Check if provided addressType is valid.
            addressTypesService.findOne(addressType);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to find all PersonAddresses with provided addressType.
        List<PersonAddress> result = personAddressRepository.findAll(asOfDate, addressType);

        //Return error message in case nothing was found.
        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("No data found.");

        //Else return PersonAddresses.
        else return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @Override
    public ResponseEntity update(PersonAddress personAddress, Integer personId, String addressType) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Validate PersonAddress information.
            PersonAddressValidator.checkPersonAddressInput(personAddress);

            //Check if provided addressType is valid.
            addressTypesService.findOne(addressType);

            //Check if PersonAddress with provided personId and addressType exists.
            PersonAddressValidator.checkIfPersonAddressExists(personId, addressType, personAddressRepository);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to update PersonAddress with new information.
        try {
            personAddressRepository.update(personAddress, personId, addressType);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("PersonAddress successfully updated.");
        }
        //Return error message in case something went wrong.
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @Override
    public ResponseEntity delete(Integer personId, String addressType) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Check if provided addressType is valid.
            addressTypesService.findOne(addressType);

            //Check if PersonAddress with provided personId and addressType exists.
            PersonAddressValidator.checkIfPersonAddressExists(personId, addressType, personAddressRepository);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to delete PersonAddress with provided personId and addressType.
        try {
            personAddressRepository.delete(personId, addressType);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("PersonAddress successfully deleted.");
        }
        //Return error message in case something went wrong.
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }
}

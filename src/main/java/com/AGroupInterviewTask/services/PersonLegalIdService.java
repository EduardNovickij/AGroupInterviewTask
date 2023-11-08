package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.entities.PersonLegalId;
import com.AGroupInterviewTask.repositories.PersonLegalIdRepository;
import com.AGroupInterviewTask.repositories.PersonRepository;
import com.AGroupInterviewTask.services.interfaces.IPersonLegalIdService;
import com.AGroupInterviewTask.validators.DateValidator;
import com.AGroupInterviewTask.validators.PersonLegalIdValidator;
import com.AGroupInterviewTask.validators.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("personLegalIdService")
public class PersonLegalIdService implements IPersonLegalIdService {

    @Autowired
    PersonLegalIdRepository personLegalIdRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LegalIdTypesService legalIdTypesService;

    //Method for inserting new PersonLegalId into database.
    @Override
    public ResponseEntity save(PersonLegalId personLegalId) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Validate PersonLegalId information.
            PersonLegalIdValidator.checkPersonLegalIdInput(personLegalId);

            //Check if provided idType is valid.
            legalIdTypesService.findOne(personLegalId.getIdType());

            //Check if Person with provided personId exists.
            PersonValidator.checkIfPersonExists(personLegalId.getPersonId(), personRepository);

            //Check if PersonLegalId with provided personId and idType already exists.
            PersonLegalIdValidator.checkIfPersonLegalIdAlreadyExists(
                    personLegalId.getPersonId(),
                    personLegalId.getIdType(),
                    personLegalIdRepository);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to insert new PersonLegalId into database.
        try {
            personLegalIdRepository.save(personLegalId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("New PersonLegalId successfully created.");
        }
        //Return error message in case something went wrong.
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @Override
    public ResponseEntity findOne(String asOfDate, Integer personId, String idType) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Check if provided date is valid.
            DateValidator.checkDateFormat(asOfDate);

            //Check if provided idType is valid.
            legalIdTypesService.findOne(idType);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to find PersonAddress with provided personId and idType.
        try {
            PersonLegalId result = personLegalIdRepository.findOne(asOfDate, personId, idType);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        }
        //Return error message in case nothing was found.
        catch (EmptyResultDataAccessException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("PersonLegalId with this personId and idType does not exist."); }
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

        //Try to find all PersonLegalIds.
        List<PersonLegalId> result = personLegalIdRepository.findAll(asOfDate);

        //Return error message in case nothing was found.
        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("No data found.");

        //Else return PersonLegalIds.
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

        //Try to find all PersonLegalIds with provided personId.
        List<PersonLegalId> result = personLegalIdRepository.findAll(asOfDate, personId);

        //Return error message in case nothing was found.
        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("No data found.");

        //Else return PersonLegalIds.
        else return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @Override
    public ResponseEntity findAll(String asOfDate, String idType) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Check if provided date is valid.
            DateValidator.checkDateFormat(asOfDate);

            //Check if provided idType is valid.
            legalIdTypesService.findOne(idType);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to find all PersonLegalIds with provided idType.
        List<PersonLegalId> result = personLegalIdRepository.findAll(asOfDate, idType);

        //Return error message in case nothing was found.
        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("No data found.");

        //Else return PersonLegalIds.
        else return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @Override
    public ResponseEntity update(PersonLegalId personLegalId, Integer personId, String idType) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Validate PersonLegalId information.
            PersonLegalIdValidator.checkPersonLegalIdInput(personLegalId);

            //Check if provided idType is valid.
            legalIdTypesService.findOne(idType);

            //Check if PersonLegalId with provided personId and idType exists.
            PersonLegalIdValidator.checkIfPersonLegalIdExists(personId, idType, personLegalIdRepository);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to update PersonLegalId with new information.
        try {
            personLegalIdRepository.update(personLegalId, personId, idType);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("PersonLegalId successfully updated.");
        }
        //Return error message in case something went wrong.
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @Override
    public ResponseEntity delete(Integer personId, String idType) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Check if provided idType is valid.
            legalIdTypesService.findOne(idType);

            //Check if PersonLegalId with provided personId and idType exists.
            PersonLegalIdValidator.checkIfPersonLegalIdExists(personId, idType, personLegalIdRepository);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to delete PersonAddress with provided personId and idType.
        try {
            personLegalIdRepository.delete(personId, idType);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("PersonLegalId successfully deleted.");
        }
        //Return error message in case something went wrong.
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }
}

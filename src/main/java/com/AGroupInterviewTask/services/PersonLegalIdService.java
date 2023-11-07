package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.entities.PersonLegalId;
import com.AGroupInterviewTask.repositories.PersonLegalIdRepository;
import com.AGroupInterviewTask.repositories.PersonRepository;
import com.AGroupInterviewTask.validators.DateValidator;
import com.AGroupInterviewTask.validators.PersonLegalIdValidator;
import com.AGroupInterviewTask.validators.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
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

    private final PersonLegalIdValidator personLegalIdValidator = new PersonLegalIdValidator();
    private final PersonValidator personValidator = new PersonValidator();
    private final DateValidator dateValidator = new DateValidator();

    @Override
    public ResponseEntity save(PersonLegalId personLegalId) {
        try {
            personLegalIdValidator.checkPersonLegalIdInput(personLegalId);
            legalIdTypesService.findOne(personLegalId.getIdType());
            personValidator.checkIfPersonExists(personLegalId.getPersonId(), personRepository);
            personLegalIdValidator.checkIfPersonLegalIdAlreadyExists(
                    personLegalId.getPersonId(),
                    personLegalId.getIdType(),
                    personLegalIdRepository);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            personLegalIdRepository.save(personLegalId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("New PersonLegalId successfully created.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @Override
    public ResponseEntity findOne(String asOfDate, Integer personId, String idType) {
        try {
            dateValidator.checkDateFormat(asOfDate);
            legalIdTypesService.findOne(idType);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            PersonLegalId result = personLegalIdRepository.findOne(asOfDate, personId, idType);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(result);
        }
        catch (EmptyResultDataAccessException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("PersonLegalId with this personId and idType does not exist."); }
    }

    @Override
    public ResponseEntity findAll(String asOfDate) {
        try {
            dateValidator.checkDateFormat(asOfDate);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        List<PersonLegalId> result = personLegalIdRepository.findAll(asOfDate);

        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No data found.");

        else return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public ResponseEntity findAll(String asOfDate, Integer personId) {
        try {
            dateValidator.checkDateFormat(asOfDate);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        List<PersonLegalId> result = personLegalIdRepository.findAll(asOfDate, personId);

        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No data found.");

        else return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public ResponseEntity findAll(String asOfDate, String idType) {
        try {
            dateValidator.checkDateFormat(asOfDate);
            legalIdTypesService.findOne(idType);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        List<PersonLegalId> result = personLegalIdRepository.findAll(asOfDate, idType);

        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No data found.");

        else return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public ResponseEntity update(PersonLegalId personLegalId, Integer personId, String idType) {
        try {
            personLegalIdValidator.checkPersonLegalIdInput(personLegalId);
            legalIdTypesService.findOne(idType);
            personLegalIdValidator.checkIfPersonLegalIdExists(personId, idType, personLegalIdRepository);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            personLegalIdRepository.update(personLegalId, personId, idType);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("PersonLegalId successfully updated.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @Override
    public ResponseEntity delete(Integer personId, String idType) {
        try {
            legalIdTypesService.findOne(idType);
            personLegalIdValidator.checkIfPersonLegalIdExists(personId, idType, personLegalIdRepository);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            personLegalIdRepository.delete(personId, idType);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("PersonLegalId successfully deleted.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }
}

package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.repositories.PersonAddressRepository;
import com.AGroupInterviewTask.repositories.PersonRepository;
import com.AGroupInterviewTask.validators.PersonAddressValidator;
import com.AGroupInterviewTask.validators.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
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

    private final PersonAddressValidator personAddressValidator = new PersonAddressValidator();
    private final PersonValidator personValidator = new PersonValidator();

    @Override
    public ResponseEntity save(PersonAddress personAddress) {
        try {
            personAddressValidator.checkPersonAddressInput(personAddress);
            addressTypesService.findOne(personAddress.getAddressType());
            personValidator.checkIfPersonExists(personAddress.getPersonId(), personRepository);
            personAddressValidator.checkIfPersonAddressAlreadyExists(
                    personAddress.getPersonId(),
                    personAddress.getAddressType(),
                    personAddressRepository);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            personAddressRepository.save(personAddress);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("New PersonAddress successfully created.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @Override
    public ResponseEntity findOne(String asOfDate, Integer personId, String addressType) {
        try {
            personAddressValidator.dateValidator(asOfDate);
            addressTypesService.findOne(addressType);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            PersonAddress result = personAddressRepository.findOne(asOfDate, personId, addressType);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(result);
        }
        catch (EmptyResultDataAccessException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("PersonAddress with this personId and addressType does not exist."); }
    }

    @Override
    public ResponseEntity findAll(String asOfDate, Integer personId) {
        try {
            personAddressValidator.dateValidator(asOfDate);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        List<PersonAddress> result = personAddressRepository.findAll(asOfDate, personId);

        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No data found.");

        else return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @Override
    public ResponseEntity update(PersonAddress personAddress, Integer personId, String addressType) {
        try {
            personAddressValidator.checkPersonAddressInput(personAddress);
            addressTypesService.findOne(addressType);
            personAddressValidator.checkIfPersonAddressExists(personId, addressType, personAddressRepository);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            personAddressRepository.update(personAddress, personId, addressType);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("PersonAddress successfully updated.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @Override
    public ResponseEntity delete(Integer personId, String addressType) {
        try {
            addressTypesService.findOne(addressType);
            personAddressValidator.checkIfPersonAddressExists(personId, addressType, personAddressRepository);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            personAddressRepository.delete(personId, addressType);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("PersonAddress successfully deleted.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }
}

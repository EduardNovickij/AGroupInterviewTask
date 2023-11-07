package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.dtos.PersonDTO;
import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.entities.PersonLegalId;
import com.AGroupInterviewTask.repositories.PersonAddressRepository;
import com.AGroupInterviewTask.repositories.PersonLegalIdRepository;
import com.AGroupInterviewTask.repositories.PersonRepository;
import com.AGroupInterviewTask.validators.DateValidator;
import com.AGroupInterviewTask.validators.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("personService")
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonAddressRepository personAddressRepository;

    @Autowired
    private PersonLegalIdRepository personLegalIdRepository;

    private final PersonValidator personValidator = new PersonValidator();
    private final DateValidator dateValidator = new DateValidator();

    @Override
    public ResponseEntity save(Person person) {
        try {
            personValidator.checkPersonInput(person);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            personRepository.save(person);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("New Person successfully created.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @Override
    public ResponseEntity findOne(String asOfDate, Integer personId) {
        try {
            dateValidator.checkDateFormat(asOfDate);
            personValidator.checkIfPersonExists(personId, personRepository);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            Person person = personRepository.findOne(asOfDate, personId);
            List<PersonAddress> personAddressList = personAddressRepository.findAll(asOfDate, personId);
            List<PersonLegalId> personLegalIdList = personLegalIdRepository.findAll(asOfDate, personId);

            PersonDTO result = new PersonDTO(person, personAddressList, personLegalIdList);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(result);
        } catch (EmptyResultDataAccessException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No data found.");
        }
    }

    @Override
    public ResponseEntity findAll(String asOfDate) {
        try {
            dateValidator.checkDateFormat(asOfDate);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        List<Person> personList = personRepository.findAll(asOfDate);

        if (personList.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No data found.");

        else {
            List<PersonDTO> result = new ArrayList<>();
            personList.forEach((person) -> {
                List<PersonAddress> personAddressList = personAddressRepository.findAll(asOfDate, person.getPersonId());
                List<PersonLegalId> personLegalIdList = personLegalIdRepository.findAll(asOfDate, person.getPersonId());
                result.add(new PersonDTO(person, personAddressList, personLegalIdList));
                    });

            return ResponseEntity.status(HttpStatus.OK)
                    .body(result);
        }
    }

    @Override
    public ResponseEntity update(Person person, Integer personId) {
        try {
            personValidator.checkPersonInput(person);
            personValidator.checkIfPersonExists(personId, personRepository);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            personRepository.update(person, personId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Person successfully updated.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @Override
    public ResponseEntity delete(Integer personId) {
        try {
            personValidator.checkIfPersonExists(personId, personRepository);
        }
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage()); }

        try {
            personRepository.delete(personId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Person successfully deleted.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }
}

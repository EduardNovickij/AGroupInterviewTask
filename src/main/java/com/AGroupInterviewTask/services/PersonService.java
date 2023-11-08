package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.dtos.DTOMapper;
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
import org.springframework.http.MediaType;
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

    @Override
    public ResponseEntity save(Person person) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Validate Person information.
            PersonValidator.checkPersonInput(person);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to insert new Person into database.
        try {
            personRepository.save(person);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("New Person successfully created.");
        }
        //Return error message in case something went wrong.
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @Override
    public ResponseEntity findOne(String asOfDate, Integer personId) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Check if provided date is valid.
            DateValidator.checkDateFormat(asOfDate);

            //Check if Person with provided personId exists.
            PersonValidator.checkIfPersonExists(personId, personRepository);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to find one Person with provided personId, as well as try to find all addresses and legal ids for that Person.
        try {
            Person person = personRepository.findOne(asOfDate, personId);
            List<PersonAddress> personAddressList = personAddressRepository.findAll(asOfDate, personId);
            List<PersonLegalId> personLegalIdList = personLegalIdRepository.findAll(asOfDate, personId);

            PersonDTO result = DTOMapper.mapToPersonDTO(person, personAddressList, personLegalIdList);

            //Return Person with provided personId, as well as their addresses and legal ids.
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        }

        //Return error message in case nothing was found.
        catch (EmptyResultDataAccessException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("No data found.");
        }
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

        //Try to find all Persons.
        List<Person> personList = personRepository.findAll(asOfDate);

        //Return error message in case nothing was found.
        if (personList.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("No data found.");

        //Else try to find all addresses and legal ids for every person.
        else {
            List<PersonDTO> result = new ArrayList<>();
            personList.forEach((person) -> {
                List<PersonAddress> personAddressList = personAddressRepository.findAll(asOfDate, person.getPersonId());
                List<PersonLegalId> personLegalIdList = personLegalIdRepository.findAll(asOfDate, person.getPersonId());
                result.add(DTOMapper.mapToPersonDTO(person, personAddressList, personLegalIdList));
                    });

            //Return all Persons with their addresses and legal ids.
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        }
    }

    @Override
    public ResponseEntity update(Person person, Integer personId) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Validate PersonAddress information.
            PersonValidator.checkPersonInput(person);

            //Check if Person with provided personId exists.
            PersonValidator.checkIfPersonExists(personId, personRepository);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to update Person with new information.
        try {
            personRepository.update(person, personId);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Person successfully updated.");
        }
        //Return error message in case something went wrong.
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @Override
    public ResponseEntity delete(Integer personId) {
        //Run validators on provided data and catch exception in case of validation failure.
        try {
            //Check if Person with provided personId exists.
            PersonValidator.checkIfPersonExists(personId, personRepository);
        }
        //Return ResponseEntity with error message in case of failed validation.
        catch (Exception exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage()); }

        //Try to delete Person with provided personId.
        try {
            personRepository.delete(personId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Person successfully deleted.");
        }
        //Return error message in case something went wrong.
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }
}

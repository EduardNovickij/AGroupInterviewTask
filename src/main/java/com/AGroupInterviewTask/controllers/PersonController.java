package com.AGroupInterviewTask.controllers;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    private static boolean isDateFormatValid(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);
            formatter.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @GetMapping("/getPerson")
    public ResponseEntity getAllPersons(@RequestParam(name = "asOfDate") String asOfDate,
                                        @RequestParam(name = "personId", required = false) Integer personId) {
        if(isDateFormatValid(asOfDate)) {
            if(personId == null) {
                List<Person> result = personService.findAll(asOfDate);

                if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No data found.");

                else return ResponseEntity.status(HttpStatus.OK)
                        .body(result);
            }

            else {
                try {
                    Person result = personService.findOne(asOfDate, personId);
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(result);
                }
                catch (EmptyResultDataAccessException exception){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("No data found.");
                }
            }
        }

        else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid date format for 'asOfDate'. Please use the format 'YYYY-MM-DD'");
    }

    @PostMapping("/insertPerson")
    public ResponseEntity getPersonById(@RequestBody Person person) {
        try {
            personService.save(person);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("New Person successfully created.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @DeleteMapping("/deletePerson")
    public ResponseEntity deletePersonById(@RequestParam(name = "personId") Integer personId) {
        try {
            personService.delete(personId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Person successfully deleted.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }

    @PostMapping("/updatePerson")
    public ResponseEntity createPerson(@RequestParam(name = "personId") Integer personId,
                                               @RequestBody Person person) {
        try {
            personService.update(person, personId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Person successfully updated.");
        }
        catch (DataAccessException exception) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Operation failed. Error message: " + exception.getMessage()); }
    }
}

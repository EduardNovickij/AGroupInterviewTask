package com.AGroupInterviewTask.controllers;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.services.interfaces.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controller for Person entity.

@RestController
public class PersonController {

    @Autowired
    private IPersonService personService;

    /*Method for getting data from Person entity.
     * Input parameters:
     * - asOfDate - required, must be in YYYY-MM-DD format.
     * - personId - optional.*/
    @GetMapping("/getPerson")
    public ResponseEntity getPerson(@RequestParam(name = "asOfDate") String asOfDate,
                                        @RequestParam(name = "personId", required = false) Integer personId) {
        if (personId == null) {
            return personService.findAll(asOfDate);
        } else {
            return personService.findOne(asOfDate, personId);
        }
    }

    /*Method for inserting new Person into Person entity.
     * Input body:
     * - Person information:
     *  - givenName - required.
     *  - familyName - required.
     *  - birthDate - required, must be in YYYY-MM-DD format.
     *  - gender - required.*/
    @PostMapping("/insertPerson")
    public ResponseEntity insertPerson(@RequestBody Person person) {
        return personService.save(person);
    }

    /*Method for deleting data from Person entity.
     * Input parameters:
     * - personId - required.*/
    @DeleteMapping("/deletePerson")
    public ResponseEntity deletePerson(@RequestParam(name = "personId") Integer personId) {
        return personService.delete(personId);
    }

    /*Method for updating data inside Person entity.
     * Input parameters:
     * - personId - required, must be in YYYY-MM-DD format.
     *
     * Input body:
     * - Person information:
     *  - givenName - required.
     *  - familyName - required.
     *  - birthDate - required, must be in YYYY-MM-DD format.
     *  - gender - required.*/
    @PostMapping("/updatePerson")
    public ResponseEntity updatePerson(@RequestParam(name = "personId") Integer personId,
                                               @RequestBody Person person) {
        return personService.update(person, personId);
    }

    //Method for retrieving a list with snapshot dates.
    @GetMapping("/getSnapshotList")
    public ResponseEntity getSnaphotList() {
        return personService.getSnapshotList();
    }
}

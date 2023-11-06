package com.AGroupInterviewTask.controllers;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private IPersonService personService;

    @GetMapping("/getPerson")
    public ResponseEntity getPerson(@RequestParam(name = "asOfDate") String asOfDate,
                                        @RequestParam(name = "personId", required = false) Integer personId) {
        if (personId == null) {
            return personService.findAll(asOfDate);
        } else {
            return personService.findOne(asOfDate, personId);
        }
    }


    @PostMapping("/insertPerson")
    public ResponseEntity insertPerson(@RequestBody Person person) {
        return personService.save(person);
    }

    @DeleteMapping("/deletePerson")
    public ResponseEntity deletePerson(@RequestParam(name = "personId") Integer personId) {
        return personService.delete(personId);
    }

    @PostMapping("/updatePerson")
    public ResponseEntity updatePerson(@RequestParam(name = "personId") Integer personId,
                                               @RequestBody Person person) {
        return personService.update(person, personId);
    }
}

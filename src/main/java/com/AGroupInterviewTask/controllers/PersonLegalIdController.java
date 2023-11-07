package com.AGroupInterviewTask.controllers;

import com.AGroupInterviewTask.entities.PersonLegalId;
import com.AGroupInterviewTask.services.IPersonLegalIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonLegalIdController {

    @Autowired
    private IPersonLegalIdService personLegalIdService;

    @GetMapping("/getPersonLegalId")
    public ResponseEntity getPersonLegalId(@RequestParam(name = "asOfDate") String asOfDate,
                                           @RequestParam(name = "personId", required = false) Integer personId,
                                           @RequestParam(name = "idType", required = false) String idType) {
        if (personId == null && idType == null) { return personLegalIdService.findAll(asOfDate); }
        else if (personId == null) { return personLegalIdService.findAll(asOfDate, idType); }
        else if (idType == null) { return personLegalIdService.findAll(asOfDate, personId); }
        else return personLegalIdService.findOne(asOfDate, personId, idType);
    }


    @PostMapping("/insertPersonLegalId")
    public ResponseEntity insertPersonLegalId(@RequestBody PersonLegalId personLegalId) {
        return personLegalIdService.save(personLegalId);
    }

    @DeleteMapping("/deletePersonLegalId")
    public ResponseEntity deletePersonLegalId(@RequestParam(name = "personId") Integer personId,
                                              @RequestParam(name = "idType") String idType) {
        return personLegalIdService.delete(personId, idType);
    }

    @PostMapping("/updatePersonLegalId")
    public ResponseEntity updatePersonLegalId(@RequestParam(name = "personId") Integer personId,
                                              @RequestParam(name = "idType") String idType,
                                              @RequestBody PersonLegalId personLegalId) {
        return personLegalIdService.update(personLegalId, personId, idType);
    }

}

package com.AGroupInterviewTask.controllers;

import com.AGroupInterviewTask.entities.PersonLegalId;
import com.AGroupInterviewTask.services.IPersonLegalIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controller for PersonLegalId entity.

@RestController
public class PersonLegalIdController {

    @Autowired
    private IPersonLegalIdService personLegalIdService;

    /*Method for getting data from PersonLegalId entity.
     * Input parameters:
     * - asOfDate - required, must be in YYYY-MM-DD format.
     * - personId - optional.
     * - idType - optional.*/
    @GetMapping("/getPersonLegalId")
    public ResponseEntity getPersonLegalId(@RequestParam(name = "asOfDate") String asOfDate,
                                           @RequestParam(name = "personId", required = false) Integer personId,
                                           @RequestParam(name = "idType", required = false) String idType) {
        if (personId == null && idType == null) { return personLegalIdService.findAll(asOfDate); }
        else if (personId == null) { return personLegalIdService.findAll(asOfDate, idType); }
        else if (idType == null) { return personLegalIdService.findAll(asOfDate, personId); }
        else return personLegalIdService.findOne(asOfDate, personId, idType);
    }

    /*Method for inserting new PersonLegalId into PersonLegalId entity.
     * Input body:
     * - PersonLegalId information:
     *  - personId - required.
     *  - idType - required.
     *  - idNumber - required.
     *  - issueDate - required, must be in YYYY-MM-DD format.
     *  - issuedBy - optional.*/
    @PostMapping("/insertPersonLegalId")
    public ResponseEntity insertPersonLegalId(@RequestBody PersonLegalId personLegalId) {
        return personLegalIdService.save(personLegalId);
    }

    /*Method for deleting data from PersonLegalId entity.
     * Input parameters:
     * - personId - required.
     * - idType - required.*/
    @DeleteMapping("/deletePersonLegalId")
    public ResponseEntity deletePersonLegalId(@RequestParam(name = "personId") Integer personId,
                                              @RequestParam(name = "idType") String idType) {
        return personLegalIdService.delete(personId, idType);
    }

    /*Method for updating data inside PersonLegalId entity.
     * Input parameters:
     * - personId - required.
     * - idType - required.
     *
     * Input body:
     * - PersonLegalId information:
     *  - idNumber - required.
     *  - issueDate - required, must be in YYYY-MM-DD format.
     *  - issuedBy - optional.*/
    @PostMapping("/updatePersonLegalId")
    public ResponseEntity updatePersonLegalId(@RequestParam(name = "personId") Integer personId,
                                              @RequestParam(name = "idType") String idType,
                                              @RequestBody PersonLegalId personLegalId) {
        return personLegalIdService.update(personLegalId, personId, idType);
    }

}

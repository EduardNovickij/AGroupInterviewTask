package com.AGroupInterviewTask.controllers;

import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.services.IPersonAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controller for PersonAddress entity.

@RestController
public class PersonAddressController {

    @Autowired
    private IPersonAddressService personAddressService;

    /*Method for getting data from PersonAddress entity.
    * Input parameters:
    * - asOfDate - required, must be in YYYY-MM-DD format.
    * - personId - optional.
    * - addressType - optional.*/
    @GetMapping("/getPersonAddress")
    public ResponseEntity getPersonAddress(@RequestParam(name = "asOfDate") String asOfDate,
                                    @RequestParam(name = "personId", required = false) Integer personId,
                                    @RequestParam(name = "addressType", required = false) String addressType) {
        if (personId == null && addressType == null) { return personAddressService.findAll(asOfDate); }
        else if (personId == null) { return personAddressService.findAll(asOfDate, addressType); }
        else if (addressType == null) { return personAddressService.findAll(asOfDate, personId); }
        else return personAddressService.findOne(asOfDate, personId, addressType);
    }

    /*Method for inserting new PersonAddress into PersonAddress entity.
     * Input body:
     * - PersonAddress information:
     *  - personId - required.
     *  - addressType - required.
     *  - city - required.
     *  - street - required.
     *  - appartment - optional.*/
    @PostMapping("/insertPersonAddress")
    public ResponseEntity insertPersonAddress(@RequestBody PersonAddress personAddress) {
        return personAddressService.save(personAddress);
    }

    /*Method for deleting data from PersonAddress entity.
     * Input parameters:
     * - personId - required.
     * - addressType - required.*/
    @DeleteMapping("/deletePersonAddress")
    public ResponseEntity deletePersonAddress(@RequestParam(name = "personId") Integer personId,
                                              @RequestParam(name = "addressType") String addressType) {
        return personAddressService.delete(personId, addressType);
    }

    /*Method for updating data inside PersonAddress entity.
     * Input parameters:
     * - personId - required.
     * - addressType - required.
     *
     * Input body:
     * - PersonAddress information:
     *  - city - required.
     *  - street - required.
     *  - appartment - optional.*/
    @PostMapping("/updatePersonAddress")
    public ResponseEntity updatePersonAddress(@RequestParam(name = "personId") Integer personId,
                                       @RequestParam(name = "addressType") String addressType,
                                       @RequestBody PersonAddress personAddress) {
        return personAddressService.update(personAddress, personId, addressType);
    }

}

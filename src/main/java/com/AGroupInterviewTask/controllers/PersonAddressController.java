package com.AGroupInterviewTask.controllers;

import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.services.IPersonAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonAddressController {

    @Autowired
    private IPersonAddressService personAddressService;

    @GetMapping("/getPersonAddress")
    public ResponseEntity getPersonAddress(@RequestParam(name = "asOfDate") String asOfDate,
                                    @RequestParam(name = "personId") Integer personId,
                                    @RequestParam(name = "addressType", required = false) String addressType) {
        if (addressType == null) {
            return personAddressService.findAll(asOfDate, personId);
        } else {
            return personAddressService.findOne(asOfDate, personId, addressType);
        }
    }


    @PostMapping("/insertPersonAddress")
    public ResponseEntity insertPersonAddress(@RequestBody PersonAddress personAddress) {
        return personAddressService.save(personAddress);
    }

    @DeleteMapping("/deletePersonAddress")
    public ResponseEntity deletePersonAddress(@RequestParam(name = "personId") Integer personId,
                                              @RequestParam(name = "addressType") String addressType) {
        return personAddressService.delete(personId, addressType);
    }

    @PostMapping("/updatePersonAddress")
    public ResponseEntity updatePersonAddress(@RequestParam(name = "personId") Integer personId,
                                       @RequestParam(name = "addressType") String addressType,
                                       @RequestBody PersonAddress personAddress) {
        return personAddressService.update(personAddress, personId, addressType);
    }

}

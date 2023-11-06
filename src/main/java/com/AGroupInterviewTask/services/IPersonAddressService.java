package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.entities.PersonAddress;
import org.springframework.http.ResponseEntity;

public interface IPersonAddressService {

    ResponseEntity save(PersonAddress personAddress);

    ResponseEntity findOne(String asOfDate, Integer personId, String addressType);

    ResponseEntity findAll(String asOfDate, Integer personId);

    ResponseEntity update(PersonAddress personAddress, Integer personId, String addressType);

    ResponseEntity delete(Integer personId, String addressType);

}

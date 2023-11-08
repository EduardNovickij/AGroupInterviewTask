package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.entities.Person;
import org.springframework.http.ResponseEntity;

//Interface for PersonService.
public interface IPersonService {

    ResponseEntity save(Person person);

    ResponseEntity findOne(String asOfDate , Integer personid);

    ResponseEntity findAll(String asOfDate);

    ResponseEntity update(Person person, Integer personId);

    ResponseEntity delete(Integer personId);
}

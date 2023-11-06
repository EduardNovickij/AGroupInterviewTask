package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.entities.Person;

import java.util.List;

public interface PersonService {

    void save(Person person);

    Person findOne(String asOfDate , Integer id);

    List<Person> findAll(String asOfDate);

    void update(Person person, Integer personId);

    void delete(Integer id);
}

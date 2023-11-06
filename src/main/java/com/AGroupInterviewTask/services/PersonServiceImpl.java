package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("personService")
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public Person findOne(String asOfDate, Integer id) {
        return personRepository.findOne(asOfDate, id);
    }

    @Override
    public List<Person> findAll(String asOfDate) {
        return personRepository.findAll(asOfDate);
    }

    @Override
    public void update(Person person, Integer personId) {
        personRepository.update(person, personId);
    }

    @Override
    public void delete(Integer id) {
        personRepository.delete(id);
    }
}

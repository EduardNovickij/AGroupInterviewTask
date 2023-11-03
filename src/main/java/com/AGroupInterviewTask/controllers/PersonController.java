package com.AGroupInterviewTask.controllers;

import com.AGroupInterviewTask.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Types;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/getAllPersons")
    public List<Person> getAllPersons() {
        List<Person> personList;
        personList = jdbcTemplate.query("SELECT * FROM Person",
                (resultSet, rowNum) -> new Person(resultSet));

        return personList;
    }

    @GetMapping("/getPersonById")
    public Person getPersonById(@RequestParam(name = "personId") int personId) {
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE personId = ?",
                new Object[]{personId},
                (resultSet, rowNum) -> new Person(resultSet));
    }

    @DeleteMapping("/deletePersonById")
    public ResponseEntity<Person> deletePersonById(@RequestParam(name = "personId") int personId) {
        Person deletedPerson = jdbcTemplate.queryForObject("SELECT * FROM Person WHERE personId = ?",
                new Object[]{personId},
                (resultSet, rowNum) -> new Person(resultSet));

        jdbcTemplate.update("DELETE FROM Person WHERE personId = ?", personId);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(deletedPerson);
    }

    @PostMapping("/createPerson")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {

        System.out.println(person);

        jdbcTemplate.update("INSERT INTO Person (givenName, familyName, birthDate, gender) VALUES (?, ?, date(?), ?)",
                person.getGivenName(), person.getFamilyName(), person.getBirthDate(), person.getGender());

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(person);
    }

    @PutMapping("/updatePerson")
    public ResponseEntity<Person> updatePerson(@RequestParam(name = "personId") int personId, @RequestBody Person person) {
        jdbcTemplate.update("UPDATE Person SET givenName = ?, familyName = ?, birthDate = ?, gender = ? " +
                        "WHERE personId = ?",
                person.getGivenName(), person.getFamilyName(), person.getBirthDate(), person.getGender(), personId);

        person = jdbcTemplate.queryForObject("SELECT * FROM Person WHERE personId = ?",
                new Object[]{personId},
                (resultSet, rowNum) -> new Person(resultSet));

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(person);
    }
}

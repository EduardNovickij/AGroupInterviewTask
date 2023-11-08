package com.AGroupInterviewTask.repositories;

import com.AGroupInterviewTask.entities.Person;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//Class for retrieving Person information from SQLite database.

@Repository("personRepository")
public class PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Method for retrieving Person information from resulting set and creating new Person object.
    private Person mapRowToPerson(ResultSet resultSet, int rowNum) throws SQLException {
        return new Person(
                resultSet.getInt("personId"),
                resultSet.getString("givenName"),
                resultSet.getString("familyName"),
                resultSet.getString("birthDate"),
                resultSet.getString("gender")
        );
    }

    //Method for retrieving timestamps from resulting set.
    private String mapRowToString(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getString("timestamp");
    }

    //Method for inserting new Person instance into Person entity.
    public void save(Person person) throws DataAccessException {
        String sqlQuery = "INSERT INTO Person(personId, givenName, familyName, birthDate, gender) " +
                "values (0, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                person.getGivenName(),
                person.getFamilyName(),
                person.getBirthDate(),
                person.getGender());
    }

    /*Method for finding one instance of Person with provided personId inside Person entity.
     * Throws exception if nothing was found.*/
    public Person findOne(String asOfDate , Integer personId) throws EmptyResultDataAccessException {
        String sqlQuery = "SELECT personId, givenName, familyName, birthDate, gender " +
                "FROM Person WHERE personId = ? AND timestamp ";

        //Check if provided date is equal to today.
        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            //If is equal to today then retrieve from current version of Person entity.
            sqlQuery += "IS NULL";
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPerson, personId);
        }
        else {
            //Else search for a snapshot of Person entity with provided date (if it exists).
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPerson, personId, asOfDate);
        }
    }

    //Method for finding all instances of Person inside Person entity.
    public List<Person> findAll(String asOfDate) {
        String sqlQuery = "SELECT personId, givenName, familyName, birthDate, gender " +
                "FROM Person WHERE timestamp ";

        //Check if provided date is equal to today.
        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            //If is equal to today then retrieve from current version of Person entity.
            sqlQuery += "IS NULL";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPerson);
        }
        else {
            //Else search for a snapshot of Person entity with provided date (if it exists).
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPerson, asOfDate);
        }
    }

    //Method for updating existing Person with new information using provided personId.
    public void update(Person person, Integer personId) throws DataAccessException {
        String sqlQuery = "UPDATE Person SET " +
                "givenName = ?, familyName = ?, birthDate = ?, gender = ? " +
                "WHERE personId = ? AND timestamp IS NULL";
        jdbcTemplate.update(sqlQuery,
                person.getGivenName(),
                person.getFamilyName(),
                person.getBirthDate(),
                person.getGender(),
                personId);
    }

    //Method for deleting existing Person with provided personId.
    public void delete(Integer personId) throws DataAccessException {
        String sqlQuery = "DELETE FROM Person WHERE personId = ? AND timestamp IS NULL";
        jdbcTemplate.update(sqlQuery, personId);
    }

    //Method for retrieving snapshot dates
    public List<String> getSnapshotList() throws EmptyResultDataAccessException {
        String sqlQuery = "SELECT DISTINCT timestamp FROM Person WHERE timestamp IS NOT NULL";

        return jdbcTemplate.query(sqlQuery, this::mapRowToString);
    }
}

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

@Repository("personRepository")
public class PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Person mapRowToPerson(ResultSet resultSet, int rowNum) throws SQLException {
        return new Person(
                resultSet.getInt("personId"),
                resultSet.getString("givenName"),
                resultSet.getString("familyName"),
                resultSet.getString("birthDate"),
                resultSet.getString("gender")
        );
    }

    public void save(Person person) throws DataAccessException {
        String sqlQuery = "INSERT INTO Person(personId, givenName, familyName, birthDate, gender) " +
                "values (0, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                person.getGivenName(),
                person.getFamilyName(),
                person.getBirthDate(),
                person.getGender());
    }

    public Person findOne(String asOfDate , Integer personId) throws EmptyResultDataAccessException {
        String sqlQuery = "SELECT personId, givenName, familyName, birthDate, gender " +
                "FROM Person WHERE personId = ? AND timestamp ";

        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            sqlQuery += "IS NULL";
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPerson, personId);
        }
        else {
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPerson, personId, asOfDate);
        }
    }

    public List<Person> findAll(String asOfDate) {
        String sqlQuery = "SELECT personId, givenName, familyName, birthDate, gender " +
                "FROM Person WHERE timestamp ";

        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            sqlQuery += "IS NULL";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPerson);
        }
        else {
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPerson, asOfDate);
        }
    }

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

    public void delete(Integer personId) throws DataAccessException {
        String sqlQuery = "DELETE FROM Person WHERE personId = ? AND timestamp IS NULL";
        jdbcTemplate.update(sqlQuery, personId);
    }
}

package com.AGroupInterviewTask.repositories;

import com.AGroupInterviewTask.entities.PersonAddress;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//Class for retrieving PersonAddress information from SQLite database.

@Repository("personAddressRepository")
public class PersonAddressRepository {

    private final JdbcTemplate jdbcTemplate;

    public PersonAddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Method for retrieving PersonAddress information from resulting set and creating new PersonAddress object.
    private PersonAddress mapRowToPersonAddress(ResultSet resultSet, int rowNum) throws SQLException {
        return new PersonAddress(
                resultSet.getString("addressType"),
                resultSet.getInt("personId"),
                resultSet.getString("city"),
                resultSet.getString("street"),
                resultSet.getString("appartment")
        );
    }

    //Method for inserting new PersonAddress instance into PersonAddress entity.
    public void save(PersonAddress personAddress) throws DataAccessException {
        String sqlQuery = "INSERT INTO PersonAddress(addressType, personId, city, street, appartment) " +
                "values ((SELECT id FROM AddressTypes WHERE addressType = ?), ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                personAddress.getAddressType(),
                personAddress.getPersonId(),
                personAddress.getCity(),
                personAddress.getStreet(),
                personAddress.getAppartment());
    }

    /*Method for finding one instance of PersonAddress with provided personId and addressType inside PersonAddress entity.
    * Throws exception if nothing was found.*/
    public PersonAddress findOne(String asOfDate , Integer personId, String addressType) throws EmptyResultDataAccessException {
        String sqlQuery = "SELECT addressType, personId, city, street, appartment " +
                "FROM (SELECT AddressTypes.addressType, PersonAddress.personId, PersonAddress.city, PersonAddress.street, PersonAddress.appartment, PersonAddress.timestamp " +
                "FROM PersonAddress INNER JOIN AddressTypes ON PersonAddress.addressType=AddressTypes.id) " +
                "WHERE addressType = ? AND personId = ? AND timestamp ";

        //Check if provided date is equal to today.
        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            //If is equal to today then retrieve from current version of PersonAddress entity.
            sqlQuery += "IS NULL";
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPersonAddress, addressType, personId);
        }
        else {
            //Else search for a snapshot of PersonAddress entity with provided date (if it exists).
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPersonAddress, addressType, personId, asOfDate);
        }
    }

    //Method for finding all instances of PersonAddress inside PersonAddress entity.
    public List<PersonAddress> findAll(String asOfDate) {
        String sqlQuery = "SELECT addressType, personId, city, street, appartment " +
                "FROM (SELECT AddressTypes.addressType, PersonAddress.personId, PersonAddress.city, PersonAddress.street, PersonAddress.appartment, PersonAddress.timestamp " +
                "FROM PersonAddress INNER JOIN AddressTypes ON PersonAddress.addressType=AddressTypes.id) " +
                "WHERE timestamp ";

        //Check if provided date is equal to today.
        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            //If is equal to today then retrieve from current version of PersonAddress entity.
            sqlQuery += "IS NULL";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress);
        }
        else {
            //Else search for a snapshot of PersonAddress entity with provided date (if it exists).
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress, asOfDate);
        }
    }

    //Method for finding all instances of PersonAddress with provided personId inside PersonAddress entity.
    public List<PersonAddress> findAll(String asOfDate, Integer personId) {
        String sqlQuery = "SELECT addressType, personId, city, street, appartment " +
                "FROM (SELECT AddressTypes.addressType, PersonAddress.personId, PersonAddress.city, PersonAddress.street, PersonAddress.appartment, PersonAddress.timestamp " +
                "FROM PersonAddress INNER JOIN AddressTypes ON PersonAddress.addressType=AddressTypes.id) " +
                "WHERE personId = ? AND timestamp ";

        //Check if provided date is equal to today.
        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            //If is equal to today then retrieve from current version of PersonAddress entity.
            sqlQuery += "IS NULL";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress, personId);
        }
        else {
            //Else search for a snapshot of PersonAddress entity with provided date (if it exists).
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress, personId, asOfDate);
        }
    }

    //Method for finding all instances of PersonAddress with provided addressType inside PersonAddress entity.
    public List<PersonAddress> findAll(String asOfDate, String addressType) {
        String sqlQuery = "SELECT addressType, personId, city, street, appartment " +
                "FROM (SELECT AddressTypes.addressType, PersonAddress.personId, PersonAddress.city, PersonAddress.street, PersonAddress.appartment, PersonAddress.timestamp " +
                "FROM PersonAddress INNER JOIN AddressTypes ON PersonAddress.addressType=AddressTypes.id) " +
                "WHERE addressType = ? AND timestamp ";

        //Check if provided date is equal to today
        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            //If is equal to today then retrieve from current version of PersonAddress entity.
            sqlQuery += "IS NULL";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress, addressType);
        }
        else {
            //Else search for a snapshot of PersonAddress entity with provided date (if it exists).
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress, addressType, asOfDate);
        }
    }

    //Method for updating existing PersonAddress with new information using provided personId and addressType.
    public void update(PersonAddress personAddress, Integer personId, String addressType) throws DataAccessException {
        String sqlQuery = "UPDATE PersonAddress SET " +
                "city = ?, street = ?, appartment = ? " +
                "WHERE personId = ? AND addressType = (SELECT id FROM AddressTypes WHERE addressType = ?) AND timestamp IS NULL";
        jdbcTemplate.update(sqlQuery,
                personAddress.getCity(),
                personAddress.getStreet(),
                personAddress.getAppartment(),
                personId,
                addressType);
    }

    //Method for deleting existing PersonAddress with provided personId and addressType.
    public void delete(Integer personId, String addressType) throws DataAccessException {
        String sqlQuery = "DELETE FROM PersonAddress " +
                "WHERE personId = ? AND addressType = (SELECT id FROM AddressTypes WHERE addressType = ?) AND timestamp IS NULL";
        jdbcTemplate.update(sqlQuery, personId, addressType);
    }

}

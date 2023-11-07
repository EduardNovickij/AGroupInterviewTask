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

@Repository("personAddressRepository")
public class PersonAddressRepository {

    private final JdbcTemplate jdbcTemplate;

    public PersonAddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private PersonAddress mapRowToPersonAddress(ResultSet resultSet, int rowNum) throws SQLException {
        return new PersonAddress(
                resultSet.getString("addressType"),
                resultSet.getInt("personId"),
                resultSet.getString("city"),
                resultSet.getString("street"),
                resultSet.getString("appartment")
        );
    }

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

    public PersonAddress findOne(String asOfDate , Integer personId, String addressType) throws EmptyResultDataAccessException {
        String sqlQuery = "SELECT addressType, personId, city, street, appartment " +
                "FROM (SELECT AddressTypes.addressType, PersonAddress.personId, PersonAddress.city, PersonAddress.street, PersonAddress.appartment, PersonAddress.timestamp " +
                "FROM PersonAddress INNER JOIN AddressTypes ON PersonAddress.addressType=AddressTypes.id) " +
                "WHERE addressType = ? AND personId = ? AND timestamp ";

        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            sqlQuery += "IS NULL";
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPersonAddress, addressType, personId);
        }
        else {
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPersonAddress, addressType, personId, asOfDate);
        }
    }

    public List<PersonAddress> findAll(String asOfDate) {
        String sqlQuery = "SELECT addressType, personId, city, street, appartment " +
                "FROM (SELECT AddressTypes.addressType, PersonAddress.personId, PersonAddress.city, PersonAddress.street, PersonAddress.appartment, PersonAddress.timestamp " +
                "FROM PersonAddress INNER JOIN AddressTypes ON PersonAddress.addressType=AddressTypes.id) " +
                "WHERE timestamp ";

        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            sqlQuery += "IS NULL";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress);
        }
        else {
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress, asOfDate);
        }
    }

    public List<PersonAddress> findAll(String asOfDate, Integer personId) {
        String sqlQuery = "SELECT addressType, personId, city, street, appartment " +
                "FROM (SELECT AddressTypes.addressType, PersonAddress.personId, PersonAddress.city, PersonAddress.street, PersonAddress.appartment, PersonAddress.timestamp " +
                "FROM PersonAddress INNER JOIN AddressTypes ON PersonAddress.addressType=AddressTypes.id) " +
                "WHERE personId = ? AND timestamp ";

        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            sqlQuery += "IS NULL";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress, personId);
        }
        else {
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress, personId, asOfDate);
        }
    }

    public List<PersonAddress> findAll(String asOfDate, String addressType) {
        String sqlQuery = "SELECT addressType, personId, city, street, appartment " +
                "FROM (SELECT AddressTypes.addressType, PersonAddress.personId, PersonAddress.city, PersonAddress.street, PersonAddress.appartment, PersonAddress.timestamp " +
                "FROM PersonAddress INNER JOIN AddressTypes ON PersonAddress.addressType=AddressTypes.id) " +
                "WHERE addressType = ? AND timestamp ";

        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            sqlQuery += "IS NULL";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress, addressType);
        }
        else {
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonAddress, addressType, asOfDate);
        }
    }

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

    public void delete(Integer personId, String addressType) throws DataAccessException {
        String sqlQuery = "DELETE FROM PersonAddress " +
                "WHERE personId = ? AND addressType = (SELECT id FROM AddressTypes WHERE addressType = ?) AND timestamp IS NULL";
        jdbcTemplate.update(sqlQuery, personId, addressType);
    }

}

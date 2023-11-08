package com.AGroupInterviewTask.repositories;

import com.AGroupInterviewTask.entities.PersonLegalId;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//Class for retrieving PersonLegalId information from SQLite database.

@Repository("personLegalIdRepository")
public class PersonLegalIdRepository {

    private final JdbcTemplate jdbcTemplate;

    public PersonLegalIdRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Method for retrieving PersonLegalId information from resulting set and creating new PersonLegalId object.
    private PersonLegalId mapRowToPersonLegalId(ResultSet resultSet, int rowNum) throws SQLException {
        return new PersonLegalId(
                resultSet.getString("idType"),
                resultSet.getInt("personId"),
                resultSet.getInt("idNumber"),
                resultSet.getString("issueDate"),
                resultSet.getString("issuedBy")
        );
    }

    //Method for inserting new PersonLegalId instance into PersonLegalId entity.
    public void save(PersonLegalId personLegalId) throws DataAccessException {
        String sqlQuery = "INSERT INTO PersonLegalId(idType, personId, idNumber, issueDate, issuedBy) " +
                "values ((SELECT id FROM LegalIdTypes WHERE legalIdType = ?), ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                personLegalId.getIdType(),
                personLegalId.getPersonId(),
                personLegalId.getIdNumber(),
                personLegalId.getIssueDate(),
                personLegalId.getIssuedBy());
    }

    /*Method for finding one instance of PersonLegalId with provided personId and idType inside PersonLegalId entity.
     * Throws exception if nothing was found.*/
    public PersonLegalId findOne(String asOfDate , Integer personId, String idType) throws EmptyResultDataAccessException {
        String sqlQuery = "SELECT idType, personId, idNumber, issueDate, issuedBy " +
                "FROM (SELECT LegalIdTypes.legalIdType AS idType, PersonLegalId.personId, PersonLegalId.idNumber, PersonLegalId.issueDate, PersonLegalId.issuedBy, PersonLegalId.timestamp " +
                "FROM PersonLegalId INNER JOIN LegalIdTypes ON PersonLegalId.idType=LegalIdTypes.id) " +
                "WHERE idType = ? AND personId = ? AND timestamp ";

        //Check if provided date is equal to today.
        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            //If is equal to today then retrieve from current version of PersonLegalId entity.
            sqlQuery += "IS NULL";
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPersonLegalId, idType, personId);
        }
        else {
            //Else search for a snapshot of PersonLegalId entity with provided date (if it exists).
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPersonLegalId, idType, personId, asOfDate);
        }
    }

    //Method for finding all instances of PersonLegalId inside PersonLegalId entity.
    public List<PersonLegalId> findAll(String asOfDate) {
        String sqlQuery = "SELECT idType, personId, idNumber, issueDate, issuedBy " +
                "FROM (SELECT LegalIdTypes.legalIdType AS idType, PersonLegalId.personId, PersonLegalId.idNumber, PersonLegalId.issueDate, PersonLegalId.issuedBy, PersonLegalId.timestamp " +
                "FROM PersonLegalId INNER JOIN LegalIdTypes ON PersonLegalId.idType=LegalIdTypes.id) " +
                "WHERE timestamp ";

        //Check if provided date is equal to today.
        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            //If is equal to today then retrieve from current version of PersonLegalId entity.
            sqlQuery += "IS NULL";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonLegalId);
        }
        else {
            //Else search for a snapshot of PersonLegalId entity with provided date (if it exists).
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonLegalId, asOfDate);
        }
    }

    //Method for finding all instances of PersonLegalId with provided personId inside PersonLegalId entity.
    public List<PersonLegalId> findAll(String asOfDate, Integer personId) {
        String sqlQuery = "SELECT idType, personId, idNumber, issueDate, issuedBy " +
                "FROM (SELECT LegalIdTypes.legalIdType AS idType, PersonLegalId.personId, PersonLegalId.idNumber, PersonLegalId.issueDate, PersonLegalId.issuedBy, PersonLegalId.timestamp " +
                "FROM PersonLegalId INNER JOIN LegalIdTypes ON PersonLegalId.idType=LegalIdTypes.id) " +
                "WHERE personId = ? AND timestamp ";

        //Check if provided date is equal to today.
        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            //If is equal to today then retrieve from current version of PersonLegalId entity.
            sqlQuery += "IS NULL";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonLegalId, personId);
        }
        else {
            //Else search for a snapshot of PersonLegalId entity with provided date (if it exists).
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonLegalId, personId, asOfDate);
        }
    }

    //Method for finding all instances of PersonLegalId with provided idType inside PersonLegalId entity.
    public List<PersonLegalId> findAll(String asOfDate, String idType) {
        String sqlQuery = "SELECT idType, personId, idNumber, issueDate, issuedBy " +
                "FROM (SELECT LegalIdTypes.legalIdType AS idType, PersonLegalId.personId, PersonLegalId.idNumber, PersonLegalId.issueDate, PersonLegalId.issuedBy, PersonLegalId.timestamp " +
                "FROM PersonLegalId INNER JOIN LegalIdTypes ON PersonLegalId.idType=LegalIdTypes.id) " +
                "WHERE idType = ? AND timestamp ";

        //Check if provided date is equal to today.
        long millis = System.currentTimeMillis();
        if(asOfDate.equals(new Date(millis).toString())) {
            //If is equal to today then retrieve from current version of PersonLegalId entity.
            sqlQuery += "IS NULL";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonLegalId, idType);
        }
        else {
            //Else search for a snapshot of PersonLegalId entity with provided date (if it exists).
            sqlQuery += "= DATE(?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToPersonLegalId, idType, asOfDate);
        }
    }

    //Method for updating existing PersonLegalId with new information using provided personId and idType.
    public void update(PersonLegalId personLegalId, Integer personId, String idType) throws DataAccessException {
        String sqlQuery = "UPDATE PersonLegalId SET " +
                "idNumber = ?, issueDate = ?, issuedBy = ? " +
                "WHERE personId = ? AND idType = (SELECT id FROM LegalIdTypes WHERE legalIdType = ?) AND timestamp IS NULL";
        jdbcTemplate.update(sqlQuery,
                personLegalId.getIdNumber(),
                personLegalId.getIssueDate(),
                personLegalId.getIssuedBy(),
                personId,
                idType);
    }

    //Method for deleting existing PersonLegalId with provided personId and idType.
    public void delete(Integer personId, String idType) throws DataAccessException {
        String sqlQuery = "DELETE FROM PersonLegalId " +
                "WHERE personId = ? AND idType = (SELECT id FROM LegalIdTypes WHERE legalIdType = ?) AND timestamp IS NULL";
        jdbcTemplate.update(sqlQuery, personId, idType);
    }

}

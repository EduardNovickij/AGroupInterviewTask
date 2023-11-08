package com.AGroupInterviewTask.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

//Class for retrieving LegalIdTypes information from SQLite database.

@Repository("legalIdTypesRepository")
public class LegalIdTypesRepository {

    private final JdbcTemplate jdbcTemplate;

    public LegalIdTypesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Method for retrieving id types id from resulting set.
    private Integer mapRowToInteger(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("id");
    }

    //Method for retrieving id for idType. Throws exception in case if nothing was found.
    public Integer findOne(String idType) throws EmptyResultDataAccessException {
        String sqlQuery = "SELECT id " +
                "FROM LegalIdTypes WHERE legalIdType = ? ";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToInteger, idType);
    }
}

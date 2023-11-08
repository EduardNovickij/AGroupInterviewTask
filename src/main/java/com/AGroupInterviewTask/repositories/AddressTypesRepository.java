package com.AGroupInterviewTask.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

//Class for retrieving AddressTypes information from SQLite database.

@Repository("addressTypesRepository")
public class AddressTypesRepository {

    private final JdbcTemplate jdbcTemplate;

    public AddressTypesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Method for retrieving address id from resulting set.
    private Integer mapRowToInteger(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("id");
    }

    //Method for retrieving id for addressType. Throws exception in case if nothing was found.
    public Integer findOne(String addressType) throws EmptyResultDataAccessException {
        String sqlQuery = "SELECT id " +
                "FROM AddressTypes WHERE addressType = ? ";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToInteger, addressType);
    }

}

package com.AGroupInterviewTask.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("addressTypesRepository")
public class AddressTypesRepository {

    private final JdbcTemplate jdbcTemplate;

    public AddressTypesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Integer mapRowToInteger(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("id");
    }

    public Integer findOne(String addressType) throws EmptyResultDataAccessException {
        String sqlQuery = "SELECT id " +
                "FROM AddressTypes WHERE addressType = ? ";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToInteger, addressType);
    }

}

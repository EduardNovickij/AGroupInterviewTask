package com.AGroupInterviewTask.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("legalIdTypesRepository")
public class LegalIdTypesRepository {

    private final JdbcTemplate jdbcTemplate;

    public LegalIdTypesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Integer mapRowToInteger(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("id");
    }

    public Integer findOne(String idType) throws EmptyResultDataAccessException {
        String sqlQuery = "SELECT id " +
                "FROM LegalIdTypes WHERE legalIdType = ? ";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToInteger, idType);
    }
}

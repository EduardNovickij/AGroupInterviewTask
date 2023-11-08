package com.AGroupInterviewTask.validators;

import com.AGroupInterviewTask.entities.PersonLegalId;
import com.AGroupInterviewTask.repositories.PersonLegalIdRepository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Date;

public record PersonLegalIdValidator() {
    public static void checkPersonLegalIdInput(PersonLegalId personLegalId) throws Exception {
        int issuedByMaxLength = 50;

        String errorMessageStart = "Incorrect input: \n";
        String errorMessageEnd = "";

        if(personLegalId.getIssueDate() == null) throw new Exception(errorMessageStart + "issueDate can't be null.");
        DateValidator.checkDateFormat(personLegalId.getIssueDate());

        if(personLegalId.getIdNumber() == null) errorMessageEnd += "idNumber can't be null.\n";

        if(personLegalId.getIssuedBy().length() > issuedByMaxLength) errorMessageEnd += "issuedBy is too long.\n";

        if(!errorMessageEnd.equals("")) throw new Exception(errorMessageStart + errorMessageEnd);
    }

    public static void checkIfPersonLegalIdAlreadyExists(
            Integer personId, String idType, PersonLegalIdRepository personLegalIdRepository)
            throws Exception {
        try {
            personLegalIdRepository.findOne(new Date(System.currentTimeMillis()).toString(), personId, idType);
            throw new Exception("PersonLegalId with this personId and idType already exists.");
        }
        catch (EmptyResultDataAccessException ignored) {}
    }

    public static void checkIfPersonLegalIdExists(
            Integer personId, String idType, PersonLegalIdRepository personLegalIdRepository)
            throws Exception {
        try {
            personLegalIdRepository.findOne(new Date(System.currentTimeMillis()).toString(), personId, idType);
        }
        catch (EmptyResultDataAccessException ignored) { throw new Exception(
                "PersonLegalId with this personId and idType does not exist."); }
    }
}

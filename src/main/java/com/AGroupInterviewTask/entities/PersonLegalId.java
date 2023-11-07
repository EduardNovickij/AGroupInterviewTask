package com.AGroupInterviewTask.entities;

import com.AGroupInterviewTask.compositeKeys.PersonLegalIdKey;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "PersonLegalId")
public class PersonLegalId {
    @EmbeddedId
    private final PersonLegalIdKey personLegalIdKey;
    private final Integer idNumber;
    private final String issueDate;
    private final String issuedBy;

    @JsonCreator
    public PersonLegalId(@JsonProperty("idType") String idType,
                         @JsonProperty("personId") Integer personId,
                         @JsonProperty("idNumber") Integer idNumber,
                         @JsonProperty("issueDate") String issueDate,
                         @JsonProperty("issuedBy") String issuedBy) {
        this.personLegalIdKey = new PersonLegalIdKey(personId, idType);
        this.idNumber = idNumber;
        this.issueDate = issueDate;
        this.issuedBy = issuedBy;
    }

    public Integer getPersonId() {
        return personLegalIdKey.getPersonId();
    }

    public String getIdType() {
        return personLegalIdKey.getIdType();
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    @Override
    public String toString() {
        return "{" +
                "personId=" + personLegalIdKey.getPersonId() +
                ", idType=" + personLegalIdKey.getIdType() + '\'' +
                ", idNumber=" + idNumber + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                '}';
    }
}

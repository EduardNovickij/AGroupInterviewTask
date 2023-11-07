package com.AGroupInterviewTask.compositeKeys;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PersonLegalIdKey implements Serializable {
    private final Integer personId;
    private final String idType;

    public PersonLegalIdKey(Integer personId, String idType) {
        this.personId = personId;
        this.idType = idType;
    }

    public Integer getPersonId() {
        return personId;
    }

    public String getIdType() {
        return idType;
    }
}

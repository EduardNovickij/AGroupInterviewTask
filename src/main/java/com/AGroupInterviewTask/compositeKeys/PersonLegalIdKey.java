package com.AGroupInterviewTask.compositeKeys;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

//Class representing composite key for PersonLegalId entity.

@Embeddable
public class PersonLegalIdKey implements Serializable {

    //PersonLegalId entities composite key is made of personId and idType.
    private final Integer personId;
    private final String idType;

    //Constructor:
    public PersonLegalIdKey(Integer personId, String idType) {
        this.personId = personId;
        this.idType = idType;
    }

    //Getters:
    public Integer getPersonId() {
        return personId;
    }

    public String getIdType() {
        return idType;
    }
}

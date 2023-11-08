package com.AGroupInterviewTask.compositeKeys;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

//Class representing composite key for PersonAddress entity.

@Embeddable
public class PersonAddressKey implements Serializable {

    //PersonAddress entities composite key is made of personId and addressType.
    private final Integer personId;
    private final String addressType;

    //Constructor:
    public PersonAddressKey(Integer personId, String addressType) {
        this.personId = personId;
        this.addressType = addressType;
    }

    //Getters:
    public Integer getPersonId() {
        return personId;
    }

    public String getAddressType() {
        return addressType;
    }
}

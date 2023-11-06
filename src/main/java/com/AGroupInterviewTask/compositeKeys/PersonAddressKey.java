package com.AGroupInterviewTask.compositeKeys;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PersonAddressKey implements Serializable {
    private final Integer personId;
    private final String addressType;

    public PersonAddressKey(Integer personId, String addressType) {
        this.personId = personId;
        this.addressType = addressType;
    }

    public Integer getPersonId() {
        return personId;
    }

    public String getAddressType() {
        return addressType;
    }
}

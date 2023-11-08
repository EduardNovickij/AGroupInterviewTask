package com.AGroupInterviewTask.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Class representing AddressTypes entity that stores all types of addresses.

@Entity
@Table(name = "AddressTypes")
public class AddressTypes {

    //Data stored in AddressTypes entity:
    @Id
    private final Integer id;
    private final String addressType;

    //Constructor:
    public AddressTypes(Integer id, String addressType) {
        this.id = id;
        this.addressType = addressType;
    }

    //Getters:
    public Integer getId() {
        return id;
    }

    public String getAddressType() {
        return addressType;
    }

    //ToString:
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", addressType='" + addressType + '\'' +
                '}';
    }
}

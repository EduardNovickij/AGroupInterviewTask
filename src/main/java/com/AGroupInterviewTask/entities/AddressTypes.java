package com.AGroupInterviewTask.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AddressTypes")
public class AddressTypes {

    @Id
    private final Integer id;
    private final String addressType;

    public AddressTypes(Integer id, String addressType) {
        this.id = id;
        this.addressType = addressType;
    }

    public Integer getId() {
        return id;
    }

    public String getAddressType() {
        return addressType;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", addressType='" + addressType + '\'' +
                '}';
    }
}

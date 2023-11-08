package com.AGroupInterviewTask.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Class representing LegalIdTypes entity that stores all types of legal ids.

@Entity
@Table(name = "LegalIdTypes")
public class LegalIdTypes {

    //Data stored in LegalIdTypes entity:
    @Id
    private final Integer id;
    private final String legalIdType;

    //Constructor:
    public LegalIdTypes(Integer id, String legalIdType) {
        this.id = id;
        this.legalIdType = legalIdType;
    }

    //Getters:
    public Integer getId() {
        return id;
    }

    public String getLegalIdType() {
        return legalIdType;
    }

    //ToString:
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", legalIdType='" + legalIdType + '\'' +
                '}';
    }
}

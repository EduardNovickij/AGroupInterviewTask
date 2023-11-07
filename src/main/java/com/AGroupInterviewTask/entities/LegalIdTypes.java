package com.AGroupInterviewTask.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LegalIdTypes")
public class LegalIdTypes {

    @Id
    private final Integer id;
    private final String legalIdType;

    public LegalIdTypes(Integer id, String legalIdType) {
        this.id = id;
        this.legalIdType = legalIdType;
    }

    public Integer getId() {
        return id;
    }

    public String getLegalIdType() {
        return legalIdType;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", legalIdType='" + legalIdType + '\'' +
                '}';
    }
}

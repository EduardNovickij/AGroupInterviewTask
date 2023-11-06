package com.AGroupInterviewTask.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    private final Integer personId;
    private final String givenName;
    private final String familyName;
    private final String birthDate;
    private final String gender;

    @JsonCreator
    public Person(@JsonProperty("personId") Integer personId,
                  @JsonProperty("givenName") String givenName,
                  @JsonProperty("familyName") String familyName,
                  @JsonProperty("birthDate") String birthDate,
                  @JsonProperty("gender") String gender) {
        this.personId = personId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public Integer getPersonId() {
        return personId;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "{" +
                "personId=" + personId +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}

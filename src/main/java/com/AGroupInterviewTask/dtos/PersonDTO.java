package com.AGroupInterviewTask.dtos;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.entities.PersonLegalId;

import java.util.List;

//Class used for returning all information about Person as well as their Addresses and LegalIds.

public class PersonDTO {

    private final Integer personId;
    private final String givenName;
    private final String familyName;
    private final String birthDate;
    private final String gender;

    private final List<PersonAddressDTO> personAddressDTOList;
    private final List<PersonLegalIdDTO> personLegalIdDTOList;

    public PersonDTO(Integer personId, String givenName, String familyName, String birthDate, String gender,
                     List<PersonAddressDTO> personAddressDTOList, List<PersonLegalIdDTO> personLegalIdDTOList) {
        this.personId = personId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.personAddressDTOList = personAddressDTOList;
        this.personLegalIdDTOList = personLegalIdDTOList;
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

    public List<PersonAddressDTO> getPersonAddressDTOList() {
        return personAddressDTOList;
    }

    public List<PersonLegalIdDTO> getPersonLegalIdDTOList() {
        return personLegalIdDTOList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender='" + gender + '\'' +
                ", PersonAddress=" + personAddressDTOList +
                ", PersonLegalId=" + personLegalIdDTOList +
                '}';
    }
}

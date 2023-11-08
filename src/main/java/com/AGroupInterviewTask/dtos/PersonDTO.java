package com.AGroupInterviewTask.dtos;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.entities.PersonLegalId;

import java.util.List;

//Class used for returning all information about Person as well as their Addresses and LegalIds.

public class PersonDTO {
    //Information about a Person from Person entity.
    private final Person person;

    //Information about their Addresses from PersonAddress entity.
    private final List<PersonAddress> personAddressList;

    //Information about their LegalIds from PersonLegalId entity.
    private final List<PersonLegalId> personLegalIdList;

    //Constructor:
    public PersonDTO(Person person, List<PersonAddress> personAddressList, List<PersonLegalId> personLegalIdList) {
        this.person = person;
        this.personAddressList = personAddressList;
        this.personLegalIdList = personLegalIdList;
    }

    //Getters:
    public Person getPerson() {
        return person;
    }

    public List<PersonAddress> getPersonAddressList() {
        return personAddressList;
    }

    public List<PersonLegalId> getPersonLegalIdList() {
        return personLegalIdList;
    }

    //ToString:
    @Override
    public String toString() {
        return "{" +
                "person=" + person +
                ", personAddressList=" + personAddressList +
                ", personLegalIdList=" + personLegalIdList +
                '}';
    }
}

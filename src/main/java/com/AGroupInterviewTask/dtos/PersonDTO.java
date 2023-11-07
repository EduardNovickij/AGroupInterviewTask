package com.AGroupInterviewTask.dtos;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.entities.PersonLegalId;

import java.util.List;

public class PersonDTO {
    private final Person person;
    private final List<PersonAddress> personAddressList;
    private final List<PersonLegalId> personLegalIdList;

    public PersonDTO(Person person, List<PersonAddress> personAddressList, List<PersonLegalId> personLegalIdList) {
        this.person = person;
        this.personAddressList = personAddressList;
        this.personLegalIdList = personLegalIdList;
    }

    public Person getPerson() {
        return person;
    }

    public List<PersonAddress> getPersonAddressList() {
        return personAddressList;
    }

    public List<PersonLegalId> getPersonLegalIdList() {
        return personLegalIdList;
    }

    @Override
    public String toString() {
        return "{" +
                "person=" + person +
                ", personAddressList=" + personAddressList +
                ", personLegalIdList=" + personLegalIdList +
                '}';
    }
}

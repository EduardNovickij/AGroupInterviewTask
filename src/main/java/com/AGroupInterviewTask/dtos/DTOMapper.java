package com.AGroupInterviewTask.dtos;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.entities.PersonLegalId;

import java.util.ArrayList;
import java.util.List;

public record DTOMapper() {
    public static PersonDTO mapToPersonDTO(Person person, List<PersonAddress> personAddressList, List<PersonLegalId> personLegalIdList) {
        List<PersonAddressDTO> personAddressDTOList = new ArrayList<>();
        List<PersonLegalIdDTO> personLegalIdDTOList = new ArrayList<>();

        personAddressList.forEach((personAddress)-> personAddressDTOList.add(mapToPersonAddressDTO(personAddress)));
        personLegalIdList.forEach((personLegalId)-> personLegalIdDTOList.add(mapToPersonLegalIdDTO(personLegalId)));

        return new PersonDTO(person.getPersonId(), person.getGivenName(), person.getFamilyName(), person.getBirthDate(),
                person.getGender(), personAddressDTOList, personLegalIdDTOList);
    }

    public static PersonAddressDTO mapToPersonAddressDTO(PersonAddress personAddress) {
        return new PersonAddressDTO(personAddress.getAddressType(), personAddress.getCity(),
                personAddress.getStreet(), personAddress.getAppartment());
    }

    public static PersonLegalIdDTO mapToPersonLegalIdDTO(PersonLegalId personLegalId) {
        return new PersonLegalIdDTO(personLegalId.getIdType(), personLegalId.getIdNumber(),
                personLegalId.getIssueDate(), personLegalId.getIssuedBy());
    }
}

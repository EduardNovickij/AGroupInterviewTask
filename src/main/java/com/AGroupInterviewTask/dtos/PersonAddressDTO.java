package com.AGroupInterviewTask.dtos;

public class PersonAddressDTO {

    private final String addressType;
    private final String city;
    private final String street;
    private final String appartment;

    public PersonAddressDTO(String addressType, String city, String street, String appartment) {
        this.addressType = addressType;
        this.city = city;
        this.street = street;
        this.appartment = appartment;
    }

    public String getAddressType() {
        return addressType;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getAppartment() {
        return appartment;
    }

    @Override
    public String toString() {
        return "{" +
                "addressType='" + addressType + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", appartment='" + appartment + '\'' +
                '}';
    }
}

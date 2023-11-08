package com.AGroupInterviewTask.dtos;

public class PersonLegalIdDTO {

    private final String idType;
    private final Integer idNumber;
    private final String issueDate;
    private final String issuedBy;

    public PersonLegalIdDTO(String idType, Integer idNumber, String issueDate, String issuedBy) {
        this.idType = idType;
        this.idNumber = idNumber;
        this.issueDate = issueDate;
        this.issuedBy = issuedBy;
    }

    public String getIdType() {
        return idType;
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    @Override
    public String toString() {
        return "{" +
                "idType='" + idType + '\'' +
                ", idNumber=" + idNumber +
                ", issueDate='" + issueDate + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                '}';
    }
}

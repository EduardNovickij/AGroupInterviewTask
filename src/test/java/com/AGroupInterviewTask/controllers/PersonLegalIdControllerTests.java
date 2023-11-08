package com.AGroupInterviewTask.controllers;

import com.AGroupInterviewTask.entities.PersonLegalId;
import com.AGroupInterviewTask.services.PersonLegalIdService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//Unit tests for PersonLegalIdController.
@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonLegalIdController.class)
public class PersonLegalIdControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonLegalIdService personLegalIdService;

    @Test
    public void getPersonLegalIdAsOfDate() throws Exception {
        String endPoint = "/getPersonLegalId";

        Mockito.when(personLegalIdService.findAll(Mockito.anyString()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(endPoint + "?asOfDate=anyDate");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void getPersonLegalIdAsOfDatePersonId() throws Exception {
        String endPoint = "/getPersonLegalId";

        Mockito.when(personLegalIdService.findAll(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(endPoint + "?asOfDate=anyDate&personId=1");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void getPersonLegalIdAsOfDateIdType() throws Exception {
        String endPoint = "/getPersonLegalId";

        Mockito.when(personLegalIdService.findAll(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(endPoint + "?asOfDate=anyDate&idType=anyType");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void getPersonLegalIdAsOfDatePersonIdIdType() throws Exception {
        String endPoint = "/getPersonLegalId";

        Mockito.when(personLegalIdService.findOne(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(endPoint + "?asOfDate=anyDate&personId=1&idType=anyType");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void insertPersonLegalId() throws Exception {
        String endPoint = "/insertPersonLegalId";
        String mockPersonLegalIdJson = "{\"personId\": 1 ," +
                "\"idType\":\"Test\"," +
                "\"idNumber\": 1 ," +
                "\"issueDate\":\"Test\"," +
                "\"issuedBy\":\"Test\"" +
                "}";

        Mockito.when(personLegalIdService.save(Mockito.any(PersonLegalId.class)))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(endPoint)
                .content(mockPersonLegalIdJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void updatePersonLegalId() throws Exception {
        String endPoint = "/updatePersonLegalId";
        String mockPersonLegalIdJson = "{\"personId\": 1 ," +
                "\"idType\":\"Test\"," +
                "\"idNumber\": 1 ," +
                "\"issueDate\":\"Test\"," +
                "\"issuedBy\":\"Test\"" +
                "}";
        Mockito.when(personLegalIdService.update(Mockito.any(PersonLegalId.class), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(endPoint + "?personId=1&idType=anyType")
                .content(mockPersonLegalIdJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deletePersonLegalId() throws Exception {
        String endPoint = "/deletePersonLegalId";

        Mockito.when(personLegalIdService.delete(Mockito.anyInt(),Mockito.anyString()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(endPoint + "?personId=1&idType=anyType");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}

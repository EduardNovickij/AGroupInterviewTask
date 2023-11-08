package com.AGroupInterviewTask.controllers;

import com.AGroupInterviewTask.entities.PersonAddress;
import com.AGroupInterviewTask.services.PersonAddressService;
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

//Unit tests for PersonAddressController.
@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonAddressController.class)
public class PersonAddressControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonAddressService personAddressService;

    @Test
    public void getPersonAddressAsOfDate() throws Exception {
        String endPoint = "/getPersonAddress";

        Mockito.when(personAddressService.findAll(Mockito.anyString()))
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
    public void getPersonAddressAsOfDatePersonId() throws Exception {
        String endPoint = "/getPersonAddress";

        Mockito.when(personAddressService.findAll(Mockito.anyString(), Mockito.anyInt()))
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
    public void getPersonAddressAsOfDateAddressType() throws Exception {
        String endPoint = "/getPersonAddress";

        Mockito.when(personAddressService.findAll(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(endPoint + "?asOfDate=anyDate&addressType=anyType");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void getPersonAddressAsOfDatePersonIdAddressType() throws Exception {
        String endPoint = "/getPersonAddress";

        Mockito.when(personAddressService.findOne(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(endPoint + "?asOfDate=anyDate&personId=1&addressType=anyType");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void insertPersonAddress() throws Exception {
        String endPoint = "/insertPersonAddress";
        String mockPersonAddressJson = "{\"personId\": 1 ," +
                "\"addressType\":\"Test\"," +
                "\"city\":\"Test\"," +
                "\"street\":\"Test\"," +
                "\"appartment\":\"Test\"" +
                "}";

        Mockito.when(personAddressService.save(Mockito.any(PersonAddress.class)))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(endPoint)
                .content(mockPersonAddressJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void updatePersonAddress() throws Exception {
        String endPoint = "/updatePersonAddress";
        String mockPersonAddressJson = "{\"personId\": 1 ," +
                "\"addressType\":\"Test\"," +
                "\"city\":\"Test\"," +
                "\"street\":\"Test\"," +
                "\"appartment\":\"Test\"" +
                "}";
        Mockito.when(personAddressService.update(Mockito.any(PersonAddress.class), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(endPoint + "?personId=1&addressType=anyType")
                .content(mockPersonAddressJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deletePersonAddress() throws Exception {
        String endPoint = "/deletePersonAddress";

        Mockito.when(personAddressService.delete(Mockito.anyInt(),Mockito.anyString()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(endPoint + "?personId=1&addressType=anyType");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}

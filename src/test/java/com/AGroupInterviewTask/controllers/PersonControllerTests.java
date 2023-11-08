package com.AGroupInterviewTask.controllers;

import com.AGroupInterviewTask.entities.Person;
import com.AGroupInterviewTask.services.PersonService;
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

//Unit tests for PersonController.
@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void getPersonAsOfDate() throws Exception {
        String endPoint = "/getPerson";

        Mockito.when(personService.findAll(Mockito.anyString()))
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
    public void getPersonAsOfDatePersonId() throws Exception {
        String endPoint = "/getPerson";

        Mockito.when(personService.findOne(Mockito.anyString(), Mockito.anyInt()))
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
    public void insertPerson() throws Exception {
        String endPoint = "/insertPerson";
        String mockPersonJson = "{\"givenName\":\"Test\"," +
                "\"familyName\":\"Test\"," +
                "\"birthDate\":\"Test\"," +
                "\"gender\":\"T\"" +
                "}";

        Mockito.when(personService.save(Mockito.any(Person.class)))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(endPoint)
                .content(mockPersonJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void updatePerson() throws Exception {
        String endPoint = "/updatePerson";
        String mockPersonJson = "{\"givenName\":\"Test\"," +
                "\"familyName\":\"Test\"," +
                "\"birthDate\":\"Test\"," +
                "\"gender\":\"T\"" +
                "}";
        Mockito.when(personService.update(Mockito.any(Person.class), Mockito.anyInt()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(endPoint + "?personId=1")
                .content(mockPersonJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deletePerson() throws Exception {
        String endPoint = "/deletePerson";

        Mockito.when(personService.delete(Mockito.anyInt()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Test"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(endPoint + "?personId=1");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}

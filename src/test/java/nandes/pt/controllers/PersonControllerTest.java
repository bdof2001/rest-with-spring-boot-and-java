package nandes.pt.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nandes.pt.exceptions.ResourceNotFoundException;
import nandes.pt.model.Person;
import nandes.pt.services.PersonServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonServices service;

    private Person person0;

    @BeforeEach
    void setup() {
        person0 = new Person("Leandro", "Costa", "leandrocosta@gmail.com", "Rua do Campo", "Male");
    }

    @DisplayName("JUnit test for Given Person Object when Create Person then Return Saved Person")
    @Test
    void testGivenPersonObject_WhenCreatePerson_thenReturnSavedPerson() throws JsonProcessingException, Exception {
        // Given / Arrange
        given(service.create(any(Person.class))).willAnswer(invocation -> invocation.getArgument(0));
        // When / Act
        ResultActions response = mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person0)));
        // Then / Assert
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person0.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person0.getLastName())))
                .andExpect(jsonPath("$.email", is(person0.getEmail())));
    }

    @Test
    @DisplayName("JUnit test for Given List of Persons when findAll Persons then Return Persons List")
    void testGivenListOfPersons_WhenFindAllPersons_thenReturnPersonsList() throws JsonProcessingException, Exception {

        // Given / Arrange
        Person person1 = new Person("Leonardo", "Costa", "leonardocosta@gmail.com", "Rua do Campo", "Male");
        List<Person> persons = new ArrayList<>();
        persons.add(person0);
        persons.add(person1);

        given(service.findAll()).willReturn(persons);

        // When / Act
        ResultActions response = mockMvc.perform(get("/person"));

        // Then / Assert
        response.
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(persons.size())));
    }

    @Test
    @DisplayName("JUnit test for Given personId when findById then Return Person Object")
    void testGivenPersonId_WhenFindById_thenReturnPersonObject() throws JsonProcessingException, Exception {

        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willReturn(person0);

        // When / Act
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        // Then / Assert
        response.
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(person0.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person0.getLastName())))
                .andExpect(jsonPath("$.email", is(person0.getEmail())));
    }

    @Test
    @DisplayName("JUnit test for Given Invalid PersonId when findById then Return Not Found")
    void testGivenInvalidPersonId_WhenFindById_thenReturnNotFound() throws JsonProcessingException, Exception {

        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willThrow(ResourceNotFoundException.class);

        // When / Act
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        // Then / Assert
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("JUnit test for Given Updated Person when Update then Return Updated Person Object")
    void testGivenUpdatedPerson_WhenUpdate_thenReturnUpdatedPersonObject() throws JsonProcessingException, Exception {

        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willReturn(person0);
        given(service.update(any(Person.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // When / Act
        Person updatedPerson = new Person("Leonardo", "Costa", "leonardocosta@gmail.com", "Rua do Campo", "Male");

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPerson)));

        // Then / Assert
        response.
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedPerson.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedPerson.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedPerson.getEmail())));
    }

    @Test
    @DisplayName("JUnit test for Given Unexistent Person when Update then Return Not Found")
    void testGivenUnexistentPerson_WhenUpdate_thenReturnNotFound() throws JsonProcessingException, Exception {

        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willThrow(ResourceNotFoundException.class);
        given(service.update(any(Person.class)))
                .willAnswer((invocation) -> invocation.getArgument(1));

        // When / Act
        Person updatedPerson = new Person("Leonardo", "Costa", "leonardocosta@gmail.com", "Rua do Campo", "Male");

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPerson)));

        // Then / Assert
        response.
                andExpect(status().isNotFound())
                .andDo(print());
    }
}

package nandes.pt.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

}

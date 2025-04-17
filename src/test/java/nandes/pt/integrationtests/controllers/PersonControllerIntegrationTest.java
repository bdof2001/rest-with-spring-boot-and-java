package nandes.pt.integrationtests.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import nandes.pt.config.TestConfigs;
import nandes.pt.integrationtests.testcontainers.AbstractIntegrationTest;
import nandes.pt.model.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PersonControllerIntegrationTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Person person0;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("/person")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
        person0 = new Person("Leandro", "Costa", "leandrocosta@gmail.com", "Rua do Campo", "Male");
    }

    @Test
    @Order(1)
    @DisplayName("JUnit integration given Person Object when Create one Person should Return a Person Object")
    void integrationTestGivenPersonObject_when_CreateOnePerson_ShouldReturnAPersonObject() throws JsonMappingException, JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person0)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person createdPerson = objectMapper.readValue(content, Person.class);

        person0 = createdPerson;

        assertNotNull(createdPerson);

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());
        assertNotNull(createdPerson.getEmail());

        assertTrue(createdPerson.getId() > 0);
        assertEquals("Leandro", createdPerson.getFirstName());
        assertEquals("Costa", createdPerson.getLastName());
        assertEquals("Rua do Campo", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertEquals("leandrocosta@gmail.com", createdPerson.getEmail());
    }

    @Test
    @Order(2)
    @DisplayName("JUnit integration given Person Object when Update one Person should Return a Updated Person Object")
    void integrationTestGivenPersonObject_when_UpdateOnePerson_ShouldReturnAUpdatedPersonObject() throws JsonMappingException, JsonProcessingException {

        person0.setFirstName("Leonardo");
        person0.setEmail("leonardo@erudio.com.br");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person0)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person updatedPerson = objectMapper.readValue(content, Person.class);

        person0 = updatedPerson;

        assertNotNull(updatedPerson);

        assertNotNull(updatedPerson.getId());
        assertNotNull(updatedPerson.getFirstName());
        assertNotNull(updatedPerson.getLastName());
        assertNotNull(updatedPerson.getAddress());
        assertNotNull(updatedPerson.getGender());
        assertNotNull(updatedPerson.getEmail());

        assertTrue(updatedPerson.getId() > 0);
        assertEquals("Leonardo", updatedPerson.getFirstName());
        assertEquals("Costa", updatedPerson.getLastName());
        assertEquals("Rua do Campo", updatedPerson.getAddress());
        assertEquals("Male", updatedPerson.getGender());
        assertEquals("leonardo@gmail.com", updatedPerson.getEmail());
    }
}

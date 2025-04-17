package nandes.pt.integrationtests.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import nandes.pt.config.TestConfigs;
import nandes.pt.integrationtests.testcontainers.AbstractIntegrationTest;
import nandes.pt.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PersonControllerIntegrationTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Person person0;

    @BeforeAll
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        specification = new RequestSpecBuilder()
                .setBasePath("")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
        person0 = new Person("Leandro", "Costa", "leandrocosta@gmail.com", "Rua do Campo", "Male");
    }

}

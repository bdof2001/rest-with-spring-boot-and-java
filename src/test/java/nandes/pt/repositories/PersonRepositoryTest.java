package nandes.pt.repositories;

import nandes.pt.integrationtests.testcontainers.AbstractIntegrationTest;
import nandes.pt.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PersonRepository repository;

    private Person person0;

    @BeforeEach
    void setup() {
        person0 = new Person("Leandro", "Costa", "leandrocosta@gmail.com", "Rua do Campo", "Male");
    }

    @DisplayName("JUnit test for Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenSave_thenReturnSavedPerson() {
        // Given / Arrange

        // When / Act
        Person savedPerson = repository.save(person0);

        // Then / Assert
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }

    @DisplayName("JUnit test for Given Person List when findAll then Return Person List")
    @Test
    void testGivenPersonList_whenFindAll_thenReturnPersonList() {
        // Given / Arrange

        Person person1 = new Person("Leonardo", "Costa", "leonardocosta@gmail.com", "Rua do Campo", "Male");

        repository.save(person0);
        repository.save(person1);

        // When / Act
        List<Person> personList = repository.findAll();

        // Then / Assert
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @DisplayName("JUnit test for Given Person Object when findById then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindById_thenReturnPersonObject() {
        // Given / Arrange

        repository.save(person0);

        // When / Act
        Person savedPerson = repository.findById(person0.getId()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(), savedPerson.getId());
    }

    @DisplayName("JUnit test for Given Person Object when findByEmail then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindByEmail_thenReturnPersonObject() {
        // Given / Arrange

        repository.save(person0);

        // When / Act
        Person savedPerson = repository.findByEmail(person0.getEmail()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(), savedPerson.getId());
    }

    @DisplayName("JUnit test for Given Person Object when Update Person then Return Updated Person Object")
    @Test
    void testGivenPersonObject_whenUpdatePerson_thenReturnUpdatedPersonObject() {
        // Given / Arrange

        repository.save(person0);

        // When / Act
        Person savedPerson = repository.findById(person0.getId()).get();
        savedPerson.setFirstName("Leonardo");
        savedPerson.setEmail("leonardocosta@gmail.com");

        Person updatedPerson = repository.save(savedPerson);

        // Then / Assert
        assertNotNull(updatedPerson);
        assertEquals("Leonardo", updatedPerson.getFirstName());
        assertEquals("leonardocosta@gmail.com", updatedPerson.getEmail());
    }

    @DisplayName("JUnit test for Given Person Object when Delete then Remove Person")
    @Test
    void testGivenPersonObject_whenDelete_thenRemovePerson() {
        // Given / Arrange

        repository.save(person0);

        // When / Act
        repository.deleteById(person0.getId());

        Optional<Person> personOptional = repository.findById(person0.getId());

        // Then / Assert
        assertTrue(personOptional.isEmpty());
    }

    @DisplayName("JUnit test for Given firstName and lastName when findByJPQL then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByJPQL_thenReturnPersonObject() {
        // Given / Arrange

        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByJPQL(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("JUnit test for Given firstName and lastName when findByJPQLNamedParameters then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByJPQLNamedParameters_thenReturnPersonObject() {
        // Given / Arrange

        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByJPQLNamedParameters(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("JUnit test for Given firstName and lastName when findByNativeSQL then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByNativeSQL_thenReturnPersonObject() {
        // Given / Arrange

        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByNativeSQL(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("JUnit test for Given firstName and lastName when findByNativeSQLwithNamedParameters then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByNativeSQLWithNamedParameters_thenReturnPersonObject() {
        // Given / Arrange

        repository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";

        // When / Act
        Person savedPerson = repository.findByNativeSQLwithNamedParameters(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }
}
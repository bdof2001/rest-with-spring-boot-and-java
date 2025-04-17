package nandes.pt.repositories;


import nandes.pt.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @DisplayName("Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenSave_thenReturnSavedPerson() {
        // Given / Arrange

        Person person0 = new Person("Leandro", "Costa", "leandrocosta@gmail.com", "Rua do Campo", "Male");

        // When / Act
        Person savedPerson = repository.save(person0);

        // Then / Assert
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }

    @DisplayName("Given Person List when findAll then Return Person List")
    @Test
    void testGivenPersonList_whenFindAll_thenReturnPersonList() {
        // Given / Arrange

        Person person0 = new Person("Leandro", "Costa", "leandrocosta@gmail.com", "Rua do Campo", "Male");
        Person person1 = new Person("Leonardo", "Costa", "leonardocosta@gmail.com", "Rua do Campo", "Male");

        repository.save(person0);
        repository.save(person1);

        // When / Act
        List<Person> personList = repository.findAll();

        // Then / Assert
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @DisplayName("Given Person Object when findById then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindById_thenReturnPersonObject() {
        // Given / Arrange

        Person person0 = new Person("Leandro", "Costa", "leandrocosta@gmail.com", "Rua do Campo", "Male");

        repository.save(person0);

        // When / Act
        Person savedPerson = repository.findById(person0.getId()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(), savedPerson.getId());
    }

    @DisplayName("Given Person Object when findByEmail then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindByEmail_thenReturnPersonObject() {
        // Given / Arrange

        Person person0 = new Person("Leandro", "Costa", "leandrocosta@gmail.com", "Rua do Campo", "Male");

        repository.save(person0);

        // When / Act
        Person savedPerson = repository.findByEmail(person0.getEmail()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(), savedPerson.getId());
    }

    @DisplayName("Given Person Object when Update Person then Return Updated Person Object")
    @Test
    void testGivenPersonObject_whenUpdatePerson_thenReturnUpdatedPersonObject() {
        // Given / Arrange

        Person person0 = new Person("Leandro", "Costa", "leandrocosta@gmail.com", "Rua do Campo", "Male");

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
}

package nandes.pt.services;

import nandes.pt.exceptions.ResourceNotFoundException;
import nandes.pt.model.Person;
import nandes.pt.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonServices services;

    private Person person0;

    @BeforeEach
    void setup() {
        person0 = new Person("Leandro", "Costa", "leandrocosta@gmail.com", "Rua do Campo", "Male");
    }

    @DisplayName("JUnit test for Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_WhenSavePerson_thenReturnPersonObject() {
        // Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(repository.save(person0)).willReturn(person0);

        // When / Act
        Person savedPerson = services.create(person0);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("Leandro", savedPerson.getFirstName());
    }

    @DisplayName("JUnit test for Given Existing Email when Save then Throws Exception")
    @Test
    void testGivenExistingEmail_WhenSavePerson_thenThrowsException() {
        // Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.of(person0));

        // When / Act
        assertThrows(ResourceNotFoundException.class, () -> {
            services.create(person0);
        });

        // Then / Assert
        verify(repository,never()).save(any(Person.class));
    }
}

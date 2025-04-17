package nandes.pt.repositories;

import nandes.pt.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);

    @Query("SELECT p FROM Person p WHERE p.firstName = ?1 AND p.lastName = ?2")
    Person findByJPQL(String firstName, String lastName);
}

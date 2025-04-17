package nandes.pt.controllers;

import nandes.pt.model.Person;
import nandes.pt.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices service;

    @GetMapping(produces = "application/json")
    public List<Person> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Person> findById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public Person create(@RequestBody Person person) {
        return service.create(person);
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Person> update(@RequestBody Person person) {
        try {
            return ResponseEntity.ok(service.update(person));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
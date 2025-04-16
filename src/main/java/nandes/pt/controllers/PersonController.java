package nandes.pt.controllers;

import nandes.pt.model.Person;
import nandes.pt.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Person findById(@PathVariable(value = "id") String id) {
        return service.findById(id);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public Person create(@RequestBody Person person) {
        return service.create(person);
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    public Person update(@RequestBody Person person) {
        return service.update(person);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void delete(@PathVariable(value = "id") String id) {
        service.delete(id);
    }
}
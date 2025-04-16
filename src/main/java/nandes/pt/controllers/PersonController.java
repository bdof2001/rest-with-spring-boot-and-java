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

    @GetMapping( produces = "application/json")
    public List<Person> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Person findById(@PathVariable(value = "id") String id) {
        return service.findById(id);
    }
}
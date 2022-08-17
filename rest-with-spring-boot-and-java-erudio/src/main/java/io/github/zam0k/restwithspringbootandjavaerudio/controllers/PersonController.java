package io.github.zam0k.restwithspringbootandjavaerudio.controllers;

import io.github.zam0k.restwithspringbootandjavaerudio.data.vo.v1.PersonVO;
import io.github.zam0k.restwithspringbootandjavaerudio.model.Person;
import io.github.zam0k.restwithspringbootandjavaerudio.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices service;

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonVO> findAll() {
        return service.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO create(@RequestBody PersonVO person) {
        return service.create(person);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, //isso aqui é necessário por causa do swagger
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@PathVariable(value = "id") Long id, @RequestBody PersonVO person) {
        return service.update(person, id);
    }

    @DeleteMapping(
            value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

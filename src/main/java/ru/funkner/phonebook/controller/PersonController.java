package ru.funkner.phonebook.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.funkner.phonebook.controller.api.ErrorResponse;
import ru.funkner.phonebook.controller.api.NameSurname;
import ru.funkner.phonebook.exceptions.PersonException;
import ru.funkner.phonebook.model.Person;
import ru.funkner.phonebook.service.PersonService;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/show-persons")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping(value = "/person")
    public ResponseEntity<?> addPerson(@RequestBody Person person) {
        try {
            return ResponseEntity.ok(personService.savePerson(person));
        } catch (PersonException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new ErrorResponse(
                                    ZonedDateTime.now(),
                                    ex.getCode(),
                                    ex.getDescription()
                            )
                    );
        }
    }

    @PostMapping(value = "/person/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(personService.deleteById(id));
        } catch (PersonException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new ErrorResponse(
                                    ZonedDateTime.now(),
                                    ex.getCode(),
                                    ex.getDescription()
                            )
                    );
        }
    }

    @PostMapping(value = "/person/delete-by-name-surname")
    public ResponseEntity<?> deleteByNameSurname(@RequestBody NameSurname nameSurname) {
        try {
            return ResponseEntity.ok(personService.deleteByNameSurname(nameSurname.getName(), nameSurname.getSurname()));
        } catch (PersonException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new ErrorResponse(
                                    ZonedDateTime.now(),
                                    ex.getCode(),
                                    ex.getDescription()
                            )
                    );
        }

    }

    @PostMapping(value = "person/update")
    public ResponseEntity<?> updatePerson(@RequestBody Person person){
        try {
            return ResponseEntity.ok(personService.updatePerson(person));
        } catch (PersonException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new ErrorResponse(
                                    ZonedDateTime.now(),
                                    ex.getCode(),
                                    ex.getDescription()
                            )
                    );
        }
    }
}

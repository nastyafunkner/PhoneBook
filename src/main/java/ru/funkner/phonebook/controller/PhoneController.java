package ru.funkner.phonebook.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.funkner.phonebook.controller.api.ErrorResponse;
import ru.funkner.phonebook.controller.api.NameSurname;
import ru.funkner.phonebook.exceptions.PersonException;
import ru.funkner.phonebook.exceptions.PhoneBookException;
import ru.funkner.phonebook.exceptions.PhoneException;
import ru.funkner.phonebook.model.Person;
import ru.funkner.phonebook.model.PhoneNumber;
import ru.funkner.phonebook.service.PhoneService;

import java.time.ZonedDateTime;

@RestController
public class PhoneController {

    private final PhoneService phoneService;


    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @PostMapping(value = "/phone/add-number")
    public ResponseEntity<?> addNumber(@RequestBody PhoneNumber phoneNumber) {
        try {
            return ResponseEntity.ok(phoneService.saveNumber(phoneNumber));
        } catch (PhoneBookException ex) {
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

    @PostMapping(value = "/phone/delete-number/{number}")
    public ResponseEntity<?> deleteNumber(@PathVariable("number") String number) {
        try {
            return ResponseEntity.ok(phoneService.deleteNumber(number));
        } catch (PhoneException ex) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            ZonedDateTime.now(),
                            ex.getCode(),
                            ex.getDescription()
                    )
            );
        }
    }

    @PostMapping(value = "/phone/find-person-by-number/{number}")
    public ResponseEntity<?> findPersonByNumber(@PathVariable("number") String number) {
        try {
            return ResponseEntity.ok(phoneService.findPersonByNumber(number));
        } catch (PhoneBookException ex) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            ZonedDateTime.now(),
                            ex.getCode(),
                            ex.getDescription()
                    )
            );
        }
    }

    @PostMapping(value = "/phone/find-numbers-by-person")
    public ResponseEntity<?> findNumberByPersonNameAndSurname(@RequestBody NameSurname nameSurname){
        try{
            return ResponseEntity.ok(phoneService.findNumberByPersonNameAndSurname(nameSurname.getName(), nameSurname.getSurname()));
        } catch (PhoneBookException ex) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            ZonedDateTime.now(),
                            ex.getCode(),
                            ex.getDescription()
                    )
            );
        }
    }
}

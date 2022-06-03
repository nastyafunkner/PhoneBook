package ru.funkner.phonebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.funkner.phonebook.exceptions.PersonException;
import ru.funkner.phonebook.exceptions.PersonException.PersonApiErrorCode;
import ru.funkner.phonebook.model.CountryCode;
import ru.funkner.phonebook.model.Person;
import ru.funkner.phonebook.repository.CountryCodeRepository;
import ru.funkner.phonebook.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;

import static ru.funkner.phonebook.exceptions.PersonException.PersonApiErrorCode.*;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final CountryCodeRepository countryCodeRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, CountryCodeRepository countryCodeRepository) {
        this.personRepository = personRepository;
        this.countryCodeRepository = countryCodeRepository;
    }

    public Person findById(Long id) {
        if (id == null) {
            throw new PersonException(PersonApiErrorCode.NO_PERSON);
        }
        return personRepository
                .findById(id)
                .orElseThrow(() -> new PersonException(PersonApiErrorCode.NO_PERSON));
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person savePerson(Person person) {
        if (person.getName() == null || person.getSurname() == null || person.getCountry() == null) {
            throw new PersonException(MISSED_REQUIRED_FIELDS);
        }
        boolean isCountryPresented = countryCodeRepository.findAll().stream()
                .map(CountryCode::getCountry)
                .anyMatch(country -> country.equals(person.getCountry()));

        if (!isCountryPresented){
            throw new PersonException(RESTRICTED_COUNTRY);
        }

        return personRepository.save(person);
    }

    public Person deleteById(Long id) {
        Person person = findById(id);
        personRepository.deleteById(id);
        return person;
    }

    public Person deleteByNameSurname(String name, String surname) {
        List<Person> persons = personRepository.findByNameAndSurname(name, surname);
        if (persons.isEmpty()) {
            throw new PersonException(PersonApiErrorCode.NO_PERSON);
        } else if (persons.size() > 1) {
            throw new PersonException(PersonApiErrorCode.TOO_MANY_PERSONS);
        } else {
            return deleteById(persons.get(0).getId());

        }
    }

    public Person updatePerson(Person newPerson){
        Person person = findById(newPerson.getId());

        if (newPerson.getName() != null){
            person.setName(newPerson.getName());
        }
        if (newPerson.getSurname() != null){
            person.setSurname(newPerson.getSurname());
        }

        if (newPerson.getCountry() == null || !newPerson.getCountry().equals(person.getCountry())){
            throw new PersonException(RESTRICTED_COUNTRY_CHANGES);
        }
        return savePerson(person);
    }

}

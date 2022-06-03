package ru.funkner.phonebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.funkner.phonebook.exceptions.PersonException;
import ru.funkner.phonebook.exceptions.PhoneException;
import ru.funkner.phonebook.model.CountryCode;
import ru.funkner.phonebook.model.Person;
import ru.funkner.phonebook.model.PhoneNumber;
import ru.funkner.phonebook.repository.CountryCodeRepository;
import ru.funkner.phonebook.repository.PersonRepository;
import ru.funkner.phonebook.repository.PhoneNumberRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.funkner.phonebook.exceptions.PhoneException.PhoneApiErrorCode.*;

@Service
public class PhoneService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final CountryCodeRepository countryCodeRepository;
    private final PersonRepository personRepository;
    private final PersonService personService;

    @Autowired
    public PhoneService(
            PhoneNumberRepository phoneNumberRepository,
            CountryCodeRepository countryCodeRepository,
            PersonRepository personRepository
            ) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.countryCodeRepository = countryCodeRepository;
        this.personRepository = personRepository;
        personService = new PersonService(personRepository, countryCodeRepository);
    }

    public PhoneNumber findById(Long id) {
        if (id == null) {
            throw new PhoneException(NO_PHONE_NUMBER);
        }
        return phoneNumberRepository
                .findById(id)
                .orElseThrow(() -> new PhoneException(NO_PHONE_NUMBER));
    }

    public PhoneNumber saveNumber(PhoneNumber phoneNumber) {
        if (phoneNumber.getNumber() == null){
            throw new PhoneException(NO_PHONE_NUMBER);
        }

        List<PhoneNumber> phones = phoneNumberRepository.findByNumber(phoneNumber.getNumber());

        if (!phones.isEmpty()){
            throw new PhoneException(DUPLICATE_NUMBER);
        }

        Person person = personService.findById(phoneNumber.getPersonId());

        CountryCode countryCode = countryCodeRepository.findById(phoneNumber.getCountry()).orElseThrow(() -> new PhoneException(RESTRICTED_COUNTRY));;

        if(!phoneNumber.getNumber().startsWith(countryCode.getCode())) {
            throw new PhoneException(INCONSISTENT_COUNTRY_CODE);
        }

        if (!person.getCountry().equals(phoneNumber.getCountry())){
            throw new PhoneException(INCONSISTENT_COUNTRY_FOR_PERSON_AND_PHONE);
        }

        return phoneNumberRepository.save(phoneNumber);
    }

    public PhoneNumber findPhoneNumberByNumber(String number){
        List<PhoneNumber> numbers = phoneNumberRepository.findByNumber(number);
        if (numbers.isEmpty()) {
            throw new PhoneException(NO_NUMBER_IN_BOOK);
        }

        return numbers.get(0);
    }

    public PhoneNumber deleteNumber(String number){
        PhoneNumber phoneNumber = findPhoneNumberByNumber(number);
        phoneNumberRepository.delete(phoneNumber);
        return phoneNumber;

    }

    public Person findPersonByNumber(String number){
        PhoneNumber phoneNumber = findPhoneNumberByNumber(number);
        return personService.findById(phoneNumber.getPersonId());
    }

    public List<String> findNumberByPersonNameAndSurname(String name, String surname){
        List<Person> persons = personRepository.findByNameAndSurname(name, surname);
        if (persons.isEmpty()) {
            throw new PersonException(PersonException.PersonApiErrorCode.NO_PERSON);
        } else if (persons.size() > 1) {
            throw new PersonException(PersonException.PersonApiErrorCode.TOO_MANY_PERSONS);
        } else {
            List<PhoneNumber> phoneNumbers = phoneNumberRepository.findByPersonId(persons.get(0).getId());
            return phoneNumbers.stream()
                    .map(PhoneNumber::getNumber).collect(Collectors.toList());

        }
    }
}

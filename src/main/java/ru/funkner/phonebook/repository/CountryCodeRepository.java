package ru.funkner.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.funkner.phonebook.model.CountryCode;
import ru.funkner.phonebook.model.Person;

import java.util.List;

public interface CountryCodeRepository extends JpaRepository<CountryCode, String> {
}

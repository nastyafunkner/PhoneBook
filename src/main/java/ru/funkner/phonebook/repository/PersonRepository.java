package ru.funkner.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.funkner.phonebook.model.Person;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByNameAndSurname(String name, String surname);
}

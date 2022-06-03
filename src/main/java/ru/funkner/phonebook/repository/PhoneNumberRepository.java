package ru.funkner.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.funkner.phonebook.model.PhoneNumber;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    List<PhoneNumber> findByNumber(String number);
    List<PhoneNumber> findByPersonId(Long person_id);
}

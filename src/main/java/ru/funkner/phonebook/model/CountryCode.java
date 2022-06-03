package ru.funkner.phonebook.model;

import javax.persistence.*;

@Entity
@Table(name = "country_code")
public class CountryCode {
    @Id
    @Column(name = "country")
    private String country;

    @Column(name="code")
    private String code;

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }
}

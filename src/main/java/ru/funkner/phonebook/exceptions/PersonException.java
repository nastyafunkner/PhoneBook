package ru.funkner.phonebook.exceptions;

public class PersonException extends PhoneBookException {

    @Override
    public String getCode() {
        return code.name();
    }

    @Override
    public String getDescription() {
        return code.getDescription();
    }

    private final PersonApiErrorCode code;

    public PersonException(PersonApiErrorCode code) {
        super(code.getDescription());
        this.code = code;
    }

    public PersonException(PersonApiErrorCode code, Throwable cause) {
        super(code.getDescription(), cause);
        this.code = code;
    }

    public enum PersonApiErrorCode{
        NO_PERSON("Такого абонента нет в телефонной книге!"),
        TOO_MANY_PERSONS("Абонентов с такими именем и фамилией несколько в телефонной книге!"),
        MISSED_REQUIRED_FIELDS("Не заполнены обязательные поля для абонента."),
        RESTRICTED_COUNTRY("Такой страны нет в справочнике."),
        RESTRICTED_COUNTRY_CHANGES("Нельзя изменить страну абонента.");

        private final String description;

        PersonApiErrorCode(String description){
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}

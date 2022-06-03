package ru.funkner.phonebook.exceptions;

public class PhoneException extends PhoneBookException{
    private final PhoneApiErrorCode code;

    public PhoneException(PhoneException.PhoneApiErrorCode code) {
        super(code.getDescription());
        this.code = code;
    }

    public PhoneException(PhoneException.PhoneApiErrorCode code, Throwable cause) {
        super(code.getDescription(), cause);
        this.code = code;
    }

    public String getCode() {return code.name();}

    @Override
    public String getDescription() {
        return code.getDescription();
    }

    public enum PhoneApiErrorCode{
        NO_PHONE_NUMBER("Номер телефона не введён."),
        NO_NUMBER_IN_BOOK("Такой телефон не найден."),
        RESTRICTED_COUNTRY("Такой страны нет в справочнике."),
        INCONSISTENT_COUNTRY_CODE("Код не соотвествует стране"),
        DUPLICATE_NUMBER("Такой телефон уже есть в справочнике."),
        INCONSISTENT_COUNTRY_FOR_PERSON_AND_PHONE("Страна номера не соответствует стране пролживания абонента.");

        private final String description;

        PhoneApiErrorCode(String description){ this.description = description;}

        public String getDescription(){return description;}
    }
}

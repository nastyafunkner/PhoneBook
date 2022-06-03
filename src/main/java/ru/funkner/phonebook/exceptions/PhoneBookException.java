package ru.funkner.phonebook.exceptions;

public abstract class PhoneBookException extends RuntimeException {

    public PhoneBookException(String message){
        super(message);
    }

    public PhoneBookException(String message, Throwable cause){
        super(message, cause);
    }

    public abstract String getCode();

    public abstract String getDescription();
}

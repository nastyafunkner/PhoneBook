package ru.funkner.phonebook.controller.api;

import java.time.ZonedDateTime;

public class ErrorResponse {
    private ZonedDateTime timestamp;
    private String code;
    private String description;

    public ErrorResponse() {
    }

    public ErrorResponse(ZonedDateTime timestamp, String code, String description) {
        this.timestamp = timestamp;
        this.code = code;
        this.description = description;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

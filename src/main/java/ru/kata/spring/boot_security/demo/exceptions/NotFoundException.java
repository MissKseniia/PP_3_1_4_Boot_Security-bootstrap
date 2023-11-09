package ru.kata.spring.boot_security.demo.exceptions;

public class NotFoundException extends AppException {

    public NotFoundException(String message) {
        super(message);
    }
}

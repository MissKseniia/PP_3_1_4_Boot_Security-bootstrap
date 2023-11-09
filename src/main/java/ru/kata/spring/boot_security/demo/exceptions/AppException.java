package ru.kata.spring.boot_security.demo.exceptions;

public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }
}

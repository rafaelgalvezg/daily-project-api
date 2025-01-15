package dev.rafaelgalvezg.dailyprojectapi.exception;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}

package dev.rafaelgalvezg.dailyprojectapi.exception;

public class UserDisabledException extends Exception {
    public UserDisabledException(String message, Throwable cause) {
        super(message, cause);
    }
}

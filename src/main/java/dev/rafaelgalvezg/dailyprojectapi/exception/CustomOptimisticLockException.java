package dev.rafaelgalvezg.dailyprojectapi.exception;

public class CustomOptimisticLockException extends RuntimeException {
    public CustomOptimisticLockException(String message) {
        super(message);
    }
}

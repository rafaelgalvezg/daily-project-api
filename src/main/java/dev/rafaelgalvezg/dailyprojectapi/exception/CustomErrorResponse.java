package dev.rafaelgalvezg.dailyprojectapi.exception;

import java.time.LocalDateTime;

public record CustomErrorResponse (
        LocalDateTime dateTime,
        String message,
        String details
) {
}
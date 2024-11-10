package dev.rafaelgalvezg.dailyprojectapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@RestController
@RequestMapping("/languages")
@RequiredArgsConstructor
@Tag(name = "Languages", description = "Languages API")
public class LanguageController {
    private final LocaleResolver localeResolver;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @GetMapping("/locale/{locale}")
    public ResponseEntity<Void> setLocale(@PathVariable("locale") String locale) {
       Locale loc = switch (locale) {
           case "en", "us" -> Locale.ENGLISH;
           default -> Locale.ROOT;
       };
         localeResolver.setLocale(request, response, loc);
         return ResponseEntity.ok().build();
    }
}

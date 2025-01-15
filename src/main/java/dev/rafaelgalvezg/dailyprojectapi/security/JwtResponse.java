package dev.rafaelgalvezg.dailyprojectapi.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtResponse(@JsonProperty(value = "access_token") String accessToken) {

}

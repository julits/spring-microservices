package com.example.moviecenter.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.Instant;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Actor(String firstName, String lastName, Instant bornDate, Enum<Gender> gender){}

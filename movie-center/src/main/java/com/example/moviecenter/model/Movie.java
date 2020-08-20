package com.example.moviecenter.model;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    private Long id;
    private String name;
    private Instant date;
    private List<Actor> actors;
}

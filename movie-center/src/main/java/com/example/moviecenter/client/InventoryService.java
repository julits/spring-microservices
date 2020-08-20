package com.example.moviecenter.client;

import com.example.moviecenter.dto.MovieDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    @RequestMapping("/movie/{movieId}")
    Optional<MovieDTO> findMovieById(@PathVariable("movieId") Long movieId);

    @RequestMapping("/movies")
    List<MovieDTO> findAll();
}

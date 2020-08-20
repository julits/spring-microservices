package com.example.moviecenter.controller;

import com.example.moviecenter.model.Movie;
import com.example.moviecenter.service.MovieService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RefreshScope
@RestController
public class MovieController {

    private final MovieService movieService;
    private final String property;

    @Autowired
    public MovieController(@Value("${external.property}") String property,
                           MovieService movieService){
        this.property = property;
        this.movieService = movieService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok(property);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> retrieveMovies() {
        List<Movie> response = movieService.getMovies();
        return ResponseEntity.ok(response);
    }

    //Example using fallbacks at general level
    @HystrixCommand(fallbackMethod = "retrieveMovieFallBack")
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Movie> retrieveMovie(@PathVariable("movieId") Long movieId) {
        Movie response = movieService.getMovie(movieId);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Movie> retrieveMovieFallBack(Long movieId){
        log.warn("Movie services unavailable right now!");
        return ResponseEntity.ok(new Movie());
    }
}

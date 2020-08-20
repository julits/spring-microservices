package com.example.inventory.controller;

import com.example.inventory.model.Book;
import com.example.inventory.model.Movie;
import com.example.inventory.repository.BookRepository;
import com.example.inventory.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
public class InventoryController {


    private final BookRepository bookRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public InventoryController(BookRepository bookRepository,
                               MovieRepository movieRepository){
        this.bookRepository = bookRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/books")
    ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/movies")
    ResponseEntity<List<Movie>> getMovies() {
        return ResponseEntity.ok(movieRepository.findAll());
    }

    @GetMapping("/movie/{movieId}")
    ResponseEntity<Movie> getMovie(@PathVariable("movieId") Integer movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        return ResponseEntity.ok(movie.orElseGet(Movie::new));
    }
}

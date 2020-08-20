package com.example.moviecenter.service;

import com.example.moviecenter.model.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getMovies();

    Movie getMovie(Long movieId);
}

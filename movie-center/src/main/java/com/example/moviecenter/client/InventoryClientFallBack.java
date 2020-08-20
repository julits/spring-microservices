package com.example.moviecenter.client;

import com.example.moviecenter.dto.MovieDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InventoryClientFallBack implements InventoryClient{

    @Override
    public Optional<MovieDTO> findMovieById(Long movieId) {
        return Optional.empty();
    }

    @Override
    public List<MovieDTO> findAll() {
        return new ArrayList<>();
    }
}

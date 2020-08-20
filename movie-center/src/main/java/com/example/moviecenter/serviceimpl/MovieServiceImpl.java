package com.example.moviecenter.serviceimpl;

import com.example.moviecenter.client.InventoryClient;
import com.example.moviecenter.dto.MovieDTO;
import com.example.moviecenter.model.Actor;
import com.example.moviecenter.model.Gender;
import com.example.moviecenter.model.Movie;
import com.example.moviecenter.service.MovieService;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    public static final String INVENTORY_SERVICE = "inventory-service";
    public static final String HTTP_ENDPOINT_PATTERN = "http://%s:%s/%s";
    private static final String MOVIES_URI = "movie/";
    //Inventory client using Feign
    private final InventoryClient inventoryClient;
    //Eureka client for retrieving instances info by service name
    private final EurekaClient eurekaClient;
    private final RestTemplate restTemplate;

    @Autowired
    public MovieServiceImpl(InventoryClient inventoryClient, EurekaClient eurekaClient) {
        this.inventoryClient = inventoryClient;
        this.eurekaClient = eurekaClient;
        this.restTemplate = new RestTemplate();
    }

    // Example using Feign Client
    @Override
    public List<Movie> getMovies() {
        List<MovieDTO> movieDTO = inventoryClient.findAll();
        List<Movie> result = movieDTO.stream().map(dto -> Movie.builder().id(dto.getId()).name(dto.getName()).date(dto.getLaunchDate()).build()).collect(Collectors.toList());
        return result;
    }

    // Example using rest template and eureka info
    @Cacheable(value="movie",
            unless = "#result == null")
    @Override
    public Movie getMovie(Long movieId) {
        Application inventoryServiceInfo = eurekaClient.getApplication(INVENTORY_SERVICE);
        String url = inventoryServiceInfo.getInstances().stream().findAny().map(instanceInfo -> String.format(HTTP_ENDPOINT_PATTERN, instanceInfo.getHostName(), instanceInfo.getPort(),MOVIES_URI+movieId)).orElse("");

        MovieDTO movieDTO = new MovieDTO();
        Optional.ofNullable(url).filter(str -> str.equals("")).map(String::toString).ifPresent(uri ->{
            movieDTO.setName(uri);
        });
        
        if(url.isEmpty()){
            return new Movie();
        }
        log.info("about to execute GET: "+ url);
        ResponseEntity<MovieDTO> responseEntity = restTemplate.getForEntity(url, MovieDTO.class);
        MovieDTO dto = responseEntity.getBody();
        // New java specification Record
        var actor = new Actor("Julian","Tobon",Instant.now(), Gender.MALE);
        var actors = new ArrayList<Actor>();
        actors.add(actor);
        return Movie.builder().id(dto.getId()).name(dto.getName()).actors(actors).date(dto.getLaunchDate()).build();
    }
}

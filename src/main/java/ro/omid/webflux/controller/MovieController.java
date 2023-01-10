package ro.omid.webflux.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.omid.webflux.domain.MovieInfo;
import ro.omid.webflux.service.MovieInfoService;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MovieController {

    private final MovieInfoService movieInfoService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> save(@RequestBody MovieInfo movieInfo) {
        return movieInfoService.save(movieInfo);
    }

    @GetMapping("/get-all")
    public Flux<MovieInfo> getAllMovieInfos() {
        return movieInfoService.getAllMovies().log();
    }

    @GetMapping("/{id}")
    public Mono<MovieInfo> getMovieInfo(@PathVariable String id) {
        return movieInfoService.getById(id).log();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<MovieInfo> update(@RequestBody MovieInfo movieInfo) {
        return movieInfoService.update(movieInfo);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return movieInfoService.delete(id);
    }
}


package ro.omid.webflux.service;

import ro.omid.webflux.domain.MovieInfo;
import ro.omid.webflux.repository.MovieInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieInfoService {

    private final MovieInfoRepository repository;

    public Mono<MovieInfo> save(MovieInfo movieInfo) {
        return repository.save(movieInfo).log();
    }

    public Flux<MovieInfo> getAllMovies() {
        return repository.findAll();
    }

    public Mono<MovieInfo> getById(String id) {
        return repository.findById(id);
    }

    public Mono<MovieInfo> update(MovieInfo movieInfo) {
        return repository.findById(movieInfo.getMovieInfoId())
                .flatMap(movie -> {
                    movie.setName(movieInfo.getName());
                    movie.setCast(movieInfo.getCast());
                    movie.setYear(movieInfo.getYear());
                    movie.setReleaseDate(movieInfo.getReleaseDate());
                    return repository.save(movie);
                });
    }

    public Mono<Void > delete(String id) {
        return repository.deleteById(id);
    }

}



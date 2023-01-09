package ro.omid.webflux;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ro.omid.webflux.domain.MovieInfo;
import ro.omid.webflux.repository.MovieInfoRepository;

import java.time.LocalDate;
import java.util.List;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        List<MovieInfo> movieInfos = List.of(
                new MovieInfo(null, "West World", 2013, List.of("Antonie Hopkins", "Omid"), LocalDate.parse("2013-06-05")),
                new MovieInfo(null, "Dark", 2019, List.of("yousef", "Anna"), LocalDate.parse("2019-08-25")),
                new MovieInfo("abc", "Breaking Bad", 2010, List.of("jimi", "hizengard"), LocalDate.parse("2010-10-08"))
        );
        movieInfoRepository.saveAll(movieInfos).blockLast();
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void findAll() {
        Flux<MovieInfo> movieInfoFlux = movieInfoRepository.findAll().log();
        StepVerifier.create(movieInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        Mono<MovieInfo> movieInfoMono = movieInfoRepository.findById("abc").log();
        StepVerifier.create(movieInfoMono)
                .assertNext(movieInfo -> {
                    assert movieInfo.getName().equals("Breaking Bad");
                })
                .verifyComplete();
    }

    @Test
    void save() {
        MovieInfo movie = new MovieInfo(null, "Friends", 1991,
                List.of("jimi", "hizengard"), LocalDate.parse("2010-10-08"));

        Mono<MovieInfo> movieInfoMono = movieInfoRepository.save(movie).log();
        StepVerifier.create(movieInfoMono)
                .assertNext(movieInfo -> {
                    assert movieInfo.getName().equals("Friends") &&
                            movieInfo.getMovieInfoId() != null;
                })
                .verifyComplete();
    }

    @Test
    void update() {
        MovieInfo movie = movieInfoRepository.findById("abc").block();
        movie.setName("Bullet Train");
        Mono<MovieInfo> updatedMovie = movieInfoRepository.save(movie);
        StepVerifier.create(updatedMovie)
                .assertNext(movieInfo -> {
                    assert movieInfo.getName().equals("Bullet Train");
                })
                .verifyComplete();
    }

    @Test
    void delete() {
        movieInfoRepository.deleteById("abc").block();
        Flux<MovieInfo> allMovies = movieInfoRepository.findAll().log();
        StepVerifier.create(allMovies)
                .expectNextCount(2)
                .verifyComplete();
    }

}




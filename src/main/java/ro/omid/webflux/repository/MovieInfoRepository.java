package ro.omid.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ro.omid.webflux.domain.MovieInfo;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String> {
}

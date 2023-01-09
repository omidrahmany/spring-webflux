package ro.omid.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class MainController {

    @GetMapping("/flux")
    public Flux<Integer> getFlux() {
        return Flux.just(1, 2, 3).log();
    }

    @GetMapping("/mono")
    public Mono<String> getMono() {
        return Mono.just("Hello Spring Webflux").log();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> getStream() {
        return Flux.interval(Duration.ofSeconds(1)).log();
    }
}

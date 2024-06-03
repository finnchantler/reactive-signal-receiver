package com.finnc.services;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class MockStringPublisher {

    private final Flux<String> stringFlux;

    public MockStringPublisher(List<String> strings) {
        this.stringFlux = Flux.fromIterable(strings)
                .concatMap(string -> Flux.just(string).delayElements(Duration.ofSeconds(1)));
    }

    public Flux<String> getStringFlux() {
        return stringFlux;
    }
}

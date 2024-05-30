package com.finnc.receiver;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class MockStringPublisher {

    private final Flux<String> stringFlux;

    public MockStringPublisher(List<String> strings) {
        this.stringFlux = Flux.fromIterable(strings)
                .concatMap(string -> Flux.just(string).delayElements(Duration.ofSeconds(2)));
    }

    public Flux<String> getStringFlux() {
        return stringFlux;
    }

    public static void main(String[] args) {
        List<String> strings = List.of("String 1", "String 2", "String 3", "String 4", "String 5");

        MockStringPublisher stringPublisher = new MockStringPublisher(strings);
        stringPublisher.getStringFlux()
                .subscribe(System.out::println);
    }
}

package com.finnc.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SignalReceiver {

    public static void main(String[] args) {
        SignalReceiver receiver = new SignalReceiver();
        receiver.start();
        // Delay keeps the main thread alive
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Flux<String> messageFlux = readSignalOutput();

        messageFlux
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(this::parseMessage)
                .subscribe(
                        this::processMessage,
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Stream finished")
                );
    }

    public Flux<String> readSignalOutput() {
        return Flux.create(emitter -> {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "reactive-signal-testing.jar");
            try {
                Process process = processBuilder.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("Read line: " + line); // Debugging line
                        emitter.next(line);
                    }
                    emitter.complete(); // Complete only if successful
                } catch (IOException e) {
                    System.err.println("Error reading process output: " + e.getMessage()); // Debugging line
                    emitter.error(e); // Emit error if reading fails
                }
            } catch (IOException e) {
                System.err.println("Error starting process: " + e.getMessage()); // Debugging line
                emitter.error(e); // Emit error if process start fails
            }
        });
    }

    private Flux<String> parseMessage(String message) {
        System.out.println("parser received: " + message);
        return Flux.just(message);
    }

    private void processMessage(String message) {
        System.out.println("processor received: " + message);
    }
}
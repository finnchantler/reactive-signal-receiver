package com.finnc.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class SignalReceiverService {

    public SignalReceiverService() {
        System.out.println("signal-receiver-service started");
    }

    @PostConstruct
    public void start() {
        //Flux<String> messageFlux = readSignalOutput();
        Flux<String> messageFlux = readMockStringPublisher(); // for testing

        messageFlux
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(this::processMessage)
                .doOnError(error -> System.err.println("Error: " + error))
                .doOnComplete(() -> System.out.println("Stream finished"))
                .blockLast();
    }

    public Flux<String> readMockStringPublisher() {
        return new MockStringPublisher(List.of("menu", "order pizza, cake, McPlant to what.three.words", "status", "Hello?"))
                .getStringFlux();
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

    private void processMessage(String message) {
        System.out.println("parser received: " + message);
        String[] split = message.split(" ");

        if (split[0].equals("menu")) {
            // Get menu from API
        } else if (split[0].equals("order")) {

            String[] parts = message.split(" to ");
            if (parts.length != 2) {
                // Invalid format, send a message back to the user
            }
            String[] orderItems = parts[0].substring(6).split(", ");
            String orderString = "ORDER: " + Arrays.toString(orderItems) + " deliver to: " + parts[1];
            System.out.println(orderString);


        } else if (split[0].equals("status")) {
            // Calls API for shop status (online, offline, back in 5 etc)
        } else {
            // Handles non-command messaging, forwarding them to admin for reply
        }
    }
}
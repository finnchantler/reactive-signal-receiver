package com.finnc;

import com.finnc.receiver.SignalReceiverService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SignalReceiverApplication {

    private final SignalReceiverService signalReceiverService;

    public SignalReceiverApplication(SignalReceiverService signalReceiverService) {
        this.signalReceiverService = signalReceiverService;
    }

    public static void main(String[] args) {

        SpringApplication.run(SignalReceiverApplication.class, args);
    }

    @Bean
    CommandLineRunner run() {
        return args -> {
            signalReceiverService.start();
        };
    }

}

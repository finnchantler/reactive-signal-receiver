package com.finnc;

import com.finnc.services.SignalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SignalReceiverApplication {

    private final SignalService signalReceiverService;

    public SignalReceiverApplication(SignalService signalReceiverService) {
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

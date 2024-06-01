package com.finnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SignalReceiverApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignalReceiverApplication.class, args);
    }

}

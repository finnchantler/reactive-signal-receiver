package com.finnc.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.finnc.models.Customer;
import com.finnc.models.MenuItem;
import com.finnc.models.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class SignalService {

    private final StorageService storageService;

    @Autowired
    public SignalService(StorageService storageService) {
        this.storageService = storageService;
        System.out.println("signal-receiver-service initialized");
    }

    @Async
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
        return new MockStringPublisher(List.of("order", "order pizza, cake, McPlant t what.three.words", "order padThai to", "order chicken to hello.three.words"))
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
        //System.out.println("parser received: " + message);

        String[] messageArray = message.split(" ");

        if (messageArray[0].equals("menu")) {
            // Get menu from API
        } else if (messageArray[0].equals("order")) {

            String result = processOrder(message);
            System.out.println(result);

        } else if (messageArray[0].equals("status")) {
            // Calls API for shop status (online, offline, back in 5 etc)
        } else {
            // Handles non-command messaging, forwarding them to admin for reply
        }
    }

    private String processOrder(String orderString) {
        System.out.println("processOrder received: " + orderString);

        String result = "order format: 'order list, of, items to what.three.words'";

        String[] orderParts = orderString.split(" to ");
        if (orderParts.length == 2) {
            String[] orderItems = orderParts[0].substring(6).split(", ");
            String deliveringTo = orderParts[1];

            // Create a customer entry
            Customer testCustomer = new Customer("finn");
            storageService.storeCustomer(testCustomer);

            String customerName = "finn"; // get this from signal-cli eventually

            List<Customer> matchingCustomers = storageService.searchCustomersByName(customerName);

            if (!matchingCustomers.isEmpty()) {
                // Customer name is valid
                List<MenuItem> menuItems = storageService.getAllMenuItems();
                int validItems = 0;
                for (String orderItem : orderItems) {
                    // Loop through items in order, check against menuItems, and check stock
                    for (MenuItem menuItem : menuItems) {
                        if (menuItem.getName().equals(orderItem) && menuItem.getStock() > 0) {
                            validItems++;
                        }
                    }
                }

                if (validItems == orderItems.length) {
                    Orders order = new Orders(matchingCustomers.getFirst(), orderItems, deliveringTo);
                    result = storageService.storeOrder(order);
                } else {
                    result = "one or more items were not found or were out of stock";
                }
            }
        }

        return result;
    }
}
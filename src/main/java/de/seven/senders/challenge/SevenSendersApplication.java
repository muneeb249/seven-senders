package de.seven.senders.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SevenSendersApplication {

    public static void main(String [] args) {
        SpringApplication.run(SevenSendersApplication.class, args);
    }

}

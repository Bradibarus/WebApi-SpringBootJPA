package Bradibarus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(EntryRepository entryRepository) {
        return (evt) -> {
            entryRepository.save(new Entry(new Date(1384651210), new ArrayList<Data>(){{add(new Data("key", 33 ));}}));
        };
    }
}

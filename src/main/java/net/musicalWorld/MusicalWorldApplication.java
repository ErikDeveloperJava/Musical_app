package net.musicalWorld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:file-config.properties")
public class MusicalWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicalWorldApplication.class, args);
    }
}

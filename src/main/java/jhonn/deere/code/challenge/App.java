package jhonn.deere.code.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"jhonn.deere.code.challenge.services",
        "jhonn.deere.code.challenge.controllers"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}

package com.bol.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Application configuration for the Eureka server app. Launch
 * the app by running the main method.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer {

    /**
     * Main method used to launch the application. Configuration located
     * in application.yml
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class, args);
    }
}

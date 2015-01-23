package com.bol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.FeignClientScan;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication  // Spring boot application
@EnableDiscoveryClient  // Enables the discovery client to connect with Eureka
@EnableCircuitBreaker   // Enables hystrix circuit breaker support (Support for @HystrixCommand annotations)
@EnableHystrixDashboard // Enables the hystrix dashboard
@EnableRetry            // Enables Spring retry
@FeignClientScan        // Scans the classpath for @FeignClient annotated classes, to wire them in when requested
public class TransporterDemoApp {

    /**
     * Register a protobuf messageconverter so we'll be able to deal with protobuf services.
     *
     * @return a protobuf message converter. Spring boot will handle the registration of this converter.
     */
    @Bean
    public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    /**
     * Application launcher, starts the client and its endpoint for demo use.
     */
    public static void main(final String[] args) {
        // Start the application
        SpringApplication.run(TransporterDemoApp.class, args);
    }
}
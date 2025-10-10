package org.example.cuidadodemascotas.servicemicroservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class})
@ComponentScan(basePackages = {
        "org.example.cuidadodemascotas.servicemicroservice",
        "org.example.cuidadodemascota.commons.entities"
})
public class ServiceMicroserviceApplication {

    public static void main(String[] args) {
        log.info("========================================");
        log.info("Starting Service Microservice...");
        log.info("========================================");

        SpringApplication.run(ServiceMicroserviceApplication.class, args);

        log.info("========================================");
        log.info("Service Microservice Started Successfully!");
        log.info("Swagger UI: http://localhost:8083/api/v1/swagger-ui.html");
        log.info("API Docs: http://localhost:8083/api/v1/api-docs");
        log.info("========================================");
    }
}
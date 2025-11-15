package org.example.cuidadodemascotas.servicemicroservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class})
@EnableDiscoveryClient
@ComponentScan(basePackages = {
        "org.example.cuidadodemascotas.servicemicroservice",
        "org.example.cuidadodemascota.commons.entities"
})
public class ServiceMicroserviceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        log.info("========================================");
        log.info("Starting Service Microservice...");
        log.info("========================================");

        SpringApplication.run(ServiceMicroserviceApplication.class, args);

        log.info("========================================");
        log.info("Service Microservice Started Successfully!");
        log.info("Swagger UI: http://localhost:8083/swagger-ui.html");
        log.info("API Docs: http://localhost:8083/api-docs");
        log.info("========================================");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServiceMicroserviceApplication.class);
    }
}
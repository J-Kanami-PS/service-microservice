package org.example.cuidadodemascotas.servicemicroservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8083}")
    private String serverPort;

    @Bean
    public OpenAPI serviceMicroserviceOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:" + serverPort + "/api/v1");
        server.setDescription("Service Microservice Local Server");

        Contact contact = new Contact();
        contact.setName("Jazmín Kanami Pavón Shiokawa");
        contact.setEmail("jazmin.pavon@example.com");

        License license = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        Info info = new Info()
                .title("Cuidado de Mascotas - Service Microservice API")
                .version("1.0.0")
                .description("Microservicio encargado de la gestión de servicios y tipos de servicios. " +
                        "Permite crear, actualizar, listar y eliminar servicios ofrecidos por cuidadores.")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
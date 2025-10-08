package org.example.cuidadodemascotas.servicemicroservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI serviceMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cuidado de Mascotas - Service Microservice API")
                        .description("Microservicio encargado de la gestión de servicios y tipos de servicios.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Jazmín Kanami Pavón Shiokawa")
                                .email("jazmin@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación completa del proyecto")
                        .url("https://github.com/jazminkanami/CuidadoDeMascotas"));
    }
}

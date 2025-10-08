package org.example.cuidadodemascotas.servicemicroservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.example.cuidadodemascota.commons.entities")
@EnableJpaRepositories(basePackages = "org.example.cuidadodemascotas.servicemicroservice.apis.repository")
public class JpaConfig {
}
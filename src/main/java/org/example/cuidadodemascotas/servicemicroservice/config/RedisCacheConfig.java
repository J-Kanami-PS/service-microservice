package org.example.cuidadodemascotas.servicemicroservice.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

        // Serializador JSON que usa el ObjectMapper configurado
        RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        // Configuración base global
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // TTL global (10 minutos)
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));

        // Configuración específica por dominio
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // Servicios (TTL 15 min)
        cacheConfigurations.put("services",
                defaultCacheConfig.entryTtl(Duration.ofMinutes(15)).prefixCacheNameWith("services::"));

        // Tipos de servicio (TTL infinito)
        cacheConfigurations.put("service_types",
                defaultCacheConfig.entryTtl(Duration.ZERO).prefixCacheNameWith("services::"));

        // Usuarios con sus servicios (TTL 10 min)
        cacheConfigurations.put("service_carers",
                defaultCacheConfig.entryTtl(Duration.ofMinutes(10)).prefixCacheNameWith("services::"));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .transactionAware()
                .build();
    }
}
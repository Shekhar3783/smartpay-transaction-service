//package com.smartpay.transactionservice.config;
//
//import org.springframework.context.annotation.Configuration;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.cache.CacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//
//import java.time.Duration;
//
//@Configuration
//public class RedisConfig {
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory,
//                                     ObjectMapper objectMapper) {
//
//        System.out.println("******** USING CUSTOM CACHE MANAGER ********");
//        GenericJackson2JsonRedisSerializer serializer =
//                new GenericJackson2JsonRedisSerializer(objectMapper);
//
//        RedisCacheConfiguration configuration =
//                RedisCacheConfiguration.defaultCacheConfig()
//                        .entryTtl(Duration.ofMinutes(10))
//                        .serializeValuesWith(
//                                RedisSerializationContext.SerializationPair
//                                        .fromSerializer(serializer));
//
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(configuration)
//                .build();
//    }
//}

package com.tttm.Whear.App.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@EnableTransactionManagement
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(
                List.of(
                        "user", "users",
                        "follower", "following",
                        "customer", "customers",
                        "post", "posts",
                        "collection", "collections"
                )
        );
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(50)
                .weakKeys()
                .recordStats()
                .expireAfterAccess(30, TimeUnit.MINUTES));
        return cacheManager;
    }
}

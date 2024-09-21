package glebkr.task.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import glebkr.task.dto.TaskAnalyticsDTO;
import glebkr.task.dto.TaskDTO;

@Configuration
public class CacheConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
        return mapper;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<TaskDTO> taskDtoSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper(), TaskDTO.class);

        Jackson2JsonRedisSerializer<List<TaskDTO>> taskDtoListSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper(), objectMapper().getTypeFactory()
                        .constructCollectionType(List.class, TaskDTO.class));

        Jackson2JsonRedisSerializer<List<TaskAnalyticsDTO>> taskAnalyticsDtoListSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper(), objectMapper().getTypeFactory()
                        .constructCollectionType(List.class, TaskAnalyticsDTO.class));

        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(20))
                .disableCachingNullValues();

        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration("task", cacheConfig
                        .serializeValuesWith(RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(taskDtoSerializer)))
                .withCacheConfiguration("tasks", cacheConfig
                        .serializeValuesWith(RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(taskDtoListSerializer)))
                .withCacheConfiguration("taskAnalytics", cacheConfig
                        .serializeValuesWith(RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(taskAnalyticsDtoListSerializer)));;

        return builder.build();
    }

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> target.getClass().getName() + "_" +
                method.getName() + "_" +
                Arrays.toString(params);
    }


}
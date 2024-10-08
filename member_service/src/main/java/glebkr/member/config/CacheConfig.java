package glebkr.member.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;

import glebkr.member.dto.MemberDTO;

@Configuration
public class CacheConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return mapper;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<MemberDTO> taskDTOSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper(), MemberDTO.class);

        Jackson2JsonRedisSerializer<List<MemberDTO>> listSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper(), objectMapper().getTypeFactory()
                        .constructCollectionType(List.class, MemberDTO.class));

        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(20))
                .disableCachingNullValues();

        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration("member", cacheConfig
                        .serializeValuesWith(RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(taskDTOSerializer)))
                .withCacheConfiguration("members", cacheConfig
                        .serializeValuesWith(RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(listSerializer)));

        return builder.build();
    }

}

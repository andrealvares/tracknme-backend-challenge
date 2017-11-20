package br.com.tracknme.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by Cleberson on 18/11/2017.
 */
@Configuration
public class RedisContextTest {

    @Bean
    public StringRedisSerializer stringSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public GenericJackson2JsonRedisSerializer jsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setDatabase(3);
        return jedisConnectionFactory;
    }

    @Bean(name="redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(stringSerializer());
        redisTemplate.setHashKeySerializer(stringSerializer());
        redisTemplate.setValueSerializer(jsonRedisSerializer());
        return redisTemplate;
    }
}

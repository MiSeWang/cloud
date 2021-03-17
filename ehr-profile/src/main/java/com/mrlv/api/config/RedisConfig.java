package com.mrlv.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //重新自定义Redis配置类 重写了 RedisAutoConfiguration类中的redisTemplate方法。
        //配置序列化方式: Key序列化为String，Value序列化为JSON（默认使用Jackson）
        //设置key序列化方式string
        template.setKeySerializer(new StringRedisSerializer());
        //设置key序列化方式string
        template.setHashKeySerializer(new StringRedisSerializer());
        //设置hash key序列化方式string
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //设置hash value的序列化方式json
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }
}

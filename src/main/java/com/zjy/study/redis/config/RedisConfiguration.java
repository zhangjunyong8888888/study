package com.zjy.study.redis.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/**
 * Redis 配置类
 *
 * @author Leon
 * @version 2018/6/17 17:46
 */
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

    /**
     * Logger
     */
    private static final Logger lg = LoggerFactory.getLogger(RedisConfiguration.class);

    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        ParserConfig.getGlobalInstance().addAccept("com.study.redis");
        //return new FastJson2JsonRedisSerializer<>(Object.class);
        return new FastJsonRedisSerializer(Object.class);
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(RedisSerializer fastJson2JsonRedisSerializer) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJson2JsonRedisSerializer)).entryTtl(Duration.ofDays(30));
        return configuration;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory, RedisCacheConfiguration redisCacheConfiguration) {

        return RedisCacheManager.builder(factory)
                .cacheDefaults(redisCacheConfiguration)
                .transactionAware()
                .build();
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer() {
        return cacheManager -> cacheManager.setAllowNullValues(true);
    }


    @Bean
    public RedisTemplate initRedisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer fastJson2JsonRedisSerializer) {
        RedisTemplate redisTemplate = new RedisTemplate();
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}


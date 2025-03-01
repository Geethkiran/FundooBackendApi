package com.fundooproject.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * This class contains configuration of Redis cache.Here we are setting Redis
 * Host,Redis port,Redis password.
 * 
 * @author
 *
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	@Value("${spring.redis.host}")
	private String redisHostName;
	@Value("${spring.redis.port}")
	private int redisPort;

	@Bean
	protected JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHostName, redisPort);
		configuration.setPassword(RedisPassword.of("password"));
		JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().build();
		JedisConnectionFactory factory = new JedisConnectionFactory(configuration, jedisClientConfiguration);
		factory.afterPropertiesSet();
		return factory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new GenericToStringSerializer<Object>(Object.class));
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}
}
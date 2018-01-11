package com.his.cache.redis.config;

import com.his.cache.redis.RedisDataSource;
import com.his.cache.redis.RedisTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JedisConfiguration {

	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.port}")
	private int port;
	
	@Value("${spring.redis.password}")
	private String password;
	
	@Value("${spring.redis.pool.max-idle}")
	private int maxIdle;
	
	@Value("${spring.redis.pool.max-active}")
	private int maxTotal;
	
	@Value("${spring.redis.pool.max-wait}")
	private int maxWaitMillis;
			
	@Value("${spring.redis.timeout}")
	private int timeout;
	
	@Bean
	public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		return jedisPoolConfig;
	}
	
	@Bean
	public JedisShardInfo jedisShardInfo(){
		JedisShardInfo jedisPoolConfig = new JedisShardInfo(host, port);
		jedisPoolConfig.setPassword(password);
		jedisPoolConfig.setSoTimeout(timeout);
		return jedisPoolConfig;
	}
	
	@Bean
	public ShardedJedisPool shardedJedisPool(){
		List<JedisShardInfo> jedisShardInfos = new ArrayList<JedisShardInfo>();
		jedisShardInfos.add(jedisShardInfo());
		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(jedisPoolConfig(), jedisShardInfos);
		return shardedJedisPool;
	}
	
	@Bean
	public RedisDataSource redisDataSource(){
		RedisDataSource redisDataSource = new RedisDataSource();
		redisDataSource.setShardedJedisPool(shardedJedisPool());
		redisDataSource.setName("sdffs");
		return redisDataSource;
	}
	
	
	@Bean
	public RedisTemplate redisTemplate(){
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setRedisDataSource(redisDataSource());
		return redisTemplate;
	}

}

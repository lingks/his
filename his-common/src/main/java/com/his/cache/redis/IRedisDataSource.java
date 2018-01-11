package com.his.cache.redis;
import redis.clients.jedis.ShardedJedis;

public interface IRedisDataSource {
    public abstract ShardedJedis getRedisClient();
    public void returnResource(ShardedJedis shardedJedis);
    public void returnResource(ShardedJedis shardedJedis, boolean broken);
}
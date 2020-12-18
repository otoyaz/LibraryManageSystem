package com.bokehai.book.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

    private static String redisHost = "127.0.0.1";
    private static int redisPort = 6379;
    private static JedisPool jedisPool;
    /**
     * 好的方式是配置到文件
     **/
    private static int jedisPoolMinIdle = 50;
    private static int jedisPoolMaxTotal = 100;

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最小空闲连接数
        poolConfig.setMinIdle(jedisPoolMinIdle);
        // 最大连接数
        poolConfig.setMaxTotal(jedisPoolMaxTotal);
        // 尝试从pool中获取多长时间失败
        poolConfig.setMaxWaitMillis(3 * 1000);
        // 在获取连接的时候检查有效性
        poolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool(poolConfig, redisHost, redisPort, 30000);
    }


    /**
     * 通过jedis连接池获取jedis对象
     * @return
     */
    public static Jedis getJedisFromPool() {
        return jedisPool.getResource();
    }
}

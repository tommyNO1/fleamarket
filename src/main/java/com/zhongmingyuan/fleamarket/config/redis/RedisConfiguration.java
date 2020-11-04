package com.zhongmingyuan.fleamarket.config.redis;

import com.zhongmingyuan.fleamarket.cache.JedisPoolWriper;
import com.zhongmingyuan.fleamarket.cache.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {
    @Value("${redis.hostname}")
    private String  hostname;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.maxTotal}")
    private int maxTotal;
    @Value("${redis.maxIdle}")
    private int maxIdle;
    @Value("${redis.maxWaitMillis}")
    private long maxWaitMillis;
    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;



    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    @Autowired
    private JedisPoolWriper jedisWritePool;

    @Autowired
    private JedisUtil jedisUtil;


    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig JedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        return jedisPoolConfig;
    }

    @Bean(name = "jedisWritePool")
    public JedisPoolWriper jedisWritePool(){
        return new JedisPoolWriper(jedisPoolConfig,hostname,port);
    }


    @Bean(name = "jedisUtil")
    public JedisUtil jedisUtil(){
        JedisUtil jedisUtil = new JedisUtil();
        jedisUtil.setJedisPool(jedisWritePool);
        return jedisUtil;
    }

    @Bean(name = "jedisKeys")
    public JedisUtil.Keys jedisKeys(){
        return jedisUtil.new Keys();
    }

    @Bean(name = "jedisStrings")
    public JedisUtil.Strings jedisStrings(){
        return jedisUtil.new Strings();
    }
}

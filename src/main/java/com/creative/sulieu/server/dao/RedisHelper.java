package com.creative.sulieu.server.dao;

import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisHelper {
  public RedisHelper(){
    logger.setLevel(Level.DEBUG);
    ResourceBundle rb = ResourceBundle.getBundle("conf.configuration");
    JedisPoolConfig redisConfig = new JedisPoolConfig();
    redisConfig.setTestOnBorrow(false);
    redisConfig.setTestOnCreate(false);
    redisConfig.setTestOnReturn(false);
    redisConfig.setMaxTotal(200);
    if("".equals(rb.getString("redis.password")))
      redisPool = new JedisPool(redisConfig, rb.getString("redis.server"), Integer.parseInt(rb.getString("redis.port")),500);
    else
      redisPool = new JedisPool(redisConfig, rb.getString("redis.server"), Integer.parseInt(rb.getString("redis.port")),500,rb.getString("redis.password"));
  }
  public JedisPool redisPool;
  final static Logger logger = Logger.getLogger(RedisHelper.class);

  public boolean exist(String domain, String key){
    Jedis redisServer = redisPool.getResource();
    boolean result = redisServer.sismember(key, key);
    redisServer.close();
    return result;
  }

  public Set<String> getAll(String domain) {
    Set<String> result = null;
    Jedis redisServer = redisPool.getResource();
    result = redisServer.smembers(domain);
    redisServer.close();
    return result;
  }
  
  public byte[] getByteArr(String domain, String key){
    byte[] result = new byte[]{};
    Jedis redisServer = redisPool.getResource();
    //if(redisServer.sismember(domain, key))
      result = redisServer.get(key.getBytes());
    redisServer.close();
    return result;
  }

  public void insertByteArr(String domain, String key, byte[] data){
    Jedis redisServer = redisPool.getResource();
    redisServer.set((key).getBytes(), data);
    redisServer.sadd(domain, key);
    redisServer.close();
  }

  public void deleteKey(String domain, String key){
    Jedis redisServer = redisPool.getResource();
    redisServer.del(key);
    redisServer.srem(domain, key);
    redisServer.close();
  }

  public Jedis getRedisServer() {
    return redisPool.getResource();
  }
}

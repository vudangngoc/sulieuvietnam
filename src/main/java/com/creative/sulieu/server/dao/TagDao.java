package com.creative.sulieu.server.dao;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

public class TagDao {
  private RedisHelper rh = new RedisHelper();
  private String domain = "TAG";
  public boolean save(Tag tag) {
    Jedis redisServer = rh.getRedisServer();
    redisServer.sadd(tag.getName(), tag.getRelativeItems());
    redisServer.sadd(domain, tag.getName());
    redisServer.close();
    return true;
  }
  
  public Tag get(String name) {
    Tag result = new Tag();String[] items = {};
    Jedis redisServer = rh.getRedisServer();
    redisServer.smembers(name).toArray(items);
    redisServer.close();
    result.setName(name);
    result.setRelativeItems(items);
    return result;
  }
  
  public boolean delete(String name) {
    boolean result = true;
    Jedis redisServer = rh.getRedisServer();
    result = redisServer.del(name) != 0;
    redisServer.close();
    return result;
  }
  
  public boolean move(String src, String des, String id) {
    boolean result = true;
    
    return result;
  }

  public List<Tag> getAll() {
    String[] tags = {};
    ArrayList<Tag> result = new ArrayList<>();
    Jedis redisServer = rh.getRedisServer();
    redisServer.smembers(domain).toArray(tags);
    redisServer.close();
    for(String tag : tags)
      result.add(get(tag));
    return result;
  }
}

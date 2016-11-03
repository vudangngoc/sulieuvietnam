package com.creative.sulieu.server.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class TagDao {
  private static TagDao singleton = new TagDao();
  public static TagDao getObject(){
    return singleton ;
  }
  private RedisHelper rh = new RedisHelper();
  private String domain = "TAG";
  public boolean save(Tag tag, String itemId) {
    Jedis redisServer = rh.getRedisServer();
    redisServer.sadd(tag.getName(), itemId);
    redisServer.sadd(domain, tag.getName());
    redisServer.close();
    return true;
  }
  
  public Tag get(String name) {
    Tag result = new Tag();
    Set<String> items = new HashSet<>();
    Jedis redisServer = rh.getRedisServer();
    if(!redisServer.sismember(domain, name)) {
      redisServer.close();
      return result;
    }
    items = redisServer.smembers(name);
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
    Jedis redisServer = rh.getRedisServer();
    if(!redisServer.sismember(domain, src)
        ||!redisServer.sismember(domain, des)
        ||!redisServer.sismember(src, id)
        ||redisServer.sismember(des, id)){
      redisServer.close();
      return false;
    }
    redisServer.srem(src, id);
    redisServer.sadd(des, id);
    redisServer.close();
    return true;
  }
  
  public Set<String> combine(String[] taglist){
    Jedis redisServer = rh.getRedisServer();
    return redisServer.sinter(taglist);
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

  public void addItem(String tagId, String itemId) {
    Jedis redisServer = rh.getRedisServer();
    if(!redisServer.sismember(domain, tagId))
      redisServer.sadd(domain, tagId);
    redisServer.sadd(tagId, itemId);
    redisServer.close();
    
  }
  public void remove(String tagId, String itemId) {
    Jedis redisServer = rh.getRedisServer();
    if(!redisServer.sismember(domain, tagId))
      return;
    redisServer.srem(tagId, itemId);
    redisServer.close();
    
  }
}

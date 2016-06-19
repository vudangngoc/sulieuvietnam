package com.creative.sulieu.server.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import org.json.JSONObject;

public class Session extends Object {
  public Session() {
    this(UUID.randomUUID().toString().replace("-", "")+ UUID.randomUUID().toString().replace("-", ""));
  }
  public Session(String id) {
    this.id = id;
  }
  //private HashMap<String,String> data = new HashMap<>();
  private String userName = "";
  private long lastActive;
  private String id;
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public long getLastActive() {
    return lastActive;
  }
  public void setLastActive(long lastActive) {
    this.lastActive = lastActive;
  }
  public String getId() {
    return id;
  }
  @Override
  public boolean valid(JSONObject object) {
    // TODO Auto-generated method stub
    return false;
  }

  
}

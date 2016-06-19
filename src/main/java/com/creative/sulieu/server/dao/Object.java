package com.creative.sulieu.server.dao;

import java.util.UUID;

import org.json.JSONObject;

public abstract class Object {
  public Object() {
    this(UUID.randomUUID().toString().replace("-", "")+ UUID.randomUUID().toString().replace("-", ""));
  }
  public Object(String id) {
    data = getDefault();
    setId(id);
  }
  protected void setId(String id) {
    if(data != null) data.put("id", id);
  }
  protected String id;
  protected JSONObject data;

  public JSONObject getData() {
    return data;
  }

  public void setData(JSONObject data) {
    if(this.valid(data))
      this.data = data;
  }
  protected JSONObject getDefault() {
    JSONObject result = new JSONObject();
    return result;
  }
  @Override
  public String toString() {return getData().toString();}
  public abstract boolean valid(JSONObject object);
}

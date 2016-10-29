package com.creative.sulieu.server.dao;

import java.util.UUID;

import org.json.JSONObject;

public abstract class Object {
  public static String ID = "id";
  public Object() {
    this(UUID.randomUUID().toString().replace("-", "")+ UUID.randomUUID().toString().replace("-", ""));
  }
  public Object(String id) {
    data = getDefault();
    setId(id);
  }
  public void setId(String id) {
    if(data != null) data.put("id", id);
    this.id = id;
  }
  protected String id;
  protected JSONObject data;
  public String getId() {
    String result = "";
    result = getData().getString(ID);
    if(!"".equals(result))
      return getData().getString(ID);
    else
      return this.id;
  }
  public JSONObject getData() {
    return data;
  }
  
  public String getProperty(String propertyName){
    if(data.has(propertyName))
      return data.getString(propertyName);
    else
      return "";
  }

  public boolean setData(JSONObject data) {
    if(this.valid(data)){
      this.data = data;
      return true;
    }else{
      return false;
    }
  }
  protected JSONObject getDefault() {
    JSONObject result = new JSONObject();
    return result;
  }
  @Override
  public String toString() {return getData().toString();}
  public abstract boolean valid(JSONObject object);
}

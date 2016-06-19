package com.creative.sulieu.server.dao;

import org.json.JSONObject;

public class Tag extends Object{
  private String name = "";
  private String[] relativeItems;
  public String[] getRelativeItems() {
    return relativeItems;
  }
  public void setRelativeItems(String[] relativeItems) {
    this.relativeItems = relativeItems;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public boolean valid(JSONObject data) {
    return data.has("name")&&data.has("item");
  }
}

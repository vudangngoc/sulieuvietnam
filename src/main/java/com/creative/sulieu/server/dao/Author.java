package com.creative.sulieu.server.dao;

import org.json.JSONObject;

public class Author extends Object {

  public Author(String id) {
    super(id);
  }
  public Author() {
    super();
  }
  private String id;
  private String name = "";
  private int yob = 0;
  private String comment = "";
  public static String ID = "id";
  public static String NAME = "name";
  public static String YOB = "yob";
  public static String COMMENT = "comment";
  public String getName() {
    return getData().getString(NAME);
  }
  public void setName(String name) {
    getData().put(NAME, name);
  }
  public int getYob() {
    return getData().getInt(YOB);
  }
  public void setYob(int yob) {
    getData().put(YOB, yob);
  }
  public String getComment() {
    return getData().getString(COMMENT);
  }
  public void setDescription(String comment) {
    getData().put(COMMENT, comment);
  }
  
  public String getId() {
    String result = "";
    result = getData().getString(ID);
    if(!"".equals(result))
      return getData().getString(ID);
    else
      return this.id;

  }
  
  public void setId(String id) {
    getData().put(ID, id);
    this.id = id;
  }
  @Override
  public boolean valid(JSONObject object) {
    // TODO Auto-generated method stub
    return object.has(COMMENT)&&object.has(ID)&&object.has(NAME)&&object.has(YOB);
  }

}

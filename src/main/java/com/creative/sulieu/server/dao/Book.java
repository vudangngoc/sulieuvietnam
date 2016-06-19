package com.creative.sulieu.server.dao;

import org.json.JSONObject;

public class Book extends Object {
  private static String ID = "id";
  private String id ;
  private static String authorId = "authorId";
  private static String bookName = "bookName";
  private static String releaseYear = "releaseYear";
  public String getAuthorId() {
    return getData().getString(id);
  }
  public void setAuthorId(String Id) {
    getData().put(authorId, Id);
  }
  public String getName() {
    return getData().getString(bookName);
  }
  public void setName(String name) {
    getData().put(bookName, name);
  }
  public int getReleaseYear() {
    return getData().getInt(releaseYear);
  }
  public void setReleaseYear(int year) {
    getData().put(releaseYear, year);
  }

  public String getId() {
    if(getData().has(ID)) 
      return getData().getString(ID);
    else
      return id;
  }
  public Book(String id) {
    super(id);
  }
  public Book() {
    super();
  }
  @Override
  public boolean valid(JSONObject object) {
    return object.has(ID)&&object.has(releaseYear)&&object.has(authorId)&&object.has(bookName);
  }
}

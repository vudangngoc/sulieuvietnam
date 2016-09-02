package com.creative.sulieu.server.dao;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

public class Book extends Object {
  private static String ID = "id";
  private String id ;
  private static String authorId = "authorId";
  private static String bookName = "bookName";
  private static String releaseYear = "releaseYear";

  public List<String> getAuthorIds() {
    List<String> list = new LinkedList<String>();
    Iterator<java.lang.Object> i = getData().getJSONArray(authorId).iterator();
    while(i.hasNext())
      list.add(i.next().toString());
    return list;
  }
  public void setAuthorId(String Id) {
    if(!getData().has(authorId))
      getData().put(authorId, new LinkedList<>());
    getData().getJSONArray(authorId).put(Id);
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

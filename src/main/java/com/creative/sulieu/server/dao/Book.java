package com.creative.sulieu.server.dao;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

public class Book extends Object {
  private static String ID = "id";
  private String id ;
  private static String authorId = "authorId";
  private static String BOOK_NAME = "bookName";
  private static String RELEASE_YEAR = "releaseYear";
  private static String TRANSLATOR = "translator";
  private static String PUBLISHER = "publisher";
  private static String PUBLISH_YEAR = "publishYear";

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
    return getData().getString(BOOK_NAME);
  }
  public void setName(String name) {
    getData().put(BOOK_NAME, name);
  }
  public int getReleaseYear() {
    return getData().getInt(RELEASE_YEAR);
  }
  public void setReleaseYear(int year) {
    getData().put(RELEASE_YEAR, year);
  }
  public int getPublisherYear() {
    String result = getProperty(PUBLISH_YEAR);
    try{
      return Integer.parseInt(result);
    }catch(Exception e) {
      return 0;
    }
  }
  public void setPublisherYear(int year) {
    getData().put(PUBLISH_YEAR, year);
  }
  public String getTranslator() {
    return getProperty(TRANSLATOR);
  }
  public void setTranslator(String translator) {
    getData().put(TRANSLATOR, translator);
  }
  public String getPublisher() {
    return getProperty(PUBLISHER);
  }
  public void setPublisher(String publisher) {
    getData().put(PUBLISHER, publisher);
  }
  public Book(String id) {
    super(id);
  }
  public Book() {
    super();
  }
  @Override
  public boolean valid(JSONObject object) {
    return object.has(ID)&&object.has(RELEASE_YEAR)&&object.has(authorId)&&object.has(BOOK_NAME);
  }
}

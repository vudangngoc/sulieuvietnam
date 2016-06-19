package com.creative.sulieu.server.dao;

public class BookDao extends ObjectDao<Book>{
  protected String domain = "BOOK";
  protected String getDomain() {return this.domain;}
  @Override
  protected  Class<Book> getHandleClass(){
    return Book.class;
  }
}

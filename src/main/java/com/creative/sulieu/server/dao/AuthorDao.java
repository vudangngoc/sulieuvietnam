package com.creative.sulieu.server.dao;

public class AuthorDao extends ObjectDao<Author>{
  protected String domain = "AUTHOR";
  @Override
  protected String getDomain() {return this.domain;}
  protected Class<Author> getHandleClass(){
    return Author.class;
  }
}

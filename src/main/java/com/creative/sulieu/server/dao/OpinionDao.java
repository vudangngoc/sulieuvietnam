package com.creative.sulieu.server.dao;

public class OpinionDao extends ObjectDao<Opinion> {
  protected String domain = "OPINION_ITEM";
  protected String getDomain() {return this.domain;}
  @Override
  protected  Class<Opinion> getHandleClass(){
    return Opinion.class;
  }
}

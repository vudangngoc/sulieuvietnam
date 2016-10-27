package com.creative.sulieu.server.dao;

import org.json.JSONObject;

public class Opinion extends Object{
  private String opinion = "";
  private String authorId = "";
  private String bookid = "";
  private String description = "";
  private String id;
  public static String ID = "id";
  public static String OPPINION = "oppinion";
  public static String DESCRIPTION = "description";
  public static String BOOK_ID = "bookId";
  public static String AUTHOR_ID = "authorId";
  @Override
  public boolean valid(JSONObject object) {
    return object.has(BOOK_ID)&&object.has(ID)&&object.has(OPPINION)&&object.has(AUTHOR_ID);
  }
  public String getAuthorId() {
    if("".equals(authorId))
      this.authorId = getData().getString(AUTHOR_ID);
    return authorId;
  }
  public void setAuthorId(String authorId) {
    this.authorId = authorId;
    getData().put(AUTHOR_ID, authorId);
  }
  public String getOpinion() {
    if("".equals(this.opinion))
      this.opinion = getData().getString(OPPINION);
    return opinion;
  }
  public void setOpinion(String opinion) {
    this.opinion = opinion;
    getData().put(OPPINION, opinion);
  }
  public String getBookid() {
    if("".equals(bookid))
      this.bookid = getData().getString(BOOK_ID);
    return bookid;
  }
  public void setBookid(String bookid) {
    this.bookid = bookid;
    getData().put(BOOK_ID, bookid);
  }
  public String getDescription() {
    if("".equals(description))
      this.description = getData().getString(DESCRIPTION);
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
    getData().put(DESCRIPTION, description);
  }
}

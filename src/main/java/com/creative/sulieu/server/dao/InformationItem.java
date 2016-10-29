package com.creative.sulieu.server.dao;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class InformationItem extends Object{
  final static Logger logger = Logger.getLogger(InformationItem.class);
  DateFormat df = new SimpleDateFormat("yyyy-MMM-dd HH-mm-ss");
  public InformationItem(String id) {
    super(id);
  }
  public InformationItem() {
    super();
  }
  public String getInfo() {
    if("".equals(this.info))
      this.info = getData().getString(INFO);
    return this.info;
  }
  public void setInfo(String info) {
    getData().put(INFO, info);
    this.info = info;
  }

  public String getLunarDate() {
    return getData().getString(LUNAR_DATE);
  }
  public void setLunarDate(String lunarYear) {
    getData().put(LUNAR_DATE, lunarYear);
  }

  public Date getDate() {
    Date result = new Date();
    try {
      result = df.parse(getData().getString(DATE));
    } catch (JSONException | ParseException e) {
      logger.debug(e);
    }
    return result;
  }
  public void setDate(Date date) {
    
    getData().put(DATE, df.format(date));
  }
  public Set<String> getTags() {
    if(this.tags.isEmpty())
      this.tags.addAll(Arrays.asList(getData().getJSONArray(TAGS).join("|").split("|")));
    return  this.tags;
  }
  
  public String getTitle(){
    if(this.title == null || title.equals(""))
      return getData().getString(TITLE);
    else
      return title;
  }
  
  public void setTitle(String title){
    this.title = title;
    getData().put(TITLE, title);
  }

  private String id;
  private String info = "";
  private String opinion = "";
  private String lunarDate = "";
  private String bookId = "";
  private String authorId = "";
  private String title = "";
  private String part = "";
  private String volume = "";
  private String chapter = "";
  private String page = "";
  private String line = "";
  public String getPart() {
    if(!part.equals(""))
      return part;
    else
      return getProperty(PART);
  }
  public void setPart(String part) {
    this.part = part;
    getData().put(PART, part);
  }
  public String getVolume() {
    if(!volume.equals(""))
    return volume;
    else 
      return getProperty(VOLUME);
  }
  public void setVolume(String volume) {
    this.volume = volume;
    getData().put(VOLUME, volume);
  }
  public String getPage() {
    if(!page.equals(""))
    return page;
    else
      return getProperty(PAGE);
  }
  public void setPage(String page) {
    this.page = page;
    getData().put(PAGE, page);
  }
  public String getLine() {
    if(!line.equals(""))
    return line;
    else
      return getProperty(LINE);
  }
  public void setLine(String line) {
    this.line = line;
    getData().put(LINE, line);
  }
  public String getChapter() {
    if(!chapter.equals(""))
    return chapter;
    else
      return getProperty(CHAPTER);
  }
  public void setChapter(String chapter) {
    this.chapter = chapter;
    getData().put(CHAPTER, chapter);
  }
  private Date date;
  private Set<String> tags = new HashSet<String>();
  
  
  public String getOpinion() {
    if("".equals(opinion) || opinion == null)
      this.opinion = getData().getString(OPPINION);
    return opinion;
  }
  public void setOpinion(String opinion) {
    this.opinion = opinion;
    getData().put(OPPINION,opinion);
  }
  public String getBookId() {
    if("".equals(bookId) || bookId == null)
      this.bookId = getData().getString(BOOK_ID);
    return bookId;
  }
  public void setBookId(String bookId) {
    this.bookId = bookId;
    getData().put(BOOK_ID,bookId);
  }
  public String getAuthorId() {
    if("".equals(authorId) || authorId == null)
      this.authorId = getData().getString(AUTHOR_ID);
    return authorId;
  }
  public void setAuthorId(String authorId) {
    this.authorId = authorId;
    getData().put(AUTHOR_ID,authorId);
  }
  public void setTags(Set<String> tags) {
    this.tags = tags;
    getData().put(TAGS, tags);
  }

  public static String ID = "id";
  public static String INFO = "info";
  public static String OPPINION = "oppinion";
  public static String LUNAR_DATE = "lunarDate";
  public static String AUTHOR_ID = "author_id";
  public static String BOOK_ID = "book_id";
  public static String DATE = "date";
  public static String TAGS = "tags";
  public static String TITLE = "title";
  public static String PART = "part";
  public static String VOLUME = "volume";
  public static String CHAPTER = "chapter";
  public static String PAGE = "page";
  public static String LINE = "line";
  @Override
  public boolean valid(JSONObject object) {
    return object.has(ID)&&object.has(INFO);
  }

}

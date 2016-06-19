package com.creative.sulieu.server.dao;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    return getData().getString(INFO);
  }
  public void setInfo(String info) {
    getData().put(INFO, info);
  }
  public String getOppinion() {
    return getData().getString(OPPINION);
  }
  public void setOppinion(String oppinion) {
    getData().put(OPPINION, oppinion);
  }
  public String getLunarYear() {
    return getData().getString(LUNAR_YEAR);
  }
  public void setLunarYear(String lunarYear) {
    getData().put(LUNAR_YEAR, lunarYear);
  }
  public String getLunarMonth() {
    return getData().getString(LUNAR_MONTH);
  }
  public void setLunarMonth(String lunarMonth) {
    getData().put(LUNAR_MONTH, lunarMonth);
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
  public String[] getTags() {
    return getData().getJSONArray(TAGS).join("|").split("|");
  }
  public void setTags(String[] tags) {
    getData().put(TAGS, tags);
  }
  public String getId() {
    if(getData().has(ID)) return getData().getString(ID);
    return id;
  }
  private String id;
  private String info;
  private String oppinion;
  private String lunarYear;
  private String lunarMonth;
  private Date date;
  private Tag[] tags;
  public static String ID = "id";
  public static String INFO = "info";
  public static String OPPINION = "oppinion";
  public static String LUNAR_YEAR = "lunarYear";
  public static String LUNAR_MONTH = "lunarMonth";
  public static String DATE = "date";
  public static String TAGS = "tags";

  @Override
  public boolean valid(JSONObject object) {
    return object.has(DATE)&&object.has(ID)&&object.has(INFO)&&object.has(LUNAR_MONTH)&&object.has(LUNAR_YEAR)&&object.has(OPPINION)&&object.has(TAGS);
  }
}

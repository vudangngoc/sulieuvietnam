package com.creative.sulieu.server.backendapi;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.creative.sulieu.server.AbstractHandler;
import com.creative.sulieu.server.NameDefine;
import com.creative.sulieu.server.dao.InfoItemDao;
import com.creative.sulieu.server.dao.InformationItem;
import com.creative.sulieu.server.dao.Tag;
import com.creative.sulieu.server.dao.TagDao;
import com.google.gson.Gson;

public class TagBackend extends AbstractHandler{
  final static Logger logger = Logger.getLogger(TagBackend.class);
  static {
    logger.setLevel(Level.DEBUG);
  }
  private static TagBackend object = new TagBackend();
  public static TagBackend getObject() {return object;}
  TagDao dao = new TagDao();
  Gson gson = new Gson();
  public void handle(HttpRequest arg0, HttpAsyncExchange arg1, HttpContext arg2) throws HttpException, IOException {
    super.handle(arg0,arg1,arg2);
    
    String result = "{}";
    if(this.queryParas.containsKey(NameDefine.ACTION)) {
      String action = this.queryParas.get(NameDefine.ACTION).get(0);
      switch (action) {
      case "saveTag":
        HttpEntity entity = null;
        if (arg0 instanceof HttpEntityEnclosingRequest)
          entity = ((HttpEntityEnclosingRequest)arg0).getEntity();
        byte[] data;
        if (entity == null) {
          data = new byte [0];
        } else {
          data = EntityUtils.toByteArray(entity);
        }
        String payload = new String(data);
        logger.debug("payload: " + payload);
        if(!"".equals(payload)) {
          JSONObject data1 = new JSONObject(payload);
          Tag tag = new Tag();
          tag.setData(data1);
          dao.save(tag,"");
          result = gson.toJson("OK");
        }
        break;
      case "deleteTag":
        for(String tagId : queryParas.get(NameDefine.TAG_ID)) {
          dao.delete(tagId);
        }
        result = gson.toJson("OK");
        break;
      case "getAllTag":
        result = getAllTag();
        break;
      case "getTagItemsId":
        if(queryParas.containsKey(NameDefine.TAG_ID)) {
          result = getTagItemsId(queryParas.get(NameDefine.TAG_ID).get(0));
        }
        break;
      case "getTagItems":
        if(queryParas.containsKey(NameDefine.TAG_ID)) {
          result = getTagItems(queryParas.get(NameDefine.TAG_ID).get(0));
        }
        break;
      case "addItem":
        if(queryParas.containsKey(NameDefine.TAG_ID)) {
          result = addItem(queryParas.get(NameDefine.TAG_ID).get(0),queryParas.get(NameDefine.ITEM_ID).get(0));
        }
        break;
      }
    }
    this.submitJsonResult(result, arg1);
  }

  private String addItem(String tagId, String itemId) {
    dao.addItem(tagId,itemId);
    return "OK";
  }

  private String getTagItems(String string) {
    InfoItemDao itemDao = new InfoItemDao();
    Tag tag = dao.get(string);
    ArrayList<InformationItem> result = new ArrayList<>();
    for(String item : tag.getRelativeItems()) {
      try {
        result.add(itemDao.get(item));
      } catch (Exception e) {
        logger.debug(e);
      }
    }
    return gson.toJson(result);
  }

  public String getTagItemsId(String string) {
    return gson.toJson(dao.get(string));
  }
  public String getAllTag() {
    return gson.toJson(dao.getAll());
  }
}

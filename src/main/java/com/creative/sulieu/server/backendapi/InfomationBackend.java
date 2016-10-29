package com.creative.sulieu.server.backendapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.creative.sulieu.server.AbstractHandler;
import com.creative.sulieu.server.NameDefine;
import com.creative.sulieu.server.dao.InfoItemDao;
import com.creative.sulieu.server.dao.InformationItem;
import com.creative.sulieu.server.dao.TagDao;
import com.google.gson.Gson;

public class InfomationBackend  extends AbstractHandler {
  final static Logger logger = Logger.getLogger(InfomationBackend.class);
  static {
    logger.setLevel(Level.DEBUG);
  }
  Gson gson = new Gson();
  static InfomationBackend object = new InfomationBackend();
  public static InfomationBackend getObject() {
    return object;
  }
  InfoItemDao dao = new InfoItemDao();
  TagDao tagDao = new TagDao();
  public void handle(HttpRequest arg0, HttpAsyncExchange arg1, HttpContext arg2) throws HttpException, IOException {
    super.handle(arg0,arg1,arg2);
    logger.debug("Incomming request:" + arg0.toString());

    String result = "{}";
    if(this.queryParas.containsKey(NameDefine.ACTION)) {
      String action = this.queryParas.get(NameDefine.ACTION).get(0);
      logger.debug("Action: " + action);
      switch (action) {
        case "saveInfo":{
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
            InformationItem a = new InformationItem();
            a.setData(new JSONObject(payload));
            a.setId(a.getId());
            dao.save(a.getId(), a);
            for(String tagId : a.getTags())
              TagDao.getObject().addItem(tagId, a.getId());
            result = "{\"OK\"}";
          }
        }
        break;
        case "deleteInfo":{
          for(String infoId : queryParas.get(InformationItem.ID)) {
            dao.delete(infoId);
          }
          result = "{\"OK\"}";
        }
        break;
        case "getAllInfo":
          result = getAllItem();
          break;
        case "getInfoListByBook":
          result = getInfoListByBook(queryParas.get(NameDefine.BOOK_ID).get(0));
          break;
        case "getInfoList":
          result = getInfoList();
          break;
        case "getInfoByAuthor":
          if(queryParas.containsKey(NameDefine.AUTHOR_ID)) {
            result = getInfoByAuthor(queryParas.get(NameDefine.AUTHOR_ID).get(0));
          }
          break;
        case "getInfo":
          if(queryParas.containsKey(InformationItem.ID)) {
            try {
              result = dao.get(queryParas.get(InformationItem.ID).get(0)).toString();
            } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          break;
      }
    }
    this.submitJsonResult(result, arg1);
  }
  
  private String getInfoListByBook(String bookId) {
    try {
      JSONObject result = new JSONObject();
      List<InformationItem> list = dao.getAll();
      StringBuilder description = new StringBuilder();
      for(InformationItem item : list){
        if(item.getBookId().equals(bookId)){
           description.setLength(0);
           description.trimToSize();
             description.append(item.getPart()).append(" - ")
                        .append(item.getVolume()).append(" - ")
                        .append(item.getChapter()).append(" - ")
                        .append(item.getPage()).append(" - ")
                        .append(item.getLine()).append(" - ")
                        .append(item.getTitle());
          result.put(item.getId(), description.toString());
        }
      }
      return result.toString();
    } catch (Exception e) {
      logger.debug(e);
    }
    
    return "";
  }

  public String getInfoByAuthor(String authorId) {
    ArrayList<InformationItem> result = new ArrayList<>();
    if(authorId != null || "".equals(authorId)){
      List<InformationItem> items = null;
      try {
        items = dao.getAll();
      } catch (Exception e) {
        logger.debug(e);
      }

      if(items != null && items.size() >0)
        for(InformationItem info : items)
          if(authorId.equals(info.getAuthorId())){
            result.add(info);
          }
    }
    return result.toString();
  }
  public String getInfoList() {
    try {
      JSONObject data = new JSONObject();
      Set<String> keys = dao.getAllKeys();
      for(String id : keys) {
        InformationItem temp = dao.get(id);
        data.put(temp.getId(),temp.getTitle());
      }
      return data.toString();
    } catch (Exception e) {
      logger.debug(e);
    }
    return "";
  }
  public String getAllItem() {
    JSONArray result = new JSONArray();
    try {
      for(InformationItem item : dao.getAll()) {
        result.put(item.getData());
      }
    } catch (Exception e) {
      logger.debug(e);
    }
    return result.toString();
  }
}

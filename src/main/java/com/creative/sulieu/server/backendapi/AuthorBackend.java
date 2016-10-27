package com.creative.sulieu.server.backendapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.nio.protocol.BasicAsyncResponseProducer;
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.creative.sulieu.server.AbstractHandler;
import com.creative.sulieu.server.NameDefine;
import com.creative.sulieu.server.dao.Author;
import com.creative.sulieu.server.dao.AuthorDao;
import com.google.gson.Gson;

public class AuthorBackend  extends AbstractHandler {
  final static Logger logger = Logger.getLogger(AuthorBackend.class);
  static {
    logger.setLevel(Level.DEBUG);
  }
  private static AuthorBackend single = new AuthorBackend();
  /**
   * Singleton 
   * @return
   */
  public static AuthorBackend getObject() {
    return single;
  }
  AuthorDao dao = new AuthorDao();
  private Gson gson= new Gson();
  public void handle(HttpRequest arg0, HttpAsyncExchange arg1, HttpContext arg2) throws HttpException, IOException {
    super.handle(arg0,arg1,arg2);
    logger.debug("Incomming request:" + arg0.toString());
    String result = "{}";
    if(this.queryParas.containsKey(NameDefine.ACTION)) {
      String action = this.queryParas.get(NameDefine.ACTION).get(0);
      logger.debug("Action: " + action);
      switch (action) {
      case "saveAuthor":{
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
          Author a = new Author();
          a.setData(new JSONObject(payload));
          a.setId(a.getId());
          dao.save(a.getId(), a);
          result = "{\"OK\"}";
        }
      }
      break;
      case "deleteAuthor":{
        for(String authorId : queryParas.get(NameDefine.AUTHOR_ID)) {
          dao.delete(authorId);
        }
        result = "{\"OK\"}";
      }
      break;
      case "getAuthor":
        if(queryParas.containsKey(NameDefine.AUTHOR_ID)) {
          result = getAuthor(queryParas.get(NameDefine.AUTHOR_ID).get(0));
        }
        break;
      case "getAllAuthor":{
        result = getAllAuthor();
      }
      break;
      case "getAuthorList":{
        result = getAuthorList();
      }
      break;
      }
    }
    this.submitJsonResult(result, arg1);
  }

  public String getAuthor(String id) {
    String result = "{}";

    try {
      Author author = dao.get(id);
      result = author.toString();
    } catch (Exception e) {
      logger.debug(e);
    }
    return result;
  }

  public String getAllAuthor() {
    JSONArray arr = new JSONArray();
    try {
      for(Author a : dao.getAll()) {
        arr.put(a);
      }
    }catch(Exception e) {
      logger.debug(e);
    }
    return  arr.toString();
  }

  public String getAuthorList() {
    ArrayList<Author> list = new ArrayList<>();
    try {
    for(String id : dao.getAllKeys()) {
      list.add(dao.get(id));
    }
    }catch(Exception e) {
      logger.debug(e);
    }
    HashMap<String,String> map = new HashMap<>();
    for(Author a : list)
      map.put(a.getId(), a.getName());
    return gson.toJson(map);
  }
}

package com.creative.sulieu.server.backendapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import com.creative.sulieu.server.dao.Book;
import com.creative.sulieu.server.dao.BookDao;
import com.fasterxml.jackson.core.sym.Name;
import com.google.gson.Gson;

public class BookBackend  extends AbstractHandler {
  final static Logger logger = Logger.getLogger(BookBackend.class);
  static {
    logger.setLevel(Level.DEBUG);
  }
  Gson gson = new Gson();
  static BookBackend object = new BookBackend();
  public static BookBackend getObject() {
    return object;
  }
  BookDao dao = new BookDao();
  public void handle(HttpRequest arg0, HttpAsyncExchange arg1, HttpContext arg2) throws HttpException, IOException {
    super.handle(arg0,arg1,arg2);
    logger.debug("Incomming request:" + arg0.toString());

    String result = "{}";
    if(this.queryParas.containsKey(NameDefine.ACTION)) {
      String action = this.queryParas.get(NameDefine.ACTION).get(0);
      logger.debug("Action: " + action);
      switch (action) {
      case "saveBook":{
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
          Book a = new Book();
          a.setData(new JSONObject(payload));
          a.setId(a.getId());
          dao.save(a.getId(), a);
          result = "{\"OK\"}";
        }
      }
      break;
      case "deleteBook":{
        for(String bookId : queryParas.get(NameDefine.BOOK_ID)) {
          dao.delete(bookId);
        }
        result = "{\"OK\"}";
      }
      break;
      case "getBook":
        if(queryParas.containsKey(NameDefine.BOOK_ID))
          result = getBook(queryParas.get(NameDefine.BOOK_ID).get(0));
        break;
      case "getAllBook":
        result = getAllBook();
        break;
      case "getBookList":
        result = getBookList();
        break;
      case "getBookByAuthor":
        if(queryParas.containsKey(NameDefine.AUTHOR_ID)) {
          result = getBookByAuthor(queryParas.get(NameDefine.AUTHOR_ID).get(0));
        }
        break;
      }
    }
    this.submitJsonResult(result, arg1);
  }
  public String getBookByAuthor(String string) {
    List<Book> books = null;
    try {
      books = dao.getAll();
    } catch (Exception e) {
      logger.debug(e);
    }
    ArrayList<Book> result = new ArrayList<>();
    for(Book book : books) {
      if(string.equals(book.getAuthorId()))
        result.add(book);
    }
    return gson.toJson(result);
  }
  public String getBookList() {
    try {
      JSONObject data = new JSONObject();
      Set<String> books = dao.getAllKeys();
      for(String id : books) {
        Book temp;
        temp = dao.get(id);
        data.put(temp.getId(),temp.getName());
      }
      return data.toString();
    } catch (Exception e) {
      logger.debug(e);
    }
    return null;
  }
  public String getAllBook() {
    JSONArray result = new JSONArray();
    try {
      for(Book book : dao.getAll()) {
        result.put(book.getData());
      }
    } catch (Exception e) {
      logger.debug(e);
    }
    return result.toString();
  }
  public String getBook(String string) {
    String result = "{}";
    try {
      Book b = dao.get(string);
      result = b.getData().toString();
    } catch (Exception e) {
      logger.debug(e);
    }
    return result;
  }

}

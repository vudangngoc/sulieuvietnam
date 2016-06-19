package com.creative.sulieu.server.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

import com.creative.sulieu.server.AbstractHandler;
import com.creative.sulieu.server.NameDefine;
import com.creative.sulieu.server.backendapi.AuthorBackend;
import com.creative.sulieu.server.backendapi.BookBackend;
import com.creative.sulieu.server.dao.Author;
import com.creative.sulieu.server.dao.AuthorDao;
import com.google.gson.Gson;

public class BookAPI extends AbstractHandler {
  final static Logger logger = Logger.getLogger(BookAPI.class);
  
  public void handle(HttpRequest arg0, HttpAsyncExchange arg1, HttpContext arg2) throws HttpException, IOException {
    super.handle(arg0,arg1,arg2);
    BookBackend api = BookBackend.getObject();
    String result = "{}";
    if(this.queryParas.containsKey(NameDefine.ACTION)) {
      String action = this.queryParas.get(NameDefine.ACTION).get(0);
      switch (action) {
      case "getBook":
        if(queryParas.containsKey(NameDefine.BOOK_ID)) {
          result = api.getBook(queryParas.get(NameDefine.BOOK_ID).get(0));
        }
        break;
      case "getAllBook":
        result = api.getAllBook();
      break;
      case "getBookList":
        result = api.getBookList();
       break;
      case "getBookByAuthor":
        if(queryParas.containsKey(NameDefine.AUTHOR_ID)) {
          result = api.getBookByAuthor(queryParas.get(NameDefine.AUTHOR_ID).get(0));
        }
      break;
      }
    }
    this.submitJsonResult(result, arg1);
  }
}

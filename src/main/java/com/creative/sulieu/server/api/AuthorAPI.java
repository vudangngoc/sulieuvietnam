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
import com.creative.sulieu.server.dao.Author;
import com.creative.sulieu.server.dao.AuthorDao;
import com.google.gson.Gson;

public class AuthorAPI extends AbstractHandler {
  final static Logger logger = Logger.getLogger(AuthorAPI.class);
  
  public void handle(HttpRequest arg0, HttpAsyncExchange arg1, HttpContext arg2) throws HttpException, IOException {
    super.handle(arg0,arg1,arg2);
    AuthorBackend api = AuthorBackend.getObject();
    String result = "{}";
    if(this.queryParas.containsKey(NameDefine.ACTION)) {
      String action = this.queryParas.get(NameDefine.ACTION).get(0);
      switch (action) {
      case "getAuthor":
        if(queryParas.containsKey(NameDefine.AUTHOR_ID)) {
          result = api.getAuthor(queryParas.get(NameDefine.AUTHOR_ID).get(0));
        }
        break;
      case "getAllAuthor":
        result = api.getAllAuthor();
      break;
      case "getAuthorList":
        result = api.getAuthorList();
      break;
      }
    }
    this.submitJsonResult(result, arg1);
  }
}

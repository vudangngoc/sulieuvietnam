package com.creative.sulieu.server.backendapi;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NFileEntity;
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

public class StatisticFileHandle  extends AbstractHandler {
  final static Logger logger = Logger.getLogger(StatisticFileHandle.class);
  private String homeFolder = "/usr/share/nginx/html/backend";
  static {
    logger.setLevel(Level.DEBUG);
  }
  private static StatisticFileHandle single = new StatisticFileHandle();
  /**
   * Singleton 
   * @return
   */
  public static StatisticFileHandle getObject() {
    return single;
  }
  protected String getRootFolder() {return homeFolder;}
  public void handle(HttpRequest request, HttpAsyncExchange httpexchange, HttpContext arg2) throws HttpException, IOException {
    String method = request.getRequestLine().getMethod().toUpperCase(Locale.ROOT);
    if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
        throw new MethodNotSupportedException(method + " method not supported");
    }
    String target = request.getRequestLine().getUri();

    if (request instanceof HttpEntityEnclosingRequest) {
        HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
        byte[] entityContent = EntityUtils.toByteArray(entity);
        System.out.println("Incoming entity content (bytes): " + entityContent.length);
    }

    final File file = new File(getRootFolder(), URLDecoder.decode(target, "UTF-8"));
    HttpResponse response = httpexchange.getResponse();
    if (!file.exists()) {
      response.setStatusCode(HttpStatus.SC_NOT_FOUND);
      response.setEntity(new NStringEntity("File not found"));
    } else if (!file.canRead() || file.isDirectory()) {
      response.setStatusCode(HttpStatus.SC_FORBIDDEN);
      response.setEntity(new NStringEntity("FORBIDDEN"));
    } else {
      response.setStatusCode(HttpStatus.SC_OK);
      String fileName = file.getName();
      NFileEntity body = null;
	if(fileName.endsWith("html"))
		body = new NFileEntity(file,ContentType.create("text/html", Consts.UTF_8));
	else if (fileName.endsWith("js"))
		body = new NFileEntity(file,ContentType.create("text/js", Consts.UTF_8));
	else
		body = new NFileEntity(file,ContentType.create("text/css", Consts.UTF_8));
      
      response.setEntity(body);
    }
    httpexchange.submitResponse(new BasicAsyncResponseProducer(response));
  }

  
}

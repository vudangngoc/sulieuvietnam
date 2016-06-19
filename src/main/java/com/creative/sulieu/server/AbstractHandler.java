package com.creative.sulieu.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.nio.protocol.BasicAsyncRequestConsumer;
import org.apache.http.nio.protocol.BasicAsyncResponseProducer;
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.nio.protocol.HttpAsyncRequestConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestHandler;
import org.apache.http.protocol.HttpContext;

public abstract class AbstractHandler implements HttpAsyncRequestHandler<HttpRequest>{
  
  protected Map<String, List<String>> queryParas = new HashMap<String, List<String>>();
  public HttpAsyncRequestConsumer<HttpRequest> processRequest(HttpRequest arg0, HttpContext arg1)
      throws HttpException, IOException {
    return new BasicAsyncRequestConsumer();
  }
  public void handle(HttpRequest arg0, HttpAsyncExchange arg1, HttpContext arg2) throws HttpException, IOException {
    this.queryParas = splitQuery(arg0.getRequestLine().getUri());
  }
  public void submitJsonResult(String result,HttpAsyncExchange arg1) {
    HttpResponse response = arg1.getResponse();
    NStringEntity entity = new NStringEntity(
        result,
        ContentType.create("text/json", "UTF-8"));
    response.setEntity(entity);
    arg1.submitResponse(new BasicAsyncResponseProducer(response));
  }
  public static Map<String, List<String>> splitQuery(String url) throws UnsupportedEncodingException {
    int index = url.indexOf('?');
    if(index > 0)
      url = url.substring(index + 1);
    final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
    final String[] pairs = url.split("&");
    for (String pair : pairs) {
      final int idx = pair.indexOf("=");
      final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
      if (!query_pairs.containsKey(key)) {
        query_pairs.put(key, new LinkedList<String>());
      }
      final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
      query_pairs.get(key).add(value);
    }
    return query_pairs;
  }
}

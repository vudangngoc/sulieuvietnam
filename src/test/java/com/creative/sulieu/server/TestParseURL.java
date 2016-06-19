package com.creative.sulieu.server;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestParseURL {
  @Test
  public void testParse() {
    String url = "/author/?action=saveAuthor";
    Map<String,List<String>> para = null; 
    try {
      para = AbstractHandler.splitQuery(url);
    } catch (UnsupportedEncodingException e) {
    }
    assertNotNull(para);
    assertTrue(para.containsKey("action"));
  }
}

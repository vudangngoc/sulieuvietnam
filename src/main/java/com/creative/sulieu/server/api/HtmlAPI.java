package com.creative.sulieu.server.api;

import org.apache.log4j.Logger;

import com.creative.sulieu.server.backendapi.StatisticFileHandle;

public class HtmlAPI extends StatisticFileHandle {
  final static Logger logger = Logger.getLogger(HtmlAPI.class);
  @Override
  protected String getRootFolder() {
    return "/usr/share/nginx/html";
  }
}

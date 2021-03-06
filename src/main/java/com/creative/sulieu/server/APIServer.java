package com.creative.sulieu.server;

/*
 * ==================================================================== Licensed to the Apache
 * Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License. ====================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

import java.util.concurrent.TimeUnit;

import org.apache.http.ExceptionLogger;
import org.apache.http.impl.nio.bootstrap.HttpServer;
import org.apache.http.impl.nio.bootstrap.ServerBootstrap;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.creative.sulieu.server.api.AuthorAPI;
import com.creative.sulieu.server.api.BookAPI;
import com.creative.sulieu.server.api.HtmlAPI;
import com.creative.sulieu.server.backendapi.StatisticFileHandle;

public class APIServer {
  final static Logger logger = Logger.getLogger(APIServer.class);
  static {
    logger.setLevel(Level.DEBUG);
  }

  public static void main(String[] args) throws Exception {

    int port = 8080;

    IOReactorConfig config =
        IOReactorConfig.custom().setSoTimeout(15000).setTcpNoDelay(true).build();
    HtmlAPI statichandle = new HtmlAPI();
    final HttpServer server = ServerBootstrap.bootstrap().setListenerPort(port)
        .setServerInfo("API frontend server").setIOReactorConfig(config)
        .setExceptionLogger(ExceptionLogger.STD_ERR).registerHandler("/author/*", new AuthorAPI())
        .registerHandler("/book/*", new BookAPI()).registerHandler("*html", statichandle)
        .registerHandler("*js", statichandle).registerHandler("*css", statichandle).create();
    System.out.println("Starting server");
    server.start();
    server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.out.println("Stoping server");
        server.shutdown(5, TimeUnit.SECONDS);
      }
    });

  }


}

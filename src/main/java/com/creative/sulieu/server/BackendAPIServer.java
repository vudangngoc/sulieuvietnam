package com.creative.sulieu.server;

/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
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
import com.creative.sulieu.server.backendapi.AuthorBackend;
import com.creative.sulieu.server.backendapi.BookBackend;
import com.creative.sulieu.server.backendapi.StatisticFileHandle;

public class BackendAPIServer {
  final static Logger logger = Logger.getLogger(BackendAPIServer.class);
  static {
    logger.setLevel(Level.DEBUG);
  }
  public static void main(String[] args) throws Exception {

    // Document root directory
    int port = 8888;

    IOReactorConfig config = IOReactorConfig.custom()
        .setSoTimeout(15000)
        .setTcpNoDelay(true)
        .build();

    final HttpServer server = ServerBootstrap.bootstrap()
        .setListenerPort(port)
        .setServerInfo("API frontend server")
        .setIOReactorConfig(config)
        .setExceptionLogger(ExceptionLogger.STD_ERR)
        .registerHandler("/author/*", AuthorBackend.getObject())
        .registerHandler("/book/*", BookBackend.getObject())
        .registerHandler("*html", StatisticFileHandle.getObject())
        .registerHandler("*js", StatisticFileHandle.getObject())
        .registerHandler("*css", StatisticFileHandle.getObject())
        .create();
    logger.info("Starting server");
    server.start();
    server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        logger.info("Stoping server");
        server.shutdown(5, TimeUnit.SECONDS);
      }
    });

  }


}
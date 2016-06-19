package com.creative.sulieu.server;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;

import redis.embedded.RedisServer;

@Ignore
public class BaseTest {
  private static RedisServer redisServer;

  @BeforeClass
  public static void setUpBaseClass() throws IOException {
    redisServer = new RedisServer(11502);
    redisServer.start();
    System.out.println("Init embedded Redis Server");
  }
  
  @AfterClass
  public static void tearDownBaseClass() {
    redisServer.stop();
    System.out.println("Close embedded Redis Server");
  }
}

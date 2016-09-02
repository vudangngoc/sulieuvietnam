package com.creative.sulieu.server.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import com.creative.sulieu.server.BaseTest;
import com.google.gson.Gson;

public class TestBookDao extends BaseTest{
  @Test
  public void testSaveGet() throws Exception {
    //Given
    BookDao dao = new BookDao();
    Book data1 = new Book();
    data1.setAuthorId("d0565d728a9c4fef85e0239540a45bd8829e80ddf2cb4e959e8586bb291d2d24");
    data1.setName("book1");
    data1.setReleaseYear(1480);
    System.out.println(data1.toString());
    //When

    dao.save(data1.getId(), data1);
    Book data = dao.get(data1.getId());
    //then
    assertNotNull(data);
    System.out.println(data.toString());


  }

}

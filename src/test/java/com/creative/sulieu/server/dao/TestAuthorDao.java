package com.creative.sulieu.server.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import com.creative.sulieu.server.BaseTest;
import com.google.gson.Gson;

public class TestAuthorDao extends BaseTest{
  @Test
  public void testSaveGet() {
    //Given
    AuthorDao dao = new AuthorDao();
    Author data = new Author();
    dao.setDomain("TestAuthorDao");
    String comment = "dfgfgfgfg";
    int yob = 1900;

    data.setId(data.getId());
    String id = data.getId();
    data.setDescription(comment);
    data.setYob(yob);
    data.setName("gfgfgfg");
    System.out.println(data.toString());
    int count = 1;
    Author data1 = null;
    //When
    long start = System.currentTimeMillis();
    for(int i = 0; i < count; i ++) {
      dao.save(id, data);
      try {
        data1 = (Author)dao.get(id);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    long duration = System.currentTimeMillis() - start;
    System.out.println("Get and set " + count + " take " + duration + "ms");
    //then
    System.out.println(data1.toString());
    assertNotNull(data1);
    assertEquals(id, data1.getId());
    assertEquals(yob, data1.getYob());
    assertEquals(comment, data1.getComment());
  }
  
  @Test
  public void testDelete() throws Exception{
    //Given
    AuthorDao dao = new AuthorDao();
    Author data = new Author();
    dao.setDomain("TestAuthorDao");
    String comment = "dfgfgfgfg";
    int yob = 1900;

    data.setId(data.getId());
    String id = data.getId();
    data.setDescription(comment);
    data.setYob(yob);
    data.setName("gfgfgfg");
    
    dao.save(id, data);
    
    assertNotNull(dao.get(id));
    dao.delete(id);
    assertNull(dao.get(id));
  }
  
  @Test
  public void testGetAll() throws Exception{
    //Given
    AuthorDao dao = new AuthorDao();
    dao.deleteAll();
    Author data = new Author();
    dao.setDomain("TestAuthorDao");
    String comment = "dfgfgfgfg";
    int yob = 1900;

    data.setId(data.getId());
    String id = data.getId();
    data.setDescription(comment);
    data.setYob(yob);
    data.setName("gfgfgfg");
    // When 
    dao.save(id, data);
    //Then
    assertEquals(1,dao.getAll().size());

  }
}

package com.creative.sulieu.server.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import com.creative.sulieu.server.BaseTest;
import com.google.gson.Gson;

public class OpinionDaoTest extends BaseTest{
  @Test
  public void testSaveGet() throws Exception {
    //Given
    OpinionDao dao = new OpinionDao();
    Opinion data1 = new Opinion();
    data1.setBookid("58338597dd864b1d974915ec430e32edefcba83ed8564424b65a780c57ab7063");
    data1.setAuthorId("f7d4c789675843dbbe3d1f9414df62ac5c7ddd3557f44c48a2251221c14ee1c8");
    data1.setOpinion("");
    System.out.println(data1.toString());
    //When

    dao.save(data1.getId(), data1);
    Opinion data = dao.get(data1.getId());
    //then
    assertNotNull(data);
    assertEquals(data.getId(), data1.getId());
    System.out.println(data.toString());


  }

}

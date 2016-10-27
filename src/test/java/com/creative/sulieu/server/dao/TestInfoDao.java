package com.creative.sulieu.server.dao;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import com.creative.sulieu.server.BaseTest;
import com.google.gson.Gson;

public class TestInfoDao extends BaseTest{
  @Test
  public void testSaveGet() throws Exception {
    //Given
    Set<String> tags = new HashSet<String>();
    tags.add("tag1");
    tags.add("tag2");
    InfoItemDao dao = new InfoItemDao();
    InformationItem data1 = new InformationItem();
    data1.setInfo("Information");
    data1.setAuthorId("f7d4c789675843dbbe3d1f9414df62ac5c7ddd3557f44c48a2251221c14ee1c8");
    data1.setBookId("58338597dd864b1d974915ec430e32edefcba83ed8564424b65a780c57ab7063");
    data1.setDate(new Date());
    data1.setOpinion("Opinion");
    data1.setLunarDate("Canh Dần");
    data1.setTags(tags);
    System.out.println(data1.toString());
    //When

    dao.save(data1.getId(), data1);
    InformationItem data = dao.get(data1.getId());
    //then
    assertNotNull(data);
    assertEquals(data.getId(), data1.getId());
    System.out.println(data.toString());


  }

}

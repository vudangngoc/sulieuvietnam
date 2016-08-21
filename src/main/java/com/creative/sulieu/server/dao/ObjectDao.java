package com.creative.sulieu.server.dao;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.nustaq.serialization.FSTConfiguration;

public class ObjectDao<T extends Object> {
  protected String domain = "";
  protected Class handleClass = Object.class;
  private RedisHelper rh = new RedisHelper();
  //private FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
  
  public  void save(String key, T data){
    rh.insertByteArr(getDomain(), key, data.getData().toString().getBytes());
  }
  
  public T get(String key) throws Exception {
    byte[] result = rh.getByteArr(getDomain(), key);
    if(result != null) {
      T o = createObject(getHandleClass());
      o.setData(new JSONObject(new String(result)));
      return o;
    }
    else
      return null;
  }
  protected Class<T> getHandleClass() {
    return handleClass;
  }

  protected String getDomain() {return this.domain;}
  public void delete(String key){
    rh.deleteKey(getDomain(), key);
  }
  
  public Set<String> getAllKeys(){
    return rh.getAll(getDomain());
  }
  
  public boolean exist(String key) {
    return rh.exist(getDomain(), key);
  }
  
  public void setDomain(String domain) {
    this.domain = domain;
  }
  
  public List<T> getAll() throws Exception{
    List<T> result = new ArrayList<>();
    Set<String> ids = getAllKeys();
    for(String id : ids)
      result.add(get(id));
    return result;
  }
  @SuppressWarnings("unchecked")
  public T createObject(Class<T> v) throws Exception{
    Constructor[] constructors = v.getConstructors();
    Constructor willBeImplemented = null;
    for(int i = 0; i <constructors.length; i++){
      if(constructors[i].getParameterCount() == 0) willBeImplemented = constructors[i];
    }
    if(willBeImplemented == null) throw new NoSuchMethodException(v.getName() + " don't have no para constructor");
    return (T) willBeImplemented.newInstance();
  }
}

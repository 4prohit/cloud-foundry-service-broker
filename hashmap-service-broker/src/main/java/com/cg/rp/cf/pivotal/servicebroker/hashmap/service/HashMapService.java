package com.cg.rp.cf.pivotal.servicebroker.hashmap.service;

/**
 * @author rohitpat
 *
 */
public interface HashMapService {
	 public void create(String serviceInstanceId);
	 public void remove(String serviceInstanceId);
	 public void put(String serviceInstanceId, Object key, Object value);
	 public Object get(String serviceInstanceId, Object key);
	 public void remove(String serviceInstanceId, Object key);
	 public Boolean exists(String serviceInstanceId);
}

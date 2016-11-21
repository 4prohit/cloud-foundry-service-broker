package com.cg.rp.cf.pivotal.servicebroker.hashmap.service.impl;

import org.springframework.stereotype.Service;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.CustomHashMap;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.service.HashMapService;

/**
 * @author rohitpat
 *
 */
@Service
public class HashMapServiceImpl implements HashMapService {
	// service to store and access key-value pairs
	
	private CustomHashMap<String, CustomHashMap<Object, Object>> customHashMap = new CustomHashMap<>();

    public void create(String serviceInstanceId) {
        customHashMap.put(serviceInstanceId, new CustomHashMap<Object, Object>());
    }

    public void remove(String serviceInstanceId) {
        customHashMap.remove(serviceInstanceId);
    }

    public void put(String serviceInstanceId, Object key, Object value) {
        CustomHashMap<Object, Object> mapInstance = customHashMap.get(serviceInstanceId);
        mapInstance.put(key, value);
    }

    public Object get(String serviceInstanceId, Object key) {
        CustomHashMap<Object, Object> mapInstance = customHashMap.get(serviceInstanceId);
        return mapInstance.get(key);
    }

    public void remove(String serviceInstanceId, Object key) {
        CustomHashMap<Object, Object> mapInstance = customHashMap.get(serviceInstanceId);
        mapInstance.remove(key);
    }

    public Boolean exists(String serviceInstanceId) {
        return null != customHashMap.get(serviceInstanceId);
    }
}

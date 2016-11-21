package com.cg.rp.cf.pivotal.servicebroker.hashmap.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.service.HashMapService;

/**
 * @author rohitpat
 *
 */
@RestController
public class HashMapController {
    private HashMapService hashMapService;
    
    @Autowired
    public HashMapController(HashMapService hashMapService) {
		this.hashMapService = hashMapService;
	}

    // REST api to put key-value pair in custom hashmap instance
    @RequestMapping(value = "/hashmap/{instanceId}/{key}", method = RequestMethod.PUT)
    public ResponseEntity<String> put(@PathVariable("instanceId") String instanceId,
                                      @PathVariable("key") String key,
                                      @RequestBody String value) {
        hashMapService.put(instanceId, key, value);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // REST api to get key-value pair from custom hashmap instance
    @RequestMapping(value = "/hashmap/{instanceId}/{key}", method = RequestMethod.GET)
    public ResponseEntity<Object> get(@PathVariable("instanceId") String instanceId,
                                      @PathVariable("key") String key) {
        Object result = hashMapService.get(instanceId, key);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("{}", HttpStatus.NOT_FOUND);
        }
    }

    // REST api to remove key-value pair from custom hashmap instance
    @RequestMapping(value = "/hashmap/{instanceId}/{key}", method = RequestMethod.DELETE)
    public ResponseEntity<String> remove(@PathVariable("instanceId") String instanceId,
                                         @PathVariable("key") String key) {
        Object result = hashMapService.get(instanceId, key);
        if (result != null) {
            hashMapService.remove(instanceId, key);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{}", HttpStatus.GONE);
        }
    }
}

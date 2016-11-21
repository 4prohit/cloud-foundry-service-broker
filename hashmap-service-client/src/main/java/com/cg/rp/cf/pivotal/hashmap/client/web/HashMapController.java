package com.cg.rp.cf.pivotal.hashmap.client.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cg.rp.cf.pivotal.hashmap.client.cloud.HashMapServiceInfo;

/**
 * @author rohitpat
 *
 */
@RestController
@RequestMapping("hashmap")
public class HashMapController {
	private HashMapServiceInfo hashMapServiceInfo;
	private RestTemplate restTemplate;
	
	@Autowired
	public HashMapController(HashMapServiceInfo hashMapServiceInfo, RestTemplate restTemplate) {
		this.hashMapServiceInfo = hashMapServiceInfo;
		this.restTemplate = restTemplate;
	}
	
	// REST api to get service info of bound service
    @RequestMapping("/info")
    public HashMapServiceInfo info() {
        return hashMapServiceInfo;
    }

    // REST api to put key-value pair in bound custom hashmap service instance
    @RequestMapping(value = "/{key}", method = RequestMethod.PUT)
    public ResponseEntity<String> put(@PathVariable("key") String key, @RequestBody String value) {
        restTemplate.put(hashMapServiceInfo.getUri()+"/{key}", value, key);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // REST api to get key-value pair from bound custom hashmap service instance
    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public ResponseEntity<String> get(@PathVariable("key") String key) {
        String response = restTemplate.getForObject(hashMapServiceInfo.getUri() + "/{key}", String.class, key);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // REST api to delete key-value pair from bound custom hashmap service instance
    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    public ResponseEntity<String> remove(@PathVariable("key") String key) {
        restTemplate.delete(hashMapServiceInfo.getUri()+"/{key}", key);
        return new ResponseEntity<>("{}", HttpStatus.NO_CONTENT);
    }
}

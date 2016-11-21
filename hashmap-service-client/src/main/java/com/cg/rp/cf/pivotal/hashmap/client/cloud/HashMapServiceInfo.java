package com.cg.rp.cf.pivotal.hashmap.client.cloud;

import org.springframework.cloud.service.BaseServiceInfo;

/**
 * @author rohitpat
 *
 */
public class HashMapServiceInfo extends BaseServiceInfo {
	// service info class to store service credentials
	
	private final String uri;
    private final String username;
    private final String password;

    public HashMapServiceInfo(String id, String uri, String username, String password) {
        super(id);
        this.uri = uri;
        this.username = username;
        this.password = password;
    }

    @ServiceProperty
    public String getUri() {
        return uri;
    }

    @ServiceProperty
    public String getUsername() {
        return username;
    }

    @ServiceProperty
    public String getPassword() {
        return password;
    }
}

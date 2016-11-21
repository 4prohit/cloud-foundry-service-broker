package com.cg.rp.cf.pivotal.hashmap.client.cloud.cloudfoundry;

import java.util.Map;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import com.cg.rp.cf.pivotal.hashmap.client.cloud.HashMapServiceInfo;

/**
 * @author rohitpat
 *
 */
public class HashMapServiceInfoCreator extends CloudFoundryServiceInfoCreator<HashMapServiceInfo> {
	public static final String HASHMAP_TAG = "hashmap";

	public HashMapServiceInfoCreator() {
		super(new Tags(HASHMAP_TAG));
	}

    @Override
    public boolean accept(Map<String, Object> serviceData) {
    	Map<String, Object> credentials = (Map<String, Object>) getCredentials(serviceData).get("credentials");
		String uri = getStringFromCredentials(credentials, "uri");
        String username = getStringFromCredentials(credentials, "username");
        String password = getStringFromCredentials(credentials, "password");
        return null != uri && null != username && null != password;
    }

	@Override
	public HashMapServiceInfo createServiceInfo(Map<String, Object> serviceData) {
		String id = (String) serviceData.get("name");
		Map<String, Object> credentials = (Map<String, Object>) getCredentials(serviceData).get("credentials");
		String uri = getStringFromCredentials(credentials, "uri");
        String username = getStringFromCredentials(credentials, "username");
        String password = getStringFromCredentials(credentials, "password");
		return new HashMapServiceInfo(id, uri, username, password);
	}
}

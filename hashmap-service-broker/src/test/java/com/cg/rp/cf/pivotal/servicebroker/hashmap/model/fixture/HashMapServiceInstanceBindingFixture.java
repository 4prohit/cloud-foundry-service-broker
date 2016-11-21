package com.cg.rp.cf.pivotal.servicebroker.hashmap.model.fixture;

import java.util.UUID;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.Credentials;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.HashMapServiceInstanceBinding;

/**
 * @author rohitpat
 *
 */
public class HashMapServiceInstanceBindingFixture {
	public static HashMapServiceInstanceBinding getServiceInstanceBinding() {
		Credentials credentials = new Credentials();
        credentials.setId(UUID.randomUUID().toString());
        credentials.setUri("http://example.com/hashmap/123456");
        credentials.setUsername("user");
        credentials.setPassword("password");
		return new HashMapServiceInstanceBinding("binding-id", "service-instance-id", credentials, null, "app-guid");
	}
}

package com.cg.rp.cf.pivotal.servicebroker.hashmap.model.fixture;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.HashMapServiceInstance;

/**
 * @author rohitpat
 *
 */
public class HashMapServiceInstanceFixture {
	public static HashMapServiceInstance getServiceInstance() {
		return new HashMapServiceInstance("service-instance-id", "service-definition-id", "plan-id", "org-guid", "space-guid");
	}
}

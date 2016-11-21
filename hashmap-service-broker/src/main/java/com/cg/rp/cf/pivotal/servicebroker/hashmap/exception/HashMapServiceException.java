package com.cg.rp.cf.pivotal.servicebroker.hashmap.exception;

import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;

/**
 * @author rohitpat
 *
 */
public class HashMapServiceException extends ServiceBrokerException {
	// custom exception to wrap service broker exceptions
	
	private static final long serialVersionUID = 8667141725171626000L;

	public HashMapServiceException(String message) {
		super(message);
	}
	
}

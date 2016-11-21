package com.cg.rp.cf.pivotal.servicebroker.hashmap.config;

import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rohitpat
 *
 */
@Configuration
public class CloudConfig {
	
	// creating Cloud bean (required to get service information)
	@Bean
    public Cloud cloud() {
        return new CloudFactory().getCloud();
    }
}

package com.cg.rp.cf.pivotal.servicebroker.hashmap.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rohitpat
 *
 */
@Configuration
public class CatalogConfig {
	// creating Catelog bean with service definition, plan and metadata
	@Bean
    public Catalog catalog() throws IOException {
        ServiceDefinition serviceDefinition = new ServiceDefinition("HashMap-Service", "HashMap-Service", "HashMap as a service on shared instance",
                true, false, getPlans(), getTags(), getServiceDefinitionMetadata(), null, null); //Arrays.asList("syslog_drain")
        return new Catalog(Arrays.asList(serviceDefinition));
    }

    private static List<Plan> getPlans() {
        Plan basic = new Plan("Basic-HashMap-Service", "Basic-HashMap-Service",
                "A basic plan providing a single HashMap instance on a shared resources with limited storage.", getPlanMetadata(), true);
        return Arrays.asList(basic);
    }
	
	private static Map<String,Object> getPlanMetadata() {
		Map<String,Object> planMetadata = new HashMap<>();
		planMetadata.put("costs", getCosts());
		planMetadata.put("bullets", getBullets());
		return planMetadata;
	}

	private static List<Map<String,Object>> getCosts() {
		Map<String,Object> costsMap = new HashMap<>();
		
		Map<String,Object> amount = new HashMap<>();
		amount.put("usd", 0.0);
	
		costsMap.put("amount", amount);
		costsMap.put("unit", "MONTHLY");
		
		return Collections.singletonList(costsMap);
	}
	
	private static List<String> getBullets() {
		return Arrays.asList("Shared HashMap Service");
	}

    private static List<String> getTags() {
        return Arrays.asList("hashmap", "Key-Value storage");
    }

	private Map<String, Object> getServiceDefinitionMetadata() {
		Map<String, Object> serviceDefinitionMetadata = new HashMap<>();
		serviceDefinitionMetadata.put("displayName", "HashMap Service");
		serviceDefinitionMetadata.put("imageUrl", "http://docs.spring.io/spring-boot/docs/1.4.1.RELEASE/reference/htmlsingle/images/note.png");
		serviceDefinitionMetadata.put("longDescription", "HashMap as a service on shared instance");
		serviceDefinitionMetadata.put("providerDisplayName", "Pivotal Cloud Foundry");
		serviceDefinitionMetadata.put("documentationUrl", "https://github.com/rohitpat/hashmap-service-broker");
		serviceDefinitionMetadata.put("supportUrl", "https://github.com/rohitpat/hashmap-service-broker");
		return serviceDefinitionMetadata;
	}
}

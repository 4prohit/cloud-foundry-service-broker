package com.cg.rp.cf.pivotal.hashmap.client.config;

import java.util.List;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.cg.rp.cf.pivotal.hashmap.client.cloud.HashMapServiceInfo;

/**
 * @author rohitpat
 *
 */
@Configuration
public class ServiceInfoConfig {
	// creating Cloud bean (required to get service information)
	@Bean
    Cloud cloud() {
        return new CloudFactory().getCloud();
    }

	// creating service info bean to connect bound service
	@Bean
    HashMapServiceInfo hashMapServiceInfo() {
        List<ServiceInfo> serviceInfos = cloud().getServiceInfos();
        for (ServiceInfo serviceInfo : serviceInfos) {
            if (serviceInfo instanceof HashMapServiceInfo) {
                return (HashMapServiceInfo) serviceInfo;
            }
        }
        throw new RuntimeException("Unable to find bound HashMap instance!");
    }
    
	// creating rest template bean to create REST client
	@Bean
    RestTemplate restTemplate() {
        //DefaultHttpClient httpClient = new DefaultHttpClient();
        BasicCredentialsProvider credentialsProvider =  new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(hashMapServiceInfo().getUsername(), hashMapServiceInfo().getPassword()));
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        ClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(clientHttpRequestFactory);
    }
}

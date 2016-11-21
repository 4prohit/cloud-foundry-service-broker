package com.cg.rp.cf.pivotal.servicebroker.hashmap.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.Credentials;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.HashMapServiceInstanceBinding;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.repository.HashMapServiceInstanceBindingRepository;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.repository.HashMapServiceInstanceRepository;

/**
 * @author rohitpat
 *
 */
@Service
public class HashMapServiceInstanceBindingServiceImpl implements ServiceInstanceBindingService {
	// service to process service binding creation and deletion requests
	
	private HashMapServiceInstanceRepository serviceInstanceRepository;
	private HashMapServiceInstanceBindingRepository serviceBindingRepository;
	private Cloud cloud;
	
	@Autowired
    public HashMapServiceInstanceBindingServiceImpl(HashMapServiceInstanceRepository serviceInstanceRepository, HashMapServiceInstanceBindingRepository serviceBindingRepository, Cloud cloud) {
		this.serviceInstanceRepository = serviceInstanceRepository;
		this.serviceBindingRepository = serviceBindingRepository;
		this.cloud = cloud;
	}

	@Override
	public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) {
		String serviceBindingId = request.getBindingId();
		String serviceInstanceId = request.getServiceInstanceId();

		if (!serviceInstanceRepository.exists(serviceInstanceId)) {
			throw new ServiceInstanceDoesNotExistException(serviceInstanceId);
		}
		HashMapServiceInstanceBinding serviceBinding = serviceBindingRepository.findOne(serviceBindingId);
		if (null != serviceBinding) {
			throw new ServiceInstanceBindingExistsException(serviceInstanceId, serviceBindingId);
		}

		// returning service credentials to requesting service to enable REST endpoint access
		Credentials credentials = new Credentials();
        credentials.setId(UUID.randomUUID().toString());
        credentials.setUri("http://" + serviceUri() + "/hashmap/" + serviceInstanceId);
        credentials.setUsername("user");
        credentials.setPassword("password");
        
        serviceBinding = new HashMapServiceInstanceBinding(serviceBindingId, serviceInstanceId, credentials, null, request.getBoundAppGuid());
		serviceBindingRepository.save(serviceBinding);
		
		return new CreateServiceInstanceAppBindingResponse().withCredentials(wrapCredentials(credentials));
	}

	@Override
	public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
		String serviceBindingId = request.getBindingId();
		HashMapServiceInstanceBinding serviceBinding = getServiceInstanceBinding(serviceBindingId);

		if (null == serviceBinding) {
			throw new ServiceInstanceBindingDoesNotExistException(serviceBindingId);
		}

		serviceBindingRepository.delete(serviceBindingId);
	}

	protected HashMapServiceInstanceBinding getServiceInstanceBinding(String serviceBindingId) {
		return serviceBindingRepository.findOne(serviceBindingId);
	}

	// getting service instance information information using Cloud bean
    private String serviceUri() {
        ApplicationInstanceInfo applicationInstanceInfo = cloud.getApplicationInstanceInfo();
        List<Object> uris = (List<Object>) applicationInstanceInfo.getProperties().get("uris");
        return uris.get(0).toString();
    }

    private Map<String, Object> wrapCredentials(Credentials credentials) {
        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("credentials", credentials);
        return wrapper;
    }
}

package com.cg.rp.cf.pivotal.servicebroker.hashmap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceExistsException;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.GetLastServiceOperationRequest;
import org.springframework.cloud.servicebroker.model.GetLastServiceOperationResponse;
import org.springframework.cloud.servicebroker.model.OperationState;
import org.springframework.cloud.servicebroker.model.UpdateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.UpdateServiceInstanceResponse;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.exception.HashMapServiceException;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.HashMapServiceInstance;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.repository.HashMapServiceInstanceRepository;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.service.HashMapService;

/**
 * @author rohitpat
 *
 */
@Service
public class HashMapServiceInstanceServiceImpl implements ServiceInstanceService {
	// service to process service instance creation and deletion requests
	
	private HashMapServiceInstanceRepository serviceInstanceRepository;
	private HashMapService hashMapService;
	
	@Autowired
    public HashMapServiceInstanceServiceImpl(HashMapServiceInstanceRepository serviceInstanceRepository, HashMapService hashMapService) {
		this.serviceInstanceRepository = serviceInstanceRepository;
		this.hashMapService = hashMapService;
	}

	@Override
	public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
		HashMapServiceInstance serviceInstance = serviceInstanceRepository.findOne(request.getServiceInstanceId());
		if (serviceInstance != null) {
			throw new ServiceInstanceExistsException(request.getServiceInstanceId(), request.getServiceDefinitionId());
		}

		serviceInstance = new HashMapServiceInstance(request);
		
		boolean isHashMapServiceInstanceExists = hashMapService.exists(serviceInstance.getServiceInstanceId());
		if (isHashMapServiceInstanceExists) {
            hashMapService.remove(serviceInstance.getServiceInstanceId());
        }
		
        hashMapService.create(serviceInstance.getServiceInstanceId());
		if (!hashMapService.exists(serviceInstance.getServiceInstanceId())) {
			throw new ServiceBrokerException("Failed to create new HashMap service serviceInstance: " + serviceInstance.getServiceInstanceId());
		}
		serviceInstanceRepository.save(serviceInstance);
		
		return new CreateServiceInstanceResponse();
	}

	@Override
	public GetLastServiceOperationResponse getLastOperation(GetLastServiceOperationRequest request) {
		return new GetLastServiceOperationResponse().withOperationState(OperationState.SUCCEEDED);
	}

	public HashMapServiceInstance getServiceInstance(String serviceInstanceId) {
		return serviceInstanceRepository.findOne(serviceInstanceId);
	}

	@Override
	public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) throws HashMapServiceException {
		String serviceInstanceId = request.getServiceInstanceId();
		HashMapServiceInstance serviceInstance = serviceInstanceRepository.findOne(serviceInstanceId);
		if (serviceInstance == null) {
			throw new ServiceInstanceDoesNotExistException(serviceInstanceId);
		}

		hashMapService.remove(serviceInstanceId);
		serviceInstanceRepository.delete(serviceInstanceId);
		return new DeleteServiceInstanceResponse();
	}

	@Override
	public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
		String serviceInstanceId = request.getServiceInstanceId();
		HashMapServiceInstance serviceInstance = serviceInstanceRepository.findOne(serviceInstanceId);
		if (serviceInstance == null) {
			throw new ServiceInstanceDoesNotExistException(serviceInstanceId);
		}

		serviceInstanceRepository.delete(serviceInstanceId);
		HashMapServiceInstance updatedHashMapServiceInstance = new HashMapServiceInstance(request);
		serviceInstanceRepository.save(updatedHashMapServiceInstance);
		return new UpdateServiceInstanceResponse();
	}

}

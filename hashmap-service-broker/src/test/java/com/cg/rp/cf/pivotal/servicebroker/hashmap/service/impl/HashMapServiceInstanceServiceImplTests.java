package com.cg.rp.cf.pivotal.servicebroker.hashmap.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceExistsException;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.HashMapServiceInstance;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.fixture.HashMapServiceInstanceFixture;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.repository.HashMapServiceInstanceRepository;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.service.HashMapService;

/**
 * @author rohitpat
 *
 */
@RunWith(SpringRunner.class)
public class HashMapServiceInstanceServiceImplTests {

	private static final String SVC_DEF_ID = "serviceDefinitionId";
	private static final String SVC_PLAN_ID = "servicePlanId";
	
	@Mock
	private HashMapServiceInstanceRepository serviceInstanceRepository;
	
	@Mock
	private HashMapService hashMapService;
	
	@Mock
	private ServiceDefinition serviceDefinition;
	
	private HashMapServiceInstanceServiceImpl service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new HashMapServiceInstanceServiceImpl(serviceInstanceRepository, hashMapService);
	}
	
	@Test
	public void shouldCreateNewServiceInstanceSuccessfully() throws Exception {
		when(serviceInstanceRepository.findOne(any(String.class))).thenReturn(null);
		when(serviceInstanceRepository.exists(any(String.class))).thenReturn(false);
		when(hashMapService.exists(any(String.class))).thenReturn(true);
		doNothing().when(hashMapService).create(any(String.class));

		CreateServiceInstanceResponse response = service.createServiceInstance(buildCreateRequest());
		
		assertNotNull(response);
		assertNull(response.getDashboardUrl());
		assertFalse(response.isAsync());

		verify(serviceInstanceRepository).save(isA(HashMapServiceInstance.class));
	}

	@Test
	public void shouldCreateNewServiceInstanceSuccessfullyWithExistingInstance() throws Exception {
		when(serviceInstanceRepository.findOne(any(String.class))).thenReturn(null);
		when(serviceInstanceRepository.exists(any(String.class))).thenReturn(false);
		when(hashMapService.exists(any(String.class))).thenReturn(true);
		doNothing().when(hashMapService).create(any(String.class));

		CreateServiceInstanceRequest request = buildCreateRequest();
		CreateServiceInstanceResponse response = service.createServiceInstance(request);

		assertNotNull(response);
		assertNull(response.getDashboardUrl());
		assertFalse(response.isAsync());

		verify(hashMapService).remove(request.getServiceInstanceId());
		verify(serviceInstanceRepository).save(isA(HashMapServiceInstance.class));
	}

	@Test(expected = ServiceInstanceExistsException.class)
	public void shouldFailServiceInstanceCreationWithExistingInstance() throws Exception {
		when(serviceInstanceRepository.findOne(any(String.class))).thenReturn(HashMapServiceInstanceFixture.getServiceInstance());

		service.createServiceInstance(buildCreateRequest());
	}

	@Test(expected = ServiceBrokerException.class)
	public void serviceInstanceCreationFailsWithHashMapCreationFailure() throws Exception {
		when(serviceInstanceRepository.findOne(any(String.class))).thenReturn(null);
		when(serviceInstanceRepository.exists(any(String.class))).thenReturn(false);
		when(hashMapService.exists(any(String.class))).thenReturn(false);
		doThrow(new ServiceBrokerException("Failed to create HashMap instance")).when(hashMapService).create(any(String.class));

		service.createServiceInstance(buildCreateRequest());
	}

	@Test
	public void shouldRetrievesServiceInstanceSuccessfully() {
		when(serviceInstanceRepository.findOne(any(String.class))).thenReturn(HashMapServiceInstanceFixture.getServiceInstance());
		String id = HashMapServiceInstanceFixture.getServiceInstance().getServiceInstanceId();
		assertEquals(id, service.getServiceInstance(id).getServiceInstanceId());
	}

	@Test
	public void shouldDeleteServiceInstanceSuccessfully() throws Exception {
		HashMapServiceInstance instance = HashMapServiceInstanceFixture.getServiceInstance();
		when(serviceInstanceRepository.findOne(any(String.class))).thenReturn(instance);
		String id = instance.getServiceInstanceId();

		DeleteServiceInstanceResponse response = service.deleteServiceInstance(buildDeleteRequest());

		assertNotNull(response);
		assertFalse(response.isAsync());

		verify(hashMapService).remove(id);
		verify(serviceInstanceRepository).delete(id);
	}

	@Test(expected = ServiceInstanceDoesNotExistException.class)
	public void shouldDeleteUnknownServiceInstanceSuccessfully() throws Exception {
		when(serviceInstanceRepository.findOne(any(String.class))).thenReturn(null);

		DeleteServiceInstanceRequest request = buildDeleteRequest();

		DeleteServiceInstanceResponse response = service.deleteServiceInstance(request);

		assertNotNull(response);
		assertFalse(response.isAsync());

		verify(hashMapService).remove(request.getServiceInstanceId());
		verify(serviceInstanceRepository).delete(request.getServiceInstanceId());
	}


	private CreateServiceInstanceRequest buildCreateRequest() {
		return new CreateServiceInstanceRequest(SVC_DEF_ID, SVC_PLAN_ID, "organizationGuid", "spaceGuid")
				.withServiceInstanceId(HashMapServiceInstanceFixture.getServiceInstance().getServiceInstanceId());
	}

	private DeleteServiceInstanceRequest buildDeleteRequest() {
		return new DeleteServiceInstanceRequest(HashMapServiceInstanceFixture.getServiceInstance().getServiceInstanceId(),
				SVC_DEF_ID, SVC_PLAN_ID, serviceDefinition);
	}

}

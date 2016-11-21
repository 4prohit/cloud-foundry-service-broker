package com.cg.rp.cf.pivotal.servicebroker.hashmap.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.ServiceBindingResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.HashMapServiceInstance;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.HashMapServiceInstanceBinding;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.fixture.HashMapServiceInstanceBindingFixture;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.fixture.HashMapServiceInstanceFixture;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.repository.HashMapServiceInstanceBindingRepository;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.repository.HashMapServiceInstanceRepository;

/**
 * @author rohitpat
 *
 */
@RunWith(SpringRunner.class)
public class HashMapServiceInstanceBindingServiceImplTests {

	@Mock
	private HashMapServiceInstanceRepository serviceInstanceRepository;
	
	@Mock
	private HashMapServiceInstanceBindingRepository serviceBindingRepository;
	
	@Mock
	private Cloud cloud;

	private HashMapServiceInstanceBindingServiceImpl service;
	
	private HashMapServiceInstance instance;
	private HashMapServiceInstanceBinding instanceBinding;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new HashMapServiceInstanceBindingServiceImpl(serviceInstanceRepository, serviceBindingRepository, cloud);
		instance = HashMapServiceInstanceFixture.getServiceInstance();
		instanceBinding = HashMapServiceInstanceBindingFixture.getServiceInstanceBinding();
	}

	/*@Test
	public void shouldCreateNewServiceInstanceBindingSuccessfully() throws Exception {
		when(serviceInstanceRepository.exists(any(String.class))).thenReturn(true);
		when(serviceBindingRepository.findOne(any(String.class))).thenReturn(null);
		// problem while calling HashMapServiceInstanceBindingServiceImpl internal private method
		CreateServiceInstanceAppBindingResponse response = (CreateServiceInstanceAppBindingResponse) service.createServiceInstanceBinding(buildCreateRequest());

		assertNotNull(response);
		assertNotNull(response.getCredentials());
		assertNull(response.getSyslogDrainUrl());

		verify(serviceBindingRepository).save(isA(HashMapServiceInstanceBinding.class));
	}*/

	@Test(expected = ServiceInstanceBindingExistsException.class)
	public void shouldFailServiceInstanceCreationWithExistingInstance() throws Exception {
		when(serviceInstanceRepository.exists(any(String.class))).thenReturn(true);
		when(serviceBindingRepository.findOne(any(String.class))).thenReturn(HashMapServiceInstanceBindingFixture.getServiceInstanceBinding());

		service.createServiceInstanceBinding(buildCreateRequest());
	}

	@Test
	public void shouldRetrieveServiceInstanceBindingSuccessfully() {
		HashMapServiceInstanceBinding binding = HashMapServiceInstanceBindingFixture.getServiceInstanceBinding();
		when(serviceBindingRepository.findOne(any(String.class))).thenReturn(binding);

		assertEquals(binding.getServiceBindingId(), service.getServiceInstanceBinding(binding.getServiceBindingId()).getServiceBindingId());
	}

	@Test
	public void shouldDeleteServiceInstanceBindingSuccessfully() throws Exception {
		HashMapServiceInstanceBinding binding = HashMapServiceInstanceBindingFixture.getServiceInstanceBinding();
		when(serviceBindingRepository.findOne(any(String.class))).thenReturn(binding);

		service.deleteServiceInstanceBinding(buildDeleteRequest());

		verify(serviceBindingRepository).delete(binding.getServiceBindingId());
	}

	@Test(expected = ServiceInstanceBindingDoesNotExistException.class)
	public void shouldDeleteUnknownServiceInstanceSuccessfully() throws Exception {
		HashMapServiceInstanceBinding binding = HashMapServiceInstanceBindingFixture.getServiceInstanceBinding();
		when(serviceBindingRepository.findOne(any(String.class))).thenReturn(null);

		service.deleteServiceInstanceBinding(buildDeleteRequest());

		verify(serviceBindingRepository, never()).delete(binding.getServiceBindingId());
	}

	private CreateServiceInstanceBindingRequest buildCreateRequest() {
		Map<String, Object> bindResource =
				Collections.singletonMap(ServiceBindingResource.BIND_RESOURCE_KEY_APP.toString(), (Object) "app_guid");
		return new CreateServiceInstanceBindingRequest(instance.getServiceDefinitionId(), instance.getPlanId(),
				"app_guid", bindResource)
				.withServiceInstanceId(instance.getServiceInstanceId())
				.withBindingId(instanceBinding.getServiceBindingId());
	}

	private DeleteServiceInstanceBindingRequest buildDeleteRequest() {
		return new DeleteServiceInstanceBindingRequest(instance.getServiceInstanceId(), instanceBinding.getServiceBindingId(),
				instance.getServiceDefinitionId(), instance.getPlanId(), null);
	}

}

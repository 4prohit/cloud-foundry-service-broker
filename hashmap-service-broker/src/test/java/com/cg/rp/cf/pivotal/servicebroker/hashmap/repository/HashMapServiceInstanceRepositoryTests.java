package com.cg.rp.cf.pivotal.servicebroker.hashmap.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.HashMapServiceInstance;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.fixture.HashMapServiceInstanceFixture;

/**
 * @author rohitpat
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class HashMapServiceInstanceRepositoryTests {
	
	@Autowired
	private HashMapServiceInstanceRepository repository;

	@Test
	public void shouldSaveServiceInstanceSuccessfully() throws Exception {
		assertEquals(0, repository.count());
		repository.save(HashMapServiceInstanceFixture.getServiceInstance());
		assertEquals(1, repository.count());
	}

	@Test
	public void shouldDeleteServiceInstanceSuccessfully() throws Exception {
		HashMapServiceInstance instance = HashMapServiceInstanceFixture.getServiceInstance();

		assertEquals(0, repository.count());
		repository.save(instance);
		assertEquals(1, repository.count());
		repository.delete(instance.getServiceInstanceId());
		assertEquals(0, repository.count());
	}

	@Test
	public void shouldFindServiceInstanceByIdSuccessfully() throws Exception {
		HashMapServiceInstance instance = HashMapServiceInstanceFixture.getServiceInstance();

		assertEquals(0, repository.count());
		repository.save(instance);
		assertEquals(1, repository.count());
		assertNotNull(repository.findOne(instance.getServiceInstanceId()));
	}
}

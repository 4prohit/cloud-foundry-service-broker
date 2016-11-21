package com.cg.rp.cf.pivotal.servicebroker.hashmap.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.HashMapServiceInstanceBinding;
import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.fixture.HashMapServiceInstanceBindingFixture;

/**
 * @author rohitpat
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class HashMapServiceInstanceBindingRepositoryTests {
	@Autowired
	private HashMapServiceInstanceBindingRepository repository;

	@Test
	public void shouldSaveServiceInstanceBindingSuccessfully() throws Exception {
		assertEquals(0, repository.count());
		repository.save(HashMapServiceInstanceBindingFixture.getServiceInstanceBinding());
		assertEquals(1, repository.count());
	}

	@Test
	public void shouldDeleteServiceInstanceBindingSuccessfully() throws Exception {
		HashMapServiceInstanceBinding instance = HashMapServiceInstanceBindingFixture.getServiceInstanceBinding();

		assertEquals(0, repository.count());
		repository.save(instance);
		assertEquals(1, repository.count());
		repository.delete(instance.getServiceBindingId());
		assertEquals(0, repository.count());
	}

	@Test
	public void shouldFindServiceInstanceBindingByIdSuccessfully() throws Exception {
		HashMapServiceInstanceBinding instance = HashMapServiceInstanceBindingFixture.getServiceInstanceBinding();

		assertEquals(0, repository.count());
		repository.save(instance);
		assertEquals(1, repository.count());
		assertNotNull(repository.findOne(instance.getServiceBindingId()));
	}
}

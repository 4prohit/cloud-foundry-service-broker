package com.cg.rp.cf.pivotal.servicebroker.hashmap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.HashMapServiceInstance;

/**
 * @author rohitpat
 *
 */
@Repository
public interface HashMapServiceInstanceRepository extends CrudRepository<HashMapServiceInstance, String> {
	// repository to persist service instance
}

package com.cg.rp.cf.pivotal.servicebroker.hashmap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.HashMapServiceInstanceBinding;

/**
 * @author rohitpat
 *
 */
@Repository
public interface HashMapServiceInstanceBindingRepository extends CrudRepository<HashMapServiceInstanceBinding, String> {
	// repository to persist service binding
}

package com.cg.rp.cf.pivotal.servicebroker.hashmap.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.rp.cf.pivotal.servicebroker.hashmap.model.CustomHashMap;

/**
 * @author rohitpat
 *
 */
@RunWith(SpringRunner.class)
public class HashMapServiceImplTests {
	
	private CustomHashMap<String, CustomHashMap<Object, Object>> customHashMap;
	
	@Before
	public void setup() {
		customHashMap = new CustomHashMap<>();
	}

	@Test
    public void shouldCreateHashMapInstance() {
		customHashMap.put("1234", new CustomHashMap<Object, Object>());
        assertNotNull(customHashMap.get("1234"));
    }

	@Test
    public void shouldRemoveHashMapInstance() {
		customHashMap.put("1234", new CustomHashMap<Object, Object>());
		assertNotNull(customHashMap.get("1234"));
		customHashMap.remove("1234");
        assertNull(customHashMap.get("1234"));
    }

	@Test
    public void shouldPutHashMapEntry() {
		customHashMap.put("1234", new CustomHashMap<Object, Object>());
		assertNotNull(customHashMap.get("1234"));
		customHashMap.get("1234").put("name", "Rohit Patil");
		assertNotNull(customHashMap.get("1234").get("name"));
		assertEquals("Rohit Patil", customHashMap.get("1234").get("name"));
    }

	@Test
    public void shouldGetHashMapEntryValue() {
		customHashMap.put("1234", new CustomHashMap<Object, Object>());
		assertNotNull(customHashMap.get("1234"));
		customHashMap.get("1234").put("name", "Rohit Patil");
		assertNotNull(customHashMap.get("1234").get("name"));
		assertEquals("Rohit Patil", customHashMap.get("1234").get("name"));assertNotNull(customHashMap.get("1234").get("name"));
		assertEquals("Rohit Patil", customHashMap.get("1234").get("name"));
    }

	@Test
    public void shouldRemoveHashMapEntry() {
		customHashMap.put("1234", new CustomHashMap<Object, Object>());
		assertNotNull(customHashMap.get("1234"));
		customHashMap.get("1234").put("name", "Rohit Patil");
		assertNotNull(customHashMap.get("1234").get("name"));
		assertEquals("Rohit Patil", customHashMap.get("1234").get("name"));
		customHashMap.get("1234").remove("name");
        assertNull(customHashMap.get("1234").get("name"));
    }

	@Test
    public void shouldCheckIfHashMapInstanceExists() {
		customHashMap.put("1234", new CustomHashMap<Object, Object>());
		assertNotNull(customHashMap.get("1234"));
    }
}

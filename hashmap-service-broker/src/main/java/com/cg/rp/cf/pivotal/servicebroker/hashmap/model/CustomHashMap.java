package com.cg.rp.cf.pivotal.servicebroker.hashmap.model;

/**
 * @author rohitpat
 *
 * @param <K>
 * @param <V>
 */
public class CustomHashMap<K, V> {
	private Entry<K, V>[] table; // Array of Entry
	private int capacity = 10; // Initial capacity of CustomHashMap

	static class Entry<K, V> {
		K key;
		V value;
		Entry<K, V> next; // reference to next Entry

		public Entry(K key, V value, Entry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}
    
    @SuppressWarnings("unchecked")
    public CustomHashMap() {
    	table = new Entry[this.capacity];
    }

	@SuppressWarnings("unchecked")
	public CustomHashMap(int capacity) {
		table = new Entry[capacity];
	}

	public void put(K newKey, V data) {
		if (newKey == null)
			return; // does not allow to store null.

		// calculate hash of key.
		int hash = hash(newKey);
		// create new entry.
		Entry<K, V> newEntry = new Entry<K, V>(newKey, data, null);

		// if table location does not contain any entry, store entry there.
		if (table[hash] == null) {
			table[hash] = newEntry;
		} else {
			Entry<K, V> previous = null;
			Entry<K, V> current = table[hash];

			while (current != null) { // we have reached last entry of bucket.
				if (current.key.equals(newKey)) {
					if (previous == null) { // node has to be insert on first of
											// bucket.
						newEntry.next = current.next;
						table[hash] = newEntry;
						return;
					} else {
						newEntry.next = current.next;
						previous.next = newEntry;
						return;
					}
				}
				previous = current;
				current = current.next;
			}
			previous.next = newEntry;
		}
	}

	public V get(K key) {
		int hash = hash(key);
		if (table[hash] == null) {
			return null;
		} else {
			Entry<K, V> temp = table[hash];
			while (temp != null) {
				if (temp.key.equals(key))
					return temp.value;
				temp = temp.next; // return value corresponding to key.
			}
			return null; // returns null if key is not found.
		}
	}

	public boolean remove(K deleteKey) {

		int hash = hash(deleteKey);

		if (table[hash] == null) {
			return false;
		} else {
			Entry<K, V> previous = null;
			Entry<K, V> current = table[hash];

			while (current != null) { // we have reached last entry node of
										// bucket.
				if (current.key.equals(deleteKey)) {
					if (previous == null) { // delete first entry node.
						table[hash] = table[hash].next;
						return true;
					} else {
						previous.next = current.next;
						return true;
					}
				}
				previous = current;
				current = current.next;
			}
			return false;
		}

	}

	// hash function to get bucket location
	private int hash(K key) {
		return Math.abs(key.hashCode()) % capacity;
	}

}

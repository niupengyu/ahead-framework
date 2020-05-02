package com.github.niupengyu.core.message;

import java.util.*;

public class CacheMap<K, V> extends AbstractMap<K, V> {
 
	private long DEFAULT_TIMEOUT = 30000;

	public CacheMap(long timeout) {
		this.cacheTimeout = timeout;
		new ClearThread().start();
	}

	public CacheMap(){

	}


	private class CacheEntry implements Entry<K, V> {
		long time;
		V value;
		K key;
 
		CacheEntry(K key, V value) {
			super();
			this.value = value;
			this.key = key;
			this.time = System.currentTimeMillis();
		}
 
		@Override
		public K getKey() {
			return key;
		}
 
		@Override
		public V getValue() {
			return value;
		}
 
		@Override
		public V setValue(V value) {
			return this.value = value;
		}
	}
 
	private class ClearThread extends Thread {
		ClearThread() {
			setName("clear cache thread");
		}
 
		public void run() {
			while (true) {
				try {
					long now = System.currentTimeMillis();
					Object[] keys = map.keySet().toArray();
					for (Object key : keys) {
						CacheEntry entry = map.get(key);
						if (now - entry.time >= cacheTimeout) {
							synchronized (map) {
								map.remove(key);
							}
						}
					}
					Thread.sleep(cacheTimeout);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
 
	private long cacheTimeout;
	private Map<K, CacheEntry> map = new HashMap<>();
 

 
	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> entrySet = new HashSet<>();
		Set<Entry<K, CacheEntry>> wrapEntrySet = map.entrySet();
		for (Entry<K, CacheEntry> entry : wrapEntrySet) {
			entrySet.add(entry.getValue());
		}
		return entrySet;
	}
 
	@Override
	public V get(Object key) {
		CacheEntry entry = map.get(key);
		return entry == null ? null : entry.value;
	}
 
	@Override
	public V put(K key, V value) {
		CacheEntry entry = new CacheEntry(key, value);
		synchronized (map) {
			map.put(key, entry);
		}
		return value;
	}
 
}

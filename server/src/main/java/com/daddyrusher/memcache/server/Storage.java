package com.daddyrusher.memcache.server;

import com.daddyrusher.memcache.model.Status;

public interface Storage extends AutoCloseable {

    Status put(String key, Long ttl, byte[] data);

    byte[] get(String key);

    Status remove(String key);

    Status clear();
}

package com.daddyrusher.jmemcached.server;

import com.daddyrusher.jmemcached.model.Status;

public interface Storage extends AutoCloseable {

    Status put(String key, Long ttl, byte[] data);

    byte[] get(String key);

    Status remove(String key);

    Status clear();
}

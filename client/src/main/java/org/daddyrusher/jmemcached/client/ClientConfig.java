package com.daddyrusher.jmemcached.client;

import com.daddyrusher.jmemcached.protocol.ObjectSerializer;
import com.daddyrusher.jmemcached.protocol.RequestConverter;
import com.daddyrusher.jmemcached.protocol.ResponseConverter;

public interface ClientConfig {
    String getHost();

    int getPort();

    RequestConverter getRequestConverter();

    ResponseConverter getResponseConverter();

    ObjectSerializer getObjectSerializer();
}

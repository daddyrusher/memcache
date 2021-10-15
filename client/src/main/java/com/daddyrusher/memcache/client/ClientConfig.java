package com.daddyrusher.memcache.client;

import com.daddyrusher.memcache.protocol.ObjectSerializer;
import com.daddyrusher.memcache.protocol.RequestConverter;
import com.daddyrusher.memcache.protocol.ResponseConverter;

public interface ClientConfig {
    String getHost();

    int getPort();

    RequestConverter getRequestConverter();

    ResponseConverter getResponseConverter();

    ObjectSerializer getObjectSerializer();
}

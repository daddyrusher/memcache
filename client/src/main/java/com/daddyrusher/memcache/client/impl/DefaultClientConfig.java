package com.daddyrusher.memcache.client.impl;

import com.daddyrusher.memcache.client.ClientConfig;
import com.daddyrusher.memcache.protocol.ObjectSerializer;
import com.daddyrusher.memcache.protocol.RequestConverter;
import com.daddyrusher.memcache.protocol.ResponseConverter;
import com.daddyrusher.memcache.protocol.impl.DefaultObjectSerializer;
import com.daddyrusher.memcache.protocol.impl.DefaultRequestConverter;
import com.daddyrusher.memcache.protocol.impl.DefaultResponseConverter;

class DefaultClientConfig implements ClientConfig {
    private final String host;
    private final int port;
    private final RequestConverter requestConverter;
    private final ResponseConverter responseConverter;
    private final ObjectSerializer objectSerializer;

    DefaultClientConfig(String host, int port) {
        this.host = host;
        this.port = port;
        this.requestConverter = new DefaultRequestConverter();
        this.responseConverter = new DefaultResponseConverter();
        this.objectSerializer = new DefaultObjectSerializer();
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public RequestConverter getRequestConverter() {
        return requestConverter;
    }

    @Override
    public ResponseConverter getResponseConverter() {
        return responseConverter;
    }

    @Override
    public ObjectSerializer getObjectSerializer() {
        return objectSerializer;
    }
}

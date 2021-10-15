package com.daddyrusher.jmemcached.client.impl;

import com.daddyrusher.jmemcached.client.ClientConfig;
import com.daddyrusher.jmemcached.protocol.ObjectSerializer;
import com.daddyrusher.jmemcached.protocol.RequestConverter;
import com.daddyrusher.jmemcached.protocol.ResponseConverter;
import com.daddyrusher.jmemcached.protocol.impl.DefaultObjectSerializer;
import com.daddyrusher.jmemcached.protocol.impl.DefaultRequestConverter;
import com.daddyrusher.jmemcached.protocol.impl.DefaultResponseConverter;

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

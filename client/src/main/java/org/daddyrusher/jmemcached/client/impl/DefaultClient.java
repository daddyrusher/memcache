package com.daddyrusher.jmemcached.client.impl;

import com.daddyrusher.jmemcached.client.Client;
import com.daddyrusher.jmemcached.client.ClientConfig;
import com.daddyrusher.jmemcached.model.Command;
import com.daddyrusher.jmemcached.model.Request;
import com.daddyrusher.jmemcached.model.Response;
import com.daddyrusher.jmemcached.model.Status;
import com.daddyrusher.jmemcached.protocol.ObjectSerializer;
import com.daddyrusher.jmemcached.protocol.RequestConverter;
import com.daddyrusher.jmemcached.protocol.ResponseConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

class DefaultClient implements Client {
    private final ObjectSerializer objectSerializer;
    private final RequestConverter requestConverter;
    private final ResponseConverter responseConverter;

    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    DefaultClient(ClientConfig config) throws IOException {
        this.objectSerializer = config.getObjectSerializer();
        this.requestConverter = config.getRequestConverter();
        this.responseConverter = config.getResponseConverter();
        this.socket = createSocket(config);
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    protected Socket createSocket(ClientConfig config) throws IOException {
        Socket socket = new Socket(config.getHost(), config.getPort());
        socket.setKeepAlive(true);
        return socket;
    }

    protected Response makeRequest(Request request) throws IOException {
        requestConverter.writeRequest(outputStream, request);
        return responseConverter.readResponse(inputStream);
    }

    @Override
    public Status put(String key, Object object) throws IOException {
        return put(key, object, null, null);
    }

    @Override
    public Status put(String key, Object object, Integer ttl, TimeUnit timeUnit) throws IOException {
        byte[] data = objectSerializer.toByteArray(object);
        var requestTtl = ttl != null && timeUnit != null ? timeUnit.toMillis(ttl) : null;
        var response = makeRequest(new Request(Command.PUT, key, requestTtl, data));
        return response.getStatus();
    }

    @Override
    public <T> T get(String key) throws IOException {
        var response = makeRequest(new Request(Command.GET, key));
        return (T) objectSerializer.fromByteArray(response.getData());
    }

    @Override
    public Status remove(String key) throws IOException {
        var response = makeRequest(new Request(Command.REMOVE, key));
        return response.getStatus();
    }

    @Override
    public Status clear() throws IOException {
        var response = makeRequest(new Request(Command.CLEAR));
        return response.getStatus();
    }

    @Override
    public void close() throws Exception {
        this.socket.close();
    }
}

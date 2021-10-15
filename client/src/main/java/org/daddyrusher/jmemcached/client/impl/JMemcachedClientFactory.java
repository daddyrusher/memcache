package com.daddyrusher.jmemcached.client.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.daddyrusher.jmemcached.client.Client;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JMemcachedClientFactory {
    public static Client makeClient(String host, int port) throws IOException {
        return new DefaultClient(new DefaultClientConfig(host, port));
    }

    public static Client makeClient(String host) throws IOException {
        return makeClient(host, 9012);
    }

    public static Client defaultClient() throws IOException {
        return makeClient("localhost");
    }
}

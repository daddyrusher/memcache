package com.daddyrusher.jmemcached.server.impl;

import com.daddyrusher.jmemcached.server.Server;

import java.util.Properties;

public class JMemcachedServerFactory {

    public static Server buildNewServer(Properties properties) {
        return new DefaultServer(new DefaultServerConfig(properties));
    }
}

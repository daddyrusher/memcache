package com.daddyrusher.memcache.server.impl;

import com.daddyrusher.memcache.server.Server;

import java.util.Properties;

public class JMemcachedServerFactory {

    public static Server buildNewServer(Properties properties) {
        return new DefaultServer(new DefaultServerConfig(properties));
    }
}

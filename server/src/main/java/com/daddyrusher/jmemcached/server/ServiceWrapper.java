package com.daddyrusher.jmemcached.server;

import com.daddyrusher.jmemcached.server.impl.JMemcachedServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServiceWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceWrapper.class);
    private static final Server SERVER = createServer();

    private static Server createServer() {
        return JMemcachedServerFactory.buildNewServer(getServerProperties());
    }

    private static Properties getServerProperties() {
        Properties properties = new Properties();
        String pathToServerProperties = System.getProperty("server-prop");

        try (InputStream inputStream = new FileInputStream(pathToServerProperties)) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Can't load server properties " + e.getMessage(), e);
        }

        return properties;
    }

    public static void main(String[] args) {
        if ("start".equals(args[0])) {
            start();
        } else if ("stop".equals(args[0])) {
            stop();
        }
    }

    private static void start() {
        SERVER.start();
    }

    private static void stop() {
        SERVER.stop();
    }
}

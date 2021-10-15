package com.daddyrusher.memcache.server.impl;

import com.daddyrusher.memcache.server.ClientSocketHandler;
import com.daddyrusher.memcache.server.CommandHandler;
import com.daddyrusher.memcache.server.ServerConfig;
import com.daddyrusher.memcache.server.Storage;
import com.daddyrusher.memcache.exception.JMemCachedConfigException;
import com.daddyrusher.memcache.protocol.RequestConverter;
import com.daddyrusher.memcache.protocol.ResponseConverter;
import com.daddyrusher.memcache.protocol.impl.DefaultRequestConverter;
import com.daddyrusher.memcache.protocol.impl.DefaultResponseConverter;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.concurrent.ThreadFactory;

class DefaultServerConfig implements ServerConfig {
    private final Properties appProperties = new Properties();
    private final RequestConverter requestConverter;
    private final ResponseConverter responseConverter;
    private final CommandHandler commandHandler;
    private final Storage storage;

    DefaultServerConfig(Properties properties) {
        loadProperties("server.properties");

        if (properties != null) {
            appProperties.putAll(properties);
        }
        this.requestConverter = createRequestConverter();
        this.responseConverter = createResponseConverter();
        this.storage = createStorage();
        this.commandHandler = createCommandHandler();
    }

    protected RequestConverter createRequestConverter() {
        return new DefaultRequestConverter();
    }

    protected ResponseConverter createResponseConverter() {
        return new DefaultResponseConverter();
    }

    protected Storage createStorage() {
        return new DefaultStorage(this);
    }

    protected CommandHandler createCommandHandler() {
        return new DefaultCommandHandler(this);
    }

    protected InputStream getClasspathResourceInputStream(String classPathResource) {
        return getClass().getClassLoader().getResourceAsStream(classPathResource);
    }

    protected void loadProperties(String resource) {
        try (InputStream inputStream = getClasspathResourceInputStream(resource)) {
            if (inputStream == null) {
                throw new JMemCachedConfigException("Class path resource not found");
            }

            appProperties.load(inputStream);
        } catch (IOException e) {
            throw new JMemCachedConfigException("Can't load properties from resource " + resource, e);
        }
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
    public ThreadFactory getThreadFactory() {
        return new ThreadFactory() {
            private int threadCount = 0;

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "worker-" + threadCount);
                threadCount++;
                thread.setDaemon(true);
                return thread;
            }
        };
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    @Override
    public int getClearDataIntervalInMs() {
        String value = appProperties.getProperty("storage.clear.data.interval");
        try {
            int interval = Integer.parseInt(value);
            if (interval < 1000) {
                throw new JMemCachedConfigException("storage.clear.data.interval should be greater than 1000 ms");
            }
            return interval;
        } catch (NumberFormatException e) {
            throw new JMemCachedConfigException("storage.clear.data.interval should be a number", e);
        }
    }

    @Override
    public int getServerPort() {
        String value = appProperties.getProperty("server.port");
        try {
            int port = Integer.parseInt(value);
            if (port < 0 || port > 65535) {
                throw new JMemCachedConfigException("server.port should be between 0 and 65535");
            }
            return port;
        } catch (NumberFormatException e) {
            throw new JMemCachedConfigException("server.port should be a number", e);
        }
    }

    protected int getThreadCount(String propertyName) {
        String value = appProperties.getProperty(propertyName);
        try {
            int threadCount = Integer.parseInt(value);
            if (threadCount < 1) {
                throw new JMemCachedConfigException(propertyName + " should be equal or greater than 1");
            }
            return threadCount;
        } catch (NumberFormatException e) {
            throw new JMemCachedConfigException(propertyName + " should be a number", e);
        }
    }

    @Override
    public int getInitThreadCount() {
        return getThreadCount("server.init.thread");
    }

    @Override
    public int getMaxThreadCount() {
        return getThreadCount("server.max.thread.count");
    }

    @Override
    public ClientSocketHandler buildNewClientSocketHandler(Socket clientSocket) {
        return new DefaultClientSocketHandler(clientSocket, this);
    }

    @Override
    public void close() throws Exception {
        storage.close();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DefaultServerConfig.class.getSimpleName() + "[", "]")
                .add("port=" + getServerPort())
                .add("initThreadCount=" + getInitThreadCount())
                .add("maxThreadCount=" + getMaxThreadCount())
                .add("clearDataIntervalInMs=" + getClearDataIntervalInMs())
                .toString();
    }
}

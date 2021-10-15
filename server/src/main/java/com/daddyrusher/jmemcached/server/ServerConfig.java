package com.daddyrusher.jmemcached.server;

import com.daddyrusher.jmemcached.protocol.RequestConverter;
import com.daddyrusher.jmemcached.protocol.ResponseConverter;

import java.net.Socket;
import java.util.concurrent.ThreadFactory;

public interface ServerConfig extends AutoCloseable {
    RequestConverter getRequestConverter();

    ResponseConverter getResponseConverter();

    ThreadFactory getThreadFactory();

    Storage getStorage();

    CommandHandler getCommandHandler();

    int getClearDataIntervalInMs();

    int getServerPort();

    int getInitThreadCount();

    int getMaxThreadCount();

    ClientSocketHandler buildNewClientSocketHandler(Socket clientSocket);
}

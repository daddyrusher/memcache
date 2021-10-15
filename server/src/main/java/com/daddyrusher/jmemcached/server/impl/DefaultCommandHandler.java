package com.daddyrusher.jmemcached.server.impl;

import com.daddyrusher.jmemcached.server.CommandHandler;
import com.daddyrusher.jmemcached.server.ServerConfig;
import com.daddyrusher.jmemcached.server.Storage;
import com.daddyrusher.jmemcached.exception.JMemcachedException;
import com.daddyrusher.jmemcached.model.Command;
import com.daddyrusher.jmemcached.model.Request;
import com.daddyrusher.jmemcached.model.Response;
import com.daddyrusher.jmemcached.model.Status;

class DefaultCommandHandler implements CommandHandler {

    private final Storage storage;

    DefaultCommandHandler(ServerConfig config) {
        this.storage = config.getStorage();
    }

    @Override
    public Response handle(Request request) {
        Status status;
        byte[] data = null ;

        if (request.getCommand() == Command.CLEAR) {
            status = storage.clear();
        } else if (request.getCommand() == Command.PUT) {
            status = storage.put(request.getKey(), request.getTtl(), request.getData());
        } else if (request.getCommand() == Command.REMOVE) {
            status = storage.remove(request.getKey());
        } else if (request.getCommand() == Command.GET) {
            data = storage.get(request.getKey());
            status = data == null ? Status.NOT_FOUND : Status.GOTTEN;
        } else {
             throw new JMemcachedException("Unsupported command + " + request.getCommand());
        }

        return new Response(status, data);
    }
}

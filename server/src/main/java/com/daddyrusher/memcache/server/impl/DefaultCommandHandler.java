package com.daddyrusher.memcache.server.impl;

import com.daddyrusher.memcache.server.CommandHandler;
import com.daddyrusher.memcache.server.ServerConfig;
import com.daddyrusher.memcache.server.Storage;
import com.daddyrusher.memcache.exception.JMemcachedException;
import com.daddyrusher.memcache.model.Command;
import com.daddyrusher.memcache.model.Request;
import com.daddyrusher.memcache.model.Response;
import com.daddyrusher.memcache.model.Status;

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

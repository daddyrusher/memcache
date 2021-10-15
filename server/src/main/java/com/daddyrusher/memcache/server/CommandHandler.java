package com.daddyrusher.memcache.server;

import com.daddyrusher.memcache.model.Request;
import com.daddyrusher.memcache.model.Response;

public interface CommandHandler {

    Response handle(Request request);
}

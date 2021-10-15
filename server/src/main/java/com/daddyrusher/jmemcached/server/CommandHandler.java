package com.daddyrusher.jmemcached.server;

import com.daddyrusher.jmemcached.model.Request;
import com.daddyrusher.jmemcached.model.Response;

public interface CommandHandler {

    Response handle(Request request);
}

package com.daddyrusher.memcache.protocol;

import com.daddyrusher.memcache.model.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ResponseConverter {
    Response readResponse(InputStream inputStream) throws IOException;

    void writeResponse(OutputStream outputStream, Response response) throws IOException;
}

package com.daddyrusher.memcache.protocol.impl;

import org.apache.commons.io.IOUtils;
import com.daddyrusher.memcache.exception.JMemcachedException;
import com.daddyrusher.memcache.model.Command;
import com.daddyrusher.memcache.model.Request;
import com.daddyrusher.memcache.protocol.RequestConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DefaultRequestConverter extends AbstractPackageConverter implements RequestConverter {
    @Override
    public Request readRequest(InputStream inputStream) throws IOException {
        var dataInputStream = new DataInputStream(inputStream);
        checkProtocolVersion(dataInputStream.readByte());
        var command = dataInputStream.readByte();
        var data = dataInputStream.readByte();
        var hasKey = (data & 1) != 0;
        var hasTtl = (data & 2) != 0;
        var hasData = (data & 4) != 0;
        return readRequest(command, hasKey, hasTtl, hasData, dataInputStream);
    }

    protected Request readRequest(byte command,
                                  boolean hasKey,
                                  boolean hasTtl,
                                  boolean hasData,
                                  DataInputStream dataInputStream) throws IOException {
        var request = new Request(Command.valueOf(command));
        if (hasKey) {
            byte keyLength = dataInputStream.readByte();
            byte[] keyBytes = IOUtils.readFully(dataInputStream, keyLength);
            request.setKey(new String(keyBytes, StandardCharsets.US_ASCII));
        }

        if (hasTtl) {
            request.setTtl(dataInputStream.readLong());
        }

        if (hasData) {
            int dataLength = dataInputStream.readInt();
            request.setData(IOUtils.readFully(dataInputStream, dataLength));
        }

        return request;
    }

    @Override
    public void writeRequest(OutputStream outputStream, Request request) throws IOException {
        var dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeByte(getVersionByte());
        dataOutputStream.writeByte(request.getCommand().getCode());
        dataOutputStream.writeByte(getFlagsByte(request));
        if (request.hasKey()) {
            writeKey(dataOutputStream, request);
        }

        if (request.hasTtl()) {
            dataOutputStream.writeLong(request.getTtl());
        }

        if (request.hasData()) {
            dataOutputStream.writeInt(request.getData().length);
            dataOutputStream.write(request.getData());
        }

        dataOutputStream.flush();
    }

    protected byte getFlagsByte(Request request) {
        byte flags = 0;

        if (request.hasKey()) {
            flags |= 1;
        }

        if (request.hasTtl()) {
            flags |= 2;
        }

        if (request.hasData()) {
            flags |= 4;
        }

        return flags;
    }

    protected void writeKey(DataOutputStream dataOutputStream, Request request) throws IOException {
        byte[] key = request.getKey().getBytes(StandardCharsets.US_ASCII);
        if (key.length > 127) {
            throw new JMemcachedException("Key length should be less than 127 for key: " + request.getKey());
        }

        dataOutputStream.writeByte(key.length);
        dataOutputStream.write(key);
    }
}

package com.daddyrusher.memcache.protocol.impl;

import org.apache.commons.io.IOUtils;
import com.daddyrusher.memcache.model.Response;
import com.daddyrusher.memcache.model.Status;
import com.daddyrusher.memcache.protocol.ResponseConverter;

import java.io.*;

public class DefaultResponseConverter extends AbstractPackageConverter implements ResponseConverter {
    @Override
    public Response readResponse(InputStream inputStream) throws IOException {
        var dataInputStream = new DataInputStream(inputStream);
        checkProtocolVersion(dataInputStream.readByte());
        byte status = dataInputStream.readByte();
        var response = new Response(Status.valueOf(status));
        byte data = dataInputStream.readByte();

        if (data == 1) {
            int dataLength = dataInputStream.readInt();
            response.setData(IOUtils.readFully(dataInputStream, dataLength));
        }

        return response;
    }

    @Override
    public void writeResponse(OutputStream outputStream, Response response) throws IOException {
        var dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeByte(getVersionByte());
        dataOutputStream.writeByte(response.getStatus().getCode());
        dataOutputStream.writeByte(response.hasData() ? 1 : 0);

        if (response.hasData()) {
            dataOutputStream.writeInt(response.getData().length);
            dataOutputStream.write(response.getData());
            dataOutputStream.flush();
        }
    }
}

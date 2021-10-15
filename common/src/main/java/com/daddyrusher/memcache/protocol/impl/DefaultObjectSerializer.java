package com.daddyrusher.memcache.protocol.impl;

import com.daddyrusher.memcache.exception.JMemcachedException;
import com.daddyrusher.memcache.protocol.ObjectSerializer;

import java.io.*;

public class DefaultObjectSerializer implements ObjectSerializer {
    @Override
    public byte[] toByteArray(Object object) {
        if (object == null) {
            return null;
        }

        if (!(object instanceof Serializable)) {
            throw new JMemcachedException("Class " + object.getClass().getName() + " has not implement Serializable!");
        }

        try (var byteArrayOutputStream = new ByteArrayOutputStream();
             var outputStream = new ObjectOutputStream(byteArrayOutputStream)) {

            outputStream.writeObject(object);
            outputStream.flush();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new JMemcachedException("Can't convert object to byte array\n" + e.getMessage(), e);
        }
    }

    @Override
    public Object fromByteArray(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        try (var inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new JMemcachedException("Can't convert byte array to object\n" + e.getMessage(), e);
        }
    }
}

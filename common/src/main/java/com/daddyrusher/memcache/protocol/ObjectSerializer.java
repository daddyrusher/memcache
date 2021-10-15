package com.daddyrusher.memcache.protocol;

public interface ObjectSerializer {

    byte[] toByteArray(Object object);

    Object fromByteArray(byte[] bytes);
}

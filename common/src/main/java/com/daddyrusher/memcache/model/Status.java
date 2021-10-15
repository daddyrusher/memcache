package com.daddyrusher.memcache.model;

import lombok.Getter;
import com.daddyrusher.memcache.exception.JMemcachedException;

import java.util.Arrays;

@Getter
public enum Status {
    ADDED(0),
    REPLACED(1),
    GOTTEN(2),
    NOT_FOUND(3),
    REMOVED(4),
    CLEARED(5);

    private final byte code;

    Status(int code) {
        this.code = (byte) code;
    }

    public static Status valueOf(byte byteCode) {
        return Arrays.stream(values())
                .filter(status -> status.getCode() == byteCode)
                .findFirst()
                .orElseThrow(() -> new JMemcachedException("Unsupported byte code for status: " + byteCode));
    }
}

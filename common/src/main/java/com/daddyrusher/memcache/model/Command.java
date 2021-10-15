package com.daddyrusher.memcache.model;

import lombok.Getter;
import com.daddyrusher.memcache.exception.JMemcachedException;

import java.util.Arrays;

@Getter
public enum Command {
    CLEAR(0),
    PUT(1),
    GET(2),
    REMOVE(3);

    private final byte code;

    Command(int code) {
        this.code = (byte) code;
    }

    public static Command valueOf(byte byteCode) {
        return Arrays.stream(values())
                .filter(command -> command.getCode() == byteCode)
                .findFirst()
                .orElseThrow(() -> new JMemcachedException("Unsupported byte code for Command: " + byteCode));
    }
}

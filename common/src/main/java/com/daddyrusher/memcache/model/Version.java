package com.daddyrusher.memcache.model;

import com.daddyrusher.memcache.exception.JMemcachedException;

import java.util.Arrays;

public enum Version {
    VERSION_1_0(1, 0),
    VERSION_0_0(0, 0);

    private final byte high;
    private final byte low;

    Version(int high, int low) {
        this.high = (byte) (high & 0x7);
        this.low = (byte) (low & 0xF);
    }

    public static Version valueOf(byte byteCode) {
        return Arrays.stream(values())
                .filter(version -> version.getByteCode() == byteCode)
                .findFirst()
                .orElseThrow(() -> new JMemcachedException("Unsupported byte code for Version: " + byteCode));
    }

    public byte getByteCode() {
        return (byte) (low + (high << 4));
    }

    @Override
    public String toString() {
        return String.format("%s.%s", high, low);
    }
}

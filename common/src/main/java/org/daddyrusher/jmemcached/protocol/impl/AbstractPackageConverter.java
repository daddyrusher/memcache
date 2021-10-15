package com.daddyrusher.jmemcached.protocol.impl;

import com.daddyrusher.jmemcached.exception.JMemcachedException;
import com.daddyrusher.jmemcached.model.Version;

abstract class AbstractPackageConverter {
    protected void checkProtocolVersion(byte versionByte) {
        var version = Version.valueOf(versionByte);

        if (version != Version.VERSION_1_0) {
            throw new JMemcachedException("Unsupported protocol version: " + version);
        }
    }

    protected byte getVersionByte() {
        return Version.VERSION_1_0.getByteCode();
    }
}

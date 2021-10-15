package com.daddyrusher.memcache.protocol.impl;

import com.daddyrusher.memcache.exception.JMemcachedException;
import com.daddyrusher.memcache.model.Version;

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

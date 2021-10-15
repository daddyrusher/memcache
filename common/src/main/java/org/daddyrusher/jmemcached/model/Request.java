package com.daddyrusher.jmemcached.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;

@Getter
@Setter
public class Request extends AbstractPackage {
    private final Command command;
    private String key;
    private Long ttl;

    public Request(Command command) {
        this.command = command;
    }

    public Request(Command command, String key, Long ttl, byte[] data) {
        super(data);
        this.command = command;
        this.key = key;
        this.ttl = ttl;
    }

    public Request(Command command, String key) {
        this.command = command;
        this.key = key;
    }

    public boolean hasKey() {
        return key != null;
    }

    public boolean hasTtl() {
        return ttl != null && ttl != 0;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder(getCommand().name());
        if (hasKey()) {
            builder.append("[").append(getKey()).append("]");
        }
        if (hasData()) {
            builder.append("=").append(getData().length).append(" bytes");
        }
        if (hasTtl()) {
            var localDate = Instant.ofEpochMilli(getTtl())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            builder.append(" (").append(localDate).append(")");
        }

        return builder.toString();
    }
}

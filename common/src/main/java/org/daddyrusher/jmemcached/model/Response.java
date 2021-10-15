package com.daddyrusher.jmemcached.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response extends AbstractPackage {
    private final Status status;

    public Response(Status status, byte[] data) {
        super(data);
        this.status = status;
    }

    public Response(Status status) {
        this.status = status;
    }
}

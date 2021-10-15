package com.daddyrusher.jmemcached.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
abstract class AbstractPackage {
    private byte[] data;

    public boolean hasData() {
        return data != null && data.length > 0;
    }
}

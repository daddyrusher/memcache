package com.daddyrusher.jmemcached.exception;

public class JMemCachedConfigException extends JMemcachedException {

    public JMemCachedConfigException(String message) {
        super(message);
    }

    public JMemCachedConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public JMemCachedConfigException(Throwable cause) {
        super(cause);
    }
}

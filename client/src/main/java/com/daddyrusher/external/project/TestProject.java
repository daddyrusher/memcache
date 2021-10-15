package com.daddyrusher.external.project;

import com.daddyrusher.memcache.client.impl.JMemcachedClientFactory;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class TestProject {
    public static void main(String[] args) throws Exception {
        try (var client = JMemcachedClientFactory.defaultClient()) {
            client.put("test", "Hello world");
            var test = client.get("test");
            System.out.println(test);
            client.remove("test");

            client.put("test", "test");
            client.put("test", new BusinessObject("test"));
            var test1 = client.get("test");
            System.out.println(test1);
            client.remove("test");
            client.clear();

            var test2 = client.get("test");
            System.out.println(test2);

            client.put("test object", "object data", 2, TimeUnit.SECONDS);

            TimeUnit.SECONDS.sleep(3);

            var testObject = client.get("test object");
            System.out.println(testObject);
        }
    }

    private static record BusinessObject(String name) implements Serializable {
        @Override
        public String toString() {
            return "BusinessObject" + "{name=" + name + "}";
        }
    }
}

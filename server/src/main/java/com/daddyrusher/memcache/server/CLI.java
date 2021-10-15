package com.daddyrusher.memcache.server;

import com.daddyrusher.memcache.server.impl.JMemcachedServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static final Logger LOGGER = LoggerFactory.getLogger(CLI.class);
    private static final List<String> QUIT_CMDS = List.of("q", "quit", "exit");

    public static void main(String[] args) {
        Thread.currentThread().setName("cli-main-thread");

        try {
            var server = JMemcachedServerFactory.buildNewServer(null);
            server.start();
            waitForStopCommand(server);
        } catch (Exception e) {
            LOGGER.error("Can't execute command " + e.getMessage(), e);
        }
    }

    private static void waitForStopCommand(Server server) {
        try (var scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {
                var cmd = scanner.nextLine();
                if (QUIT_CMDS.contains(cmd)) {
                    server.stop();
                    break;
                } else {
                    LOGGER.error("Undefined command " + cmd + "! To shutdown server please type: q ");
                }
            }

        }
    }
}

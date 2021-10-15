package com.daddyrusher.memcache.model;

import com.daddyrusher.memcache.exception.JMemcachedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandTest {
    @ParameterizedTest
    @MethodSource("valueOfSuccessDataProvider")
    void valueOfSuccess(Command expected, byte byteValue) {
        //GIVEN

        //WHEN
        Command actual = Command.valueOf(byteValue);

        //THEN
        assertEquals(expected, actual);
    }

    static Stream<Arguments> valueOfSuccessDataProvider() {
        return Stream.of(
                Arguments.of(Command.CLEAR, (byte) 0),
                Arguments.of(Command.PUT, (byte) 1),
                Arguments.of(Command.GET, (byte) 2),
                Arguments.of(Command.REMOVE, (byte) 3)
        );
    }

    @Test
    void valueOfFailure() {
        //GIVEN

        //WHEN

        //THEN
        assertThrows(JMemcachedException.class, () -> Command.valueOf((byte) -1));
    }

    @ParameterizedTest
    @MethodSource("getCodeDataProvider")
    void getCode(Command command, byte expected) {
        //GIVEN

        //WHEN
        byte actual = command.getCode();

        //THEN
        assertEquals(expected, actual);
    }

    static Stream<Arguments> getCodeDataProvider() {
        return Stream.of(
                Arguments.of(Command.CLEAR, (byte) 0),
                Arguments.of(Command.PUT, (byte) 1),
                Arguments.of(Command.GET, (byte) 2),
                Arguments.of(Command.REMOVE, (byte) 3)
        );
    }

}
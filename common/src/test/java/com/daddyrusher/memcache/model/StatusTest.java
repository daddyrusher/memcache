package com.daddyrusher.memcache.model;

import com.daddyrusher.memcache.exception.JMemcachedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusTest {
    @ParameterizedTest
    @MethodSource("valueOfSuccessDataProvider")
    void valueOfSuccess(Status expected, byte byteValue) {
        //GIVEN

        //WHEN
        Status actual = Status.valueOf(byteValue);

        //THEN
        assertEquals(expected, actual);
    }

    static Stream<Arguments> valueOfSuccessDataProvider() {
        return Stream.of(
                Arguments.of(Status.ADDED, (byte) 0),
                Arguments.of(Status.REPLACED, (byte) 1),
                Arguments.of(Status.GOTTEN, (byte) 2),
                Arguments.of(Status.NOT_FOUND, (byte) 3),
                Arguments.of(Status.REMOVED, (byte) 4),
                Arguments.of(Status.CLEARED, (byte) 5)
        );
    }

    @Test
    void valueOfFailure() {
        //GIVEN

        //WHEN

        //THEN
        assertThrows(JMemcachedException.class, () -> Status.valueOf((byte) -1));
    }

    @ParameterizedTest
    @MethodSource("getCodeDataProvider")
    void getCode(Status status, byte expected) {
        //GIVEN

        //WHEN
        byte actual = status.getCode();

        //THEN
        assertEquals(expected, actual);
    }


    static Stream<Arguments> getCodeDataProvider() {
        return Stream.of(
                Arguments.of(Status.ADDED, (byte) 0),
                Arguments.of(Status.REPLACED, (byte) 1),
                Arguments.of(Status.GOTTEN, (byte) 2),
                Arguments.of(Status.NOT_FOUND, (byte) 3),
                Arguments.of(Status.REMOVED, (byte) 4),
                Arguments.of(Status.CLEARED, (byte) 5)
        );
    }

}
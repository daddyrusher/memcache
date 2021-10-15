package com.daddyrusher.jmemcached.model;

import com.daddyrusher.jmemcached.exception.JMemcachedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {

    @Test
    void valueOfSuccess() {
        //GIVEN
        Version expected = Version.VERSION_1_0;

        //WHEN
        Version actual = Version.valueOf((byte) 16);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void valueOfFailure() {
        //GIVEN

        //WHEN

        //THEN
        assertThrows(JMemcachedException.class, () -> Version.valueOf((byte) 1));
    }

    @ParameterizedTest
    @MethodSource("getCodeDataProvider")
    void getCode(Version version, byte expected) {
        //GIVEN

        //WHEN
        byte actual = version.getByteCode();

        //THEN
        assertEquals(expected, actual);
    }

    static Stream<Arguments> getCodeDataProvider() {
        return Stream.of(
                Arguments.of(Version.VERSION_1_0, (byte) 16),
                Arguments.of(Version.VERSION_0_0, (byte) 0)
        );
    }

}
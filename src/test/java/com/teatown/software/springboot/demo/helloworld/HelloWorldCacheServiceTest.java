package com.teatown.software.springboot.demo.helloworld;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest
class HelloWorldCacheServiceTest {

    @Autowired
    private HelloWorldCacheService cacheService;

    @Test
    void whenHelloWorldWasLoadedIntoCache_thenReturnCachedHelloWorld() {

        final var wantHello = "Hello-O11y!";
        final var wantTimestamp = Instant.now();
        cacheService.setHelloWorld(wantHello, wantTimestamp);

        final String got = cacheService.getHelloWorld(wantHello);

        final var elements = got.split(" ");
        Assertions.assertEquals(wantHello, elements[0]);
        Assertions.assertEquals(wantTimestamp.toString(), elements[2]);

        final String gotFromCache = cacheService.getCachedHellowWorld(wantHello);

        final var elementsFromCache = gotFromCache.split(" ");
        Assertions.assertEquals(wantHello, elementsFromCache[0]);
        Assertions.assertEquals(wantTimestamp.toString(), elementsFromCache[2]);
    }

    @Test
    void whenHelloWorldWasNotLoadedIntoCache_thenReturnNonCachedHelloWorld() {

        final var wantHello = "Hello-O11y!";
        final var wantTimestamp = Instant.now();
        cacheService.setHelloWorld(wantHello, wantTimestamp);

        final String got = cacheService.getCachedHellowWorld(wantHello);

        final var elements = got.split(" ");
        Assertions.assertEquals(wantHello, elements[0]);
        Assertions.assertEquals(wantTimestamp.toString(), elements[2]);
        Assertions.assertEquals("(not-cached)", elements[3]);
    }

}
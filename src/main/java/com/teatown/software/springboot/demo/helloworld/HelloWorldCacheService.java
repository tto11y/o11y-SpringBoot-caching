package com.teatown.software.springboot.demo.helloworld;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@CacheConfig(cacheNames = "invoiceSearchCounts")
public class HelloWorldCacheService {

    private String helloWorld = "Hello World!";

    @CacheEvict(allEntries = true)
    public void setHelloWorld(final String helloWorld, @Nonnull final Instant at) {
        log.debug("Evicts hello world cache");

        this.helloWorld = helloWorld + " at " + at;
        log.debug("Loaded new hello world into cache");
    }

    @Cacheable(key = "#key")
    public String getCachedHellowWorld(final String key) {
        log.info("Retrieve hello world from member variable helloWorld (not from cache)");

        return helloWorld + " (not-cached)";
    }

    @CacheEvict(beforeInvocation = true, key = "#key")
    @Cacheable(key = "#key")
    public String getHelloWorld(final String key) {

        log.info("Starting to sleep");
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Woke up again");

        log.info("Retrieve hello world from member variable helloWorld (not from cache)");
        return helloWorld;
    }
}

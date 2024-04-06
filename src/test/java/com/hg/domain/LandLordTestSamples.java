package com.hg.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LandLordTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static LandLord getLandLordSample1() {
        return new LandLord()
            .id(1L)
            .firstName("firstName1")
            .lastName("lastName1")
            .gender(1)
            .email("email1")
            .phone("phone1")
            .settingsMetadata("settingsMetadata1");
    }

    public static LandLord getLandLordSample2() {
        return new LandLord()
            .id(2L)
            .firstName("firstName2")
            .lastName("lastName2")
            .gender(2)
            .email("email2")
            .phone("phone2")
            .settingsMetadata("settingsMetadata2");
    }

    public static LandLord getLandLordRandomSampleGenerator() {
        return new LandLord()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .gender(intCount.incrementAndGet())
            .email(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .settingsMetadata(UUID.randomUUID().toString());
    }
}

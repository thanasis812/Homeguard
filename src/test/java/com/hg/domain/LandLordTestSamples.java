package com.hg.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LandLordTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LandLord getLandLordSample1() {
        return new LandLord().id(1L).settingsMetadata("settingsMetadata1");
    }

    public static LandLord getLandLordSample2() {
        return new LandLord().id(2L).settingsMetadata("settingsMetadata2");
    }

    public static LandLord getLandLordRandomSampleGenerator() {
        return new LandLord().id(longCount.incrementAndGet()).settingsMetadata(UUID.randomUUID().toString());
    }
}

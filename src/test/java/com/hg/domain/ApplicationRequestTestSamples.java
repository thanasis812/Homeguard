package com.hg.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ApplicationRequestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ApplicationRequest getApplicationRequestSample1() {
        return new ApplicationRequest().id(1L).payload("payload1");
    }

    public static ApplicationRequest getApplicationRequestSample2() {
        return new ApplicationRequest().id(2L).payload("payload2");
    }

    public static ApplicationRequest getApplicationRequestRandomSampleGenerator() {
        return new ApplicationRequest().id(longCount.incrementAndGet()).payload(UUID.randomUUID().toString());
    }
}

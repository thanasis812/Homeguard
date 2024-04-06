package com.hg.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ImageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Image getImageSample1() {
        return new Image().id(1L).path("path1");
    }

    public static Image getImageSample2() {
        return new Image().id(2L).path("path2");
    }

    public static Image getImageRandomSampleGenerator() {
        return new Image().id(longCount.incrementAndGet()).path(UUID.randomUUID().toString());
    }
}

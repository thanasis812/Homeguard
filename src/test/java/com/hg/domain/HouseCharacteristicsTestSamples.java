package com.hg.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class HouseCharacteristicsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static HouseCharacteristics getHouseCharacteristicsSample1() {
        return new HouseCharacteristics().id(1L);
    }

    public static HouseCharacteristics getHouseCharacteristicsSample2() {
        return new HouseCharacteristics().id(2L);
    }

    public static HouseCharacteristics getHouseCharacteristicsRandomSampleGenerator() {
        return new HouseCharacteristics().id(longCount.incrementAndGet());
    }
}

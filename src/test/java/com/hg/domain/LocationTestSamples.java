package com.hg.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LocationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Location getLocationSample1() {
        return new Location()
            .id(1L)
            .streetAddress("streetAddress1")
            .apartmentUnit("apartmentUnit1")
            .city("city1")
            .stateProvinceRegion("stateProvinceRegion1")
            .postalCode("postalCode1")
            .country("country1")
            .localGeographicDivision("localGeographicDivision1")
            .municipalCommunity("municipalCommunity1")
            .number("number1")
            .zipCode(1)
            .floor(1)
            .directions("directions1");
    }

    public static Location getLocationSample2() {
        return new Location()
            .id(2L)
            .streetAddress("streetAddress2")
            .apartmentUnit("apartmentUnit2")
            .city("city2")
            .stateProvinceRegion("stateProvinceRegion2")
            .postalCode("postalCode2")
            .country("country2")
            .localGeographicDivision("localGeographicDivision2")
            .municipalCommunity("municipalCommunity2")
            .number("number2")
            .zipCode(2)
            .floor(2)
            .directions("directions2");
    }

    public static Location getLocationRandomSampleGenerator() {
        return new Location()
            .id(longCount.incrementAndGet())
            .streetAddress(UUID.randomUUID().toString())
            .apartmentUnit(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .stateProvinceRegion(UUID.randomUUID().toString())
            .postalCode(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .localGeographicDivision(UUID.randomUUID().toString())
            .municipalCommunity(UUID.randomUUID().toString())
            .number(UUID.randomUUID().toString())
            .zipCode(intCount.incrementAndGet())
            .floor(intCount.incrementAndGet())
            .directions(UUID.randomUUID().toString());
    }
}

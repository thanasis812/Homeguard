package com.hg.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PropertyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Property getPropertySample1() {
        return new Property()
            .id(1L)
            .name("name1")
            .description("description1")
            .price(1)
            .numberOfBathrooms(1)
            .numberOfBedrooms(1)
            .numberOfKitchens(1)
            .numberOfAirConditioner(1)
            .contractYears(1)
            .thumbnail(1L)
            .houseType("houseType1")
            .floor(1)
            .numberOfFlats(1)
            .energyClass("energyClass1")
            .yearOfManufacture(1)
            .yearOfRenovation(1)
            .propertyCode("propertyCode1")
            .furnituredDescription("furnituredDescription1");
    }

    public static Property getPropertySample2() {
        return new Property()
            .id(2L)
            .name("name2")
            .description("description2")
            .price(2)
            .numberOfBathrooms(2)
            .numberOfBedrooms(2)
            .numberOfKitchens(2)
            .numberOfAirConditioner(2)
            .contractYears(2)
            .thumbnail(2L)
            .houseType("houseType2")
            .floor(2)
            .numberOfFlats(2)
            .energyClass("energyClass2")
            .yearOfManufacture(2)
            .yearOfRenovation(2)
            .propertyCode("propertyCode2")
            .furnituredDescription("furnituredDescription2");
    }

    public static Property getPropertyRandomSampleGenerator() {
        return new Property()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .price(intCount.incrementAndGet())
            .numberOfBathrooms(intCount.incrementAndGet())
            .numberOfBedrooms(intCount.incrementAndGet())
            .numberOfKitchens(intCount.incrementAndGet())
            .numberOfAirConditioner(intCount.incrementAndGet())
            .contractYears(intCount.incrementAndGet())
            .thumbnail(longCount.incrementAndGet())
            .houseType(UUID.randomUUID().toString())
            .floor(intCount.incrementAndGet())
            .numberOfFlats(intCount.incrementAndGet())
            .energyClass(UUID.randomUUID().toString())
            .yearOfManufacture(intCount.incrementAndGet())
            .yearOfRenovation(intCount.incrementAndGet())
            .propertyCode(UUID.randomUUID().toString())
            .furnituredDescription(UUID.randomUUID().toString());
    }
}

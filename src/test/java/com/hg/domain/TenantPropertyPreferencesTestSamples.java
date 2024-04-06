package com.hg.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TenantPropertyPreferencesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TenantPropertyPreferences getTenantPropertyPreferencesSample1() {
        return new TenantPropertyPreferences().id(1L);
    }

    public static TenantPropertyPreferences getTenantPropertyPreferencesSample2() {
        return new TenantPropertyPreferences().id(2L);
    }

    public static TenantPropertyPreferences getTenantPropertyPreferencesRandomSampleGenerator() {
        return new TenantPropertyPreferences().id(longCount.incrementAndGet());
    }
}

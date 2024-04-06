package com.hg.domain;

import static com.hg.domain.HouseCharacteristicsTestSamples.*;
import static com.hg.domain.PropertyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HouseCharacteristicsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseCharacteristics.class);
        HouseCharacteristics houseCharacteristics1 = getHouseCharacteristicsSample1();
        HouseCharacteristics houseCharacteristics2 = new HouseCharacteristics();
        assertThat(houseCharacteristics1).isNotEqualTo(houseCharacteristics2);

        houseCharacteristics2.setId(houseCharacteristics1.getId());
        assertThat(houseCharacteristics1).isEqualTo(houseCharacteristics2);

        houseCharacteristics2 = getHouseCharacteristicsSample2();
        assertThat(houseCharacteristics1).isNotEqualTo(houseCharacteristics2);
    }

    @Test
    void propertyTest() throws Exception {
        HouseCharacteristics houseCharacteristics = getHouseCharacteristicsRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        houseCharacteristics.setProperty(propertyBack);
        assertThat(houseCharacteristics.getProperty()).isEqualTo(propertyBack);

        houseCharacteristics.property(null);
        assertThat(houseCharacteristics.getProperty()).isNull();
    }
}

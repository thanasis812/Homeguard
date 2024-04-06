package com.hg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HouseCharacteristicsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseCharacteristicsDTO.class);
        HouseCharacteristicsDTO houseCharacteristicsDTO1 = new HouseCharacteristicsDTO();
        houseCharacteristicsDTO1.setId(1L);
        HouseCharacteristicsDTO houseCharacteristicsDTO2 = new HouseCharacteristicsDTO();
        assertThat(houseCharacteristicsDTO1).isNotEqualTo(houseCharacteristicsDTO2);
        houseCharacteristicsDTO2.setId(houseCharacteristicsDTO1.getId());
        assertThat(houseCharacteristicsDTO1).isEqualTo(houseCharacteristicsDTO2);
        houseCharacteristicsDTO2.setId(2L);
        assertThat(houseCharacteristicsDTO1).isNotEqualTo(houseCharacteristicsDTO2);
        houseCharacteristicsDTO1.setId(null);
        assertThat(houseCharacteristicsDTO1).isNotEqualTo(houseCharacteristicsDTO2);
    }
}

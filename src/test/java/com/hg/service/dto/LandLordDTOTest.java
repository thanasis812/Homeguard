package com.hg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LandLordDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandLordDTO.class);
        LandLordDTO landLordDTO1 = new LandLordDTO();
        landLordDTO1.setId(1L);
        LandLordDTO landLordDTO2 = new LandLordDTO();
        assertThat(landLordDTO1).isNotEqualTo(landLordDTO2);
        landLordDTO2.setId(landLordDTO1.getId());
        assertThat(landLordDTO1).isEqualTo(landLordDTO2);
        landLordDTO2.setId(2L);
        assertThat(landLordDTO1).isNotEqualTo(landLordDTO2);
        landLordDTO1.setId(null);
        assertThat(landLordDTO1).isNotEqualTo(landLordDTO2);
    }
}

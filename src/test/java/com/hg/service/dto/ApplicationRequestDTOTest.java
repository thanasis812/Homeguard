package com.hg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationRequestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationRequestDTO.class);
        ApplicationRequestDTO applicationRequestDTO1 = new ApplicationRequestDTO();
        applicationRequestDTO1.setId(1L);
        ApplicationRequestDTO applicationRequestDTO2 = new ApplicationRequestDTO();
        assertThat(applicationRequestDTO1).isNotEqualTo(applicationRequestDTO2);
        applicationRequestDTO2.setId(applicationRequestDTO1.getId());
        assertThat(applicationRequestDTO1).isEqualTo(applicationRequestDTO2);
        applicationRequestDTO2.setId(2L);
        assertThat(applicationRequestDTO1).isNotEqualTo(applicationRequestDTO2);
        applicationRequestDTO1.setId(null);
        assertThat(applicationRequestDTO1).isNotEqualTo(applicationRequestDTO2);
    }
}

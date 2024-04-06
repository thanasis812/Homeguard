package com.hg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RentalAgreementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentalAgreementDTO.class);
        RentalAgreementDTO rentalAgreementDTO1 = new RentalAgreementDTO();
        rentalAgreementDTO1.setId(1L);
        RentalAgreementDTO rentalAgreementDTO2 = new RentalAgreementDTO();
        assertThat(rentalAgreementDTO1).isNotEqualTo(rentalAgreementDTO2);
        rentalAgreementDTO2.setId(rentalAgreementDTO1.getId());
        assertThat(rentalAgreementDTO1).isEqualTo(rentalAgreementDTO2);
        rentalAgreementDTO2.setId(2L);
        assertThat(rentalAgreementDTO1).isNotEqualTo(rentalAgreementDTO2);
        rentalAgreementDTO1.setId(null);
        assertThat(rentalAgreementDTO1).isNotEqualTo(rentalAgreementDTO2);
    }
}

package com.hg.web.rest;

import com.hg.service.RentalAgreementService;
import com.hg.service.dto.RentalAgreementDTO;
import com.hg.service.dto.mydto.PropertyDossierDTO;
import com.hg.service.dto.mydto.RentalAgreementSaveDTO;
import com.hg.service.dto.mydto.RentalApplicationStatusDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URISyntaxException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hg.domain.RentalAgreement}.
 */
@RestController
@Deprecated
@RequestMapping("/api/rental-agreements")
public class RentalAgreementResource {

    private final Logger log = LoggerFactory.getLogger(RentalAgreementResource.class);

    private final RentalAgreementService rentalAgreementService;

    private final HGTokenService tokenService;

    public RentalAgreementResource(RentalAgreementService rentalAgreementService, HGTokenService tokenService) {
        this.rentalAgreementService = rentalAgreementService;
        this.tokenService = tokenService;
    }
}

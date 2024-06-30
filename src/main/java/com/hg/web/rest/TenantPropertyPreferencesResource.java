package com.hg.web.rest;

import com.hg.service.TenantPropertyPreferencesService;
import com.hg.service.dto.TenantPropertyPreferencesDTO;
import com.hg.service.dto.mydto.FavoritePropertyDTO;
import com.hg.service.dto.mydto.HousePropertyDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.hg.domain.TenantPropertyPreferences}.
 */
@Hidden
@RestController
@RequestMapping("/api/property-preferences")
public class TenantPropertyPreferencesResource {

    private final Logger log = LoggerFactory.getLogger(TenantPropertyPreferencesResource.class);

    private final TenantPropertyPreferencesService tenantPropertyPreferencesService;

    private final HGTokenService tokenService;

    public TenantPropertyPreferencesResource(
        TenantPropertyPreferencesService tenantPropertyPreferencesService,
        HGTokenService tokenService
    ) {
        this.tenantPropertyPreferencesService = tenantPropertyPreferencesService;
        this.tokenService = tokenService;
    }
}

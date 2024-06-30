package com.hg.web.rest;

import com.hg.service.ReviewService;
import com.hg.service.TenantPropertyPreferencesService;
import com.hg.service.dto.TenantPropertyPreferencesDTO;
import com.hg.service.dto.mydto.FavoritePropertyDTO;
import com.hg.service.dto.mydto.HousePropertyDTO;
import com.hg.service.dto.mydto.UserReviewDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hg.domain.Review}.
 */
@Hidden
@RestController
@RequestMapping("/api/users")
public class HMUserResource {

    private final Logger log = LoggerFactory.getLogger(HMUserResource.class);

    private final ReviewService reviewService;
    private final TenantPropertyPreferencesService tenantPropertyPreferencesService;

    private final HGTokenService tokenService;

    public HMUserResource(
        ReviewService reviewService,
        TenantPropertyPreferencesService tenantPropertyPreferencesService,
        HGTokenService tokenService
    ) {
        this.reviewService = reviewService;
        this.tenantPropertyPreferencesService = tenantPropertyPreferencesService;
        this.tokenService = tokenService;
    }

    /**
     * {@code GET  /reviews/tenant/id} : get all the reviews for selected tenant.
     * /users/reviews
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reviews in body.
     */
    @GetMapping("reviews")
    public ResponseEntity<List<UserReviewDTO>> findReviewsByTenantId(HttpServletRequest request) {
        log.debug("REST request to get tenant reviews");
        Long tenantId = tokenService.getTenantId(request);
        if (tenantId == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no tenant ID found
        }
        List<UserReviewDTO> reviews = reviewService.findUserReviews(tenantId);
        return ResponseUtil.wrapOrNotFound(Optional.of(reviews));
    }

    /**
     * GET  /favourite-house : get the favourite and alarm houses for a given tenant.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favourite and alarm houses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/favourite-house")
    public ResponseEntity<FavoritePropertyDTO> getFavouriteAndAlarmHouses(HttpServletRequest request) {
        Long tenantId = tokenService.getTenantId(request);
        if (tenantId == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no tenant ID found
        }
        log.debug("REST request to getFavouriteAndAlarmHouses");
        FavoritePropertyDTO content = tenantPropertyPreferencesService.getFavouriteAndAlarmHouses(tenantId);
        return ResponseEntity.ok().body(content);
    }

    /**
     * POST  /favourite-house : Favorite a house for a given tenant.
     *
     * @param housePropertyDTO the house to favorite for the tenant.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favorite house, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/favourite-house")
    public ResponseEntity<TenantPropertyPreferencesDTO> favoriteHouse(@RequestBody HousePropertyDTO housePropertyDTO) {
        log.debug("REST request to getFavouriteAndAlarmHouses");
        TenantPropertyPreferencesDTO content = tenantPropertyPreferencesService.favoritePropertyForTenant(
            housePropertyDTO.getHouseId(),
            housePropertyDTO.getTenantId(),
            housePropertyDTO.isFavorite()
        );
        return ResponseEntity.ok().body(content);
    }

    /**
     * POST  /alarm-house : Alarm a house for a given tenant.
     *
     * @param housePropertyDTO the house to alarm for the tenant.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarm house, or with status {@code 404 (Not Found)}.
     *
     * @see #favoriteHouse(HousePropertyDTO)
     */
    @PostMapping("/alarm-house")
    public ResponseEntity<TenantPropertyPreferencesDTO> alarmHouse(@RequestBody HousePropertyDTO housePropertyDTO) {
        log.debug("REST request to getFavouriteAndAlarmHouses");
        TenantPropertyPreferencesDTO content = tenantPropertyPreferencesService.alarmPropertyForTenant(
            housePropertyDTO.getHouseId(),
            housePropertyDTO.getTenantId(),
            housePropertyDTO.isReminder()
        );
        return ResponseEntity.ok().body(content);
    }

    //TODO: 6/30/24 implement this
    @GetMapping("profile-preview")
    public ResponseEntity<List<UserReviewDTO>> getProfilePreview() {
        throw new RuntimeException("Not implemented");
    }

    //TODO: 6/30/24 implement this
    @GetMapping("landlord-house-info")
    public ResponseEntity<List<UserReviewDTO>> getLandlordHouseInfo() {
        throw new RuntimeException("Not implemented");
    }

    //TODO: 6/30/24 implement this

    @PostMapping("landlord-registration")
    public ResponseEntity<List<UserReviewDTO>> landLordRegistration() {
        throw new RuntimeException("Not implemented");
    }

    //TODO: 6/30/24 implement this

    @PostMapping("landlord-communication")
    public ResponseEntity<List<UserReviewDTO>> landLordCommunication() {
        throw new RuntimeException("Not implemented");
    }

    //TODO: 6/30/24 implement this
    @GetMapping("rent-requests")
    public ResponseEntity<List<UserReviewDTO>> rentRequest() {
        throw new RuntimeException("Not implemented");
    }

    //TODO: 6/30/24 implement this
    @GetMapping("taxisnet-document")
    public ResponseEntity<List<UserReviewDTO>> taxisnetDocument() {
        throw new RuntimeException("Not implemented");
    }
}

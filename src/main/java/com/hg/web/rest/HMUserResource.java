package com.hg.web.rest;

import com.hg.service.*;
import com.hg.service.dto.TenantPropertyPreferencesDTO;
import com.hg.service.dto.mydto.*;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/api/users")
public class HMUserResource {

    private final Logger log = LoggerFactory.getLogger(HMUserResource.class);

    private final ReviewService reviewService;
    private final TenantPropertyPreferencesService tenantPropertyPreferencesService;
    private final UserService userService;
    private final PropertyService propertyService;
    private final UserPrincipalService principalService;

    /**
     * {@code GET  /reviews/tenant/id} : get all the reviews for selected tenant.
     * /users/reviews
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reviews in body.
     */
    @GetMapping("reviews")
    public ResponseEntity<List<UserReviewDTO>> findReviewsByTenantId() {
        log.debug("REST request to get tenant reviews");
        Long tenantId = principalService.getTenantId();
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
    public ResponseEntity<FavoritePropertyDTO> getFavouriteAndAlarmHouses() {
        Long tenantId = principalService.getTenantId();
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
        Long tenantId = principalService.getTenantId();
        if (tenantId == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no tenant ID found
        }
        TenantPropertyPreferencesDTO content = tenantPropertyPreferencesService.favoritePropertyForTenant(
            housePropertyDTO.getHouseId(),
            tenantId,
            housePropertyDTO.isFavorite()
        );
        return ResponseEntity.ok().body(content);
    }

    /**
     * POST  /alarm-house : Alarm a house for a given tenant.
     *
     * @param housePropertyDTO the house to alarm for the tenant.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarm house, or with status {@code 404 (Not Found)}.
     * @see #favoriteHouse(HousePropertyDTO)
     */
    @PostMapping("/alarm-house")
    public ResponseEntity<TenantPropertyPreferencesDTO> alarmHouse(@RequestBody HousePropertyDTO housePropertyDTO) {
        log.debug("REST request to getFavouriteAndAlarmHouses");
        Long tenantId = principalService.getTenantId();
        if (tenantId == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no tenant ID found
        }
        TenantPropertyPreferencesDTO content = tenantPropertyPreferencesService.alarmPropertyForTenant(
            housePropertyDTO.getHouseId(),
            tenantId,
            housePropertyDTO.isReminder()
        );
        return ResponseEntity.ok().body(content);
    }

    //TODO: 6/30/24 implement this
    @GetMapping("profile-preview/{id}")
    public ResponseEntity<UserDetailDTO> getProfilePreview(@PathVariable("id") Long id) {
        log.debug("REST request to getProfilePreview for id {}", id);
        var profilePreview = userService.findById(id);
        return ResponseUtil.wrapOrNotFound(profilePreview);
    }

    @GetMapping("landlord-house-info/{houseId}")
    public ResponseEntity<LandLordHouseInfo> getLandlordHouseInfo(@PathVariable Long houseId) {
        log.debug("REST request to getLandlordHouseInfo for house id  {}", houseId);
        return ResponseUtil.wrapOrNotFound(propertyService.getLandlordHouseInfo(houseId));
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
    public ResponseEntity<List<MessageDTO>> rentRequest() {
        List<MessageDTO> messages = new ArrayList<>();

        messages.add(
            new MessageDTO(
                "info",
                "Information",
                "This is an informational message.",
                1,
                "infoKey",
                5000,
                false,
                true,
                null,
                "info-icon",
                "info-content",
                "info-style",
                "close-info-icon"
            )
        );

        messages.add(
            new MessageDTO(
                "warning",
                "Warning",
                "This is a warning message.",
                2,
                "warnKey",
                7000,
                true,
                true,
                null,
                "warning-icon",
                "warning-content",
                "warning-style",
                "close-warning-icon"
            )
        );

        messages.add(
            new MessageDTO(
                "error",
                "Error",
                "This is an error message.",
                3,
                "errorKey",
                10000,
                false,
                false,
                null,
                "error-icon",
                "error-content",
                "error-style",
                "close-error-icon"
            )
        );
        return ResponseEntity.ok(messages);
    }

    //TODO: 6/30/24 implement this
    @GetMapping("taxisnet-document")
    public ResponseEntity<List<UserReviewDTO>> taxisnetDocument() {
        throw new RuntimeException("Not implemented");
    }
}

package com.hg.web.rest;

import com.hg.repository.ReviewRepository;
import com.hg.service.ReviewService;
import com.hg.service.dto.mydto.UserReviewDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hg.domain.Review}.
 */
@Hidden
@RestController
@RequestMapping("/api/reviews")
public class ReviewResource {

    private final Logger log = LoggerFactory.getLogger(ReviewResource.class);

    private final ReviewService reviewService;

    private final HGTokenService tokenService;

    public ReviewResource(ReviewService reviewService, HGTokenService tokenService) {
        this.reviewService = reviewService;
        this.tokenService = tokenService;
    }

    /**
     * {@code GET  /reviews/tenant/id} : get all the reviews for selected tenant.
     * /users/reviews
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reviews in body.
     */
    @GetMapping("tenant")
    public ResponseEntity<List<UserReviewDTO>> findReviewsByTenantId(HttpServletRequest request) {
        log.debug("REST request to get tenant reviews");
        Long tenantId = tokenService.getTenantId(request);
        if (tenantId == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no tenant ID found
        }
        List<UserReviewDTO> reviews = reviewService.findUserReviews(tenantId);
        return ResponseUtil.wrapOrNotFound(Optional.of(reviews));
    }
}

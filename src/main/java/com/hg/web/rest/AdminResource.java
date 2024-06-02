package com.hg.web.rest;

import com.hg.repository.ReviewRepository;
import com.hg.service.ReviewService;
import com.hg.service.dto.ReviewDTO;
import com.hg.service.dto.mydto.UserReviewDTO;
import com.hg.web.rest.errors.BaseException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hg.domain.Review}.
 */
@Hidden
@RestController
@RequestMapping("/api/admin")
public class AdminResource {

    private final Logger log = LoggerFactory.getLogger(AdminResource.class);

    private static final String ENTITY_NAME = "review";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReviewService reviewService;

    private final ReviewRepository reviewRepository;

    public AdminResource(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }

    /**
     * Processes a request to approve a credit card.
     *
     * @param creditInfo the credit card number to be approved.
     * @return a {@link ResponseEntity} containing a success message and the approved credit card number.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credit-card-approve")
    public ResponseEntity<String> creditCardApprove(@Valid @RequestBody String creditInfo) throws URISyntaxException {
        log.debug("REST request to creditCardApprove for credit : {}", creditInfo);
        return ResponseEntity.ok().body("Credit card with number " + creditInfo + " approved");
    }

    /**
     * Processes a request to approve a house.
     *
     * @param houseId the ID of the house to be approved.
     * @return a {@link ResponseEntity} containing a success message and the approved house ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/new-house-approve")
    public ResponseEntity<String> newHouseApprove(@Valid @RequestBody Long houseId) throws URISyntaxException {
        log.debug("REST request to newHouseApprove to with id : {}", houseId);
        return ResponseEntity.ok().body("House with ID " + houseId + " approved");
    }

    /**
     * Processes a request to approve a house.
     *
     * @param privateAgreementId the ID of the house to be approved.
     * @return a {@link ResponseEntity} containing a success message and the approved house ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/private-agreement-approve")
    public ResponseEntity<String> privateAgreementApprove(@Valid @RequestBody Long privateAgreementId) throws URISyntaxException {
        log.debug("REST request to privateAgreementApprove to with id : {}", privateAgreementId);
        return ResponseEntity.ok().body("Private agreement with ID " + privateAgreementId + " approved");
    }

    /**
     * Processes a request to approve a house.
     *
     * @param id the ID of the house to be approved.
     * @return a {@link ResponseEntity} containing a success message and the approved house ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salary-certificate-approve")
    public ResponseEntity<String> salaryCertificateApprove(@Valid @RequestBody Long id) throws URISyntaxException {
        log.debug("REST request to privateAgreementApprove to with id : {}", id);
        return ResponseEntity.ok().body("Salary certification with ID " + id + " approved");
    }

    /**
     * Processes a request to approve a house.
     *
     * @param id the ID of the house to be approved.
     * @return a {@link ResponseEntity} containing a success message and the approved house ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taxid-certificate-approve")
    public ResponseEntity<String> taxidCertificateApprove(@Valid @RequestBody Long id) throws URISyntaxException {
        log.debug("REST request to privateAgreementApprove to with id : {}", id);
        return ResponseEntity.ok().body("Tax ID certificate with ID " + id + " approved");
    }

    /**
     * Processes a request to approve a house.
     *
     * @param id the ID of the house to be approved.
     * @return a {@link ResponseEntity} containing a success message and the approved house ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taxisnet-document-approve")
    public ResponseEntity<String> taxisnetDocumentApprove(@Valid @RequestBody Long id) throws URISyntaxException {
        log.debug("REST request to privateAgreementApprove to with id : {}", id);
        return ResponseEntity.ok().body("Taxis net with with ID " + id + " approved");
    }
}

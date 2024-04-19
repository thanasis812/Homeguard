package com.hg.service.impl;

import com.hg.domain.Review;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import com.hg.repository.ReviewRepository;
import com.hg.repository.TenantRepository;
import com.hg.service.ReviewService;
import com.hg.service.dto.ReviewDTO;
import com.hg.service.dto.mydto.UserReviewDTO;
import com.hg.service.mapper.ReviewMapper;
import com.hg.web.rest.errors.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.hg.domain.Review}.
 */
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final TenantRepository tenantRepository;

    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, TenantRepository tenantRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.tenantRepository = tenantRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public ReviewDTO save(ReviewDTO reviewDTO) {
        log.debug("Request to save Review : {}", reviewDTO);
        Review review = reviewMapper.toEntity(reviewDTO);
        review = reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    @Override
    public ReviewDTO update(ReviewDTO reviewDTO) {
        log.debug("Request to update Review : {}", reviewDTO);
        Review review = reviewMapper.toEntity(reviewDTO);
        review = reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    @Override
    public Optional<ReviewDTO> partialUpdate(ReviewDTO reviewDTO) {
        log.debug("Request to partially update Review : {}", reviewDTO);

        return reviewRepository
            .findById(reviewDTO.getId())
            .map(existingReview -> {
                reviewMapper.partialUpdate(existingReview, reviewDTO);

                return existingReview;
            })
            .map(reviewRepository::save)
            .map(reviewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reviews");
        return reviewRepository.findAll(pageable).map(reviewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewDTO> findOne(Long id) {
        log.debug("Request to get Review : {}", id);
        return reviewRepository.findById(id).map(reviewMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Review : {}", id);
        reviewRepository.deleteById(id);
    }

    @Override
    public List<UserReviewDTO> findUserReviews(Long tenantId) {
        log.debug("Request to findUserReviews for tenant : {}", tenantId);
        return reviewMapper.toUserDTOList(
            reviewRepository.findByTenantAndDeletedOrderByCreatedDate(
                tenantRepository
                    .findById(tenantId)
                    .orElseThrow(() -> new NotFoundException(String.format("Can't find Tenant with Tenant id: %d", tenantId))),
                false
            )
        );
    }
}

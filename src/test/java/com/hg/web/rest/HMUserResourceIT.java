package com.hg.web.rest;

import static com.hg.domain.ReviewAsserts.*;
import static com.hg.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.IntegrationTest;
import com.hg.domain.Review;
import com.hg.repository.ReviewRepository;
import com.hg.service.dto.ReviewDTO;
import com.hg.service.mapper.ReviewMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HMUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HMUserResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_STARS = 1;
    private static final Integer UPDATED_STARS = 2;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String ENTITY_API_URL = "/api/reviews";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReviewMockMvc;

    private Review review;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Review createEntity(EntityManager em) {
        Review review = new Review()
            .description(DEFAULT_DESCRIPTION)
            .stars(DEFAULT_STARS)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .deleted(DEFAULT_DELETED);
        return review;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Review createUpdatedEntity(EntityManager em) {
        Review review = new Review()
            .description(UPDATED_DESCRIPTION)
            .stars(UPDATED_STARS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deleted(UPDATED_DELETED);
        return review;
    }

    @BeforeEach
    public void initTest() {
        review = createEntity(em);
    }

    @Test
    @Transactional
    void createReview() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Review
        ReviewDTO reviewDTO = reviewMapper.toDto(review);
        var returnedReviewDTO = om.readValue(
            restReviewMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reviewDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ReviewDTO.class
        );

        // Validate the Review in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedReview = reviewMapper.toEntity(returnedReviewDTO);
        assertReviewUpdatableFieldsEquals(returnedReview, getPersistedReview(returnedReview));
    }

    @Test
    @Transactional
    void createReviewWithExistingId() throws Exception {
        // Create the Review with an existing ID
        review.setId(1L);
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStarsIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        review.setStars(null);

        // Create the Review, which fails.
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        restReviewMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reviewDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        review.setCreatedDate(null);

        // Create the Review, which fails.
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        restReviewMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reviewDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReviews() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList
        restReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(review.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].stars").value(hasItem(DEFAULT_STARS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get the review
        restReviewMockMvc
            .perform(get(ENTITY_API_URL_ID, review.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(review.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.stars").value(DEFAULT_STARS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingReview() throws Exception {
        // Get the review
        restReviewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the review
        Review updatedReview = reviewRepository.findById(review.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReview are not directly saved in db
        em.detach(updatedReview);
        updatedReview
            .description(UPDATED_DESCRIPTION)
            .stars(UPDATED_STARS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deleted(UPDATED_DELETED);
        ReviewDTO reviewDTO = reviewMapper.toDto(updatedReview);

        restReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reviewDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reviewDTO))
            )
            .andExpect(status().isOk());

        // Validate the Review in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedReviewToMatchAllProperties(updatedReview);
    }

    @Test
    @Transactional
    void putNonExistingReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        review.setId(longCount.incrementAndGet());

        // Create the Review
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reviewDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        review.setId(longCount.incrementAndGet());

        // Create the Review
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        review.setId(longCount.incrementAndGet());

        // Create the Review
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reviewDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Review in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReviewWithPatch() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the review using partial update
        Review partialUpdatedReview = new Review();
        partialUpdatedReview.setId(review.getId());

        partialUpdatedReview.description(UPDATED_DESCRIPTION).updatedDate(UPDATED_UPDATED_DATE).deleted(UPDATED_DELETED);

        restReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReview))
            )
            .andExpect(status().isOk());

        // Validate the Review in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReviewUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedReview, review), getPersistedReview(review));
    }

    @Test
    @Transactional
    void fullUpdateReviewWithPatch() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the review using partial update
        Review partialUpdatedReview = new Review();
        partialUpdatedReview.setId(review.getId());

        partialUpdatedReview
            .description(UPDATED_DESCRIPTION)
            .stars(UPDATED_STARS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deleted(UPDATED_DELETED);

        restReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReview))
            )
            .andExpect(status().isOk());

        // Validate the Review in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReviewUpdatableFieldsEquals(partialUpdatedReview, getPersistedReview(partialUpdatedReview));
    }

    @Test
    @Transactional
    void patchNonExistingReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        review.setId(longCount.incrementAndGet());

        // Create the Review
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reviewDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        review.setId(longCount.incrementAndGet());

        // Create the Review
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        review.setId(longCount.incrementAndGet());

        // Create the Review
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(reviewDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Review in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the review
        restReviewMockMvc
            .perform(delete(ENTITY_API_URL_ID, review.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return reviewRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Review getPersistedReview(Review review) {
        return reviewRepository.findById(review.getId()).orElseThrow();
    }

    protected void assertPersistedReviewToMatchAllProperties(Review expectedReview) {
        assertReviewAllPropertiesEquals(expectedReview, getPersistedReview(expectedReview));
    }

    protected void assertPersistedReviewToMatchUpdatableProperties(Review expectedReview) {
        assertReviewAllUpdatablePropertiesEquals(expectedReview, getPersistedReview(expectedReview));
    }
}

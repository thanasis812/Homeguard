package com.hg.web.rest;

import static com.hg.domain.TenantPropertyPreferencesAsserts.*;
import static com.hg.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.IntegrationTest;
import com.hg.domain.TenantPropertyPreferences;
import com.hg.repository.TenantPropertyPreferencesRepository;
import com.hg.service.dto.TenantPropertyPreferencesDTO;
import com.hg.service.mapper.TenantPropertyPreferencesMapper;
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
 * Integration tests for the {@link TenantPropertyPreferencesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TenantPropertyPreferencesResourceIT {

    private static final Boolean DEFAULT_FAVORITE = false;
    private static final Boolean UPDATED_FAVORITE = true;

    private static final LocalDate DEFAULT_FAVORITE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FAVORITE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_REMINDER = false;
    private static final Boolean UPDATED_REMINDER = true;

    private static final LocalDate DEFAULT_REMINDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REMINDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String ENTITY_API_URL = "/api/tenant-property-preferences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TenantPropertyPreferencesRepository tenantPropertyPreferencesRepository;

    @Autowired
    private TenantPropertyPreferencesMapper tenantPropertyPreferencesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTenantPropertyPreferencesMockMvc;

    private TenantPropertyPreferences tenantPropertyPreferences;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TenantPropertyPreferences createEntity(EntityManager em) {
        TenantPropertyPreferences tenantPropertyPreferences = new TenantPropertyPreferences()
            .favorite(DEFAULT_FAVORITE)
            .favoriteDate(DEFAULT_FAVORITE_DATE)
            .reminder(DEFAULT_REMINDER)
            .reminderDate(DEFAULT_REMINDER_DATE)
            .deleted(DEFAULT_DELETED);
        return tenantPropertyPreferences;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TenantPropertyPreferences createUpdatedEntity(EntityManager em) {
        TenantPropertyPreferences tenantPropertyPreferences = new TenantPropertyPreferences()
            .favorite(UPDATED_FAVORITE)
            .favoriteDate(UPDATED_FAVORITE_DATE)
            .reminder(UPDATED_REMINDER)
            .reminderDate(UPDATED_REMINDER_DATE)
            .deleted(UPDATED_DELETED);
        return tenantPropertyPreferences;
    }

    @BeforeEach
    public void initTest() {
        tenantPropertyPreferences = createEntity(em);
    }

    @Test
    @Transactional
    void createTenantPropertyPreferences() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TenantPropertyPreferences
        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO = tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);
        var returnedTenantPropertyPreferencesDTO = om.readValue(
            restTenantPropertyPreferencesMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantPropertyPreferencesDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TenantPropertyPreferencesDTO.class
        );

        // Validate the TenantPropertyPreferences in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTenantPropertyPreferences = tenantPropertyPreferencesMapper.toEntity(returnedTenantPropertyPreferencesDTO);
        assertTenantPropertyPreferencesUpdatableFieldsEquals(
            returnedTenantPropertyPreferences,
            getPersistedTenantPropertyPreferences(returnedTenantPropertyPreferences)
        );
    }

    @Test
    @Transactional
    void createTenantPropertyPreferencesWithExistingId() throws Exception {
        // Create the TenantPropertyPreferences with an existing ID
        tenantPropertyPreferences.setId(1L);
        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO = tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenantPropertyPreferencesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantPropertyPreferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TenantPropertyPreferences in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTenantPropertyPreferences() throws Exception {
        // Initialize the database
        tenantPropertyPreferencesRepository.saveAndFlush(tenantPropertyPreferences);

        // Get all the tenantPropertyPreferencesList
        restTenantPropertyPreferencesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenantPropertyPreferences.getId().intValue())))
            .andExpect(jsonPath("$.[*].favorite").value(hasItem(DEFAULT_FAVORITE.booleanValue())))
            .andExpect(jsonPath("$.[*].favoriteDate").value(hasItem(DEFAULT_FAVORITE_DATE.toString())))
            .andExpect(jsonPath("$.[*].reminder").value(hasItem(DEFAULT_REMINDER.booleanValue())))
            .andExpect(jsonPath("$.[*].reminderDate").value(hasItem(DEFAULT_REMINDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getTenantPropertyPreferences() throws Exception {
        // Initialize the database
        tenantPropertyPreferencesRepository.saveAndFlush(tenantPropertyPreferences);

        // Get the tenantPropertyPreferences
        restTenantPropertyPreferencesMockMvc
            .perform(get(ENTITY_API_URL_ID, tenantPropertyPreferences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tenantPropertyPreferences.getId().intValue()))
            .andExpect(jsonPath("$.favorite").value(DEFAULT_FAVORITE.booleanValue()))
            .andExpect(jsonPath("$.favoriteDate").value(DEFAULT_FAVORITE_DATE.toString()))
            .andExpect(jsonPath("$.reminder").value(DEFAULT_REMINDER.booleanValue()))
            .andExpect(jsonPath("$.reminderDate").value(DEFAULT_REMINDER_DATE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTenantPropertyPreferences() throws Exception {
        // Get the tenantPropertyPreferences
        restTenantPropertyPreferencesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTenantPropertyPreferences() throws Exception {
        // Initialize the database
        tenantPropertyPreferencesRepository.saveAndFlush(tenantPropertyPreferences);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenantPropertyPreferences
        TenantPropertyPreferences updatedTenantPropertyPreferences = tenantPropertyPreferencesRepository
            .findById(tenantPropertyPreferences.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTenantPropertyPreferences are not directly saved in db
        em.detach(updatedTenantPropertyPreferences);
        updatedTenantPropertyPreferences
            .favorite(UPDATED_FAVORITE)
            .favoriteDate(UPDATED_FAVORITE_DATE)
            .reminder(UPDATED_REMINDER)
            .reminderDate(UPDATED_REMINDER_DATE)
            .deleted(UPDATED_DELETED);
        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO = tenantPropertyPreferencesMapper.toDto(updatedTenantPropertyPreferences);

        restTenantPropertyPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tenantPropertyPreferencesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tenantPropertyPreferencesDTO))
            )
            .andExpect(status().isOk());

        // Validate the TenantPropertyPreferences in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTenantPropertyPreferencesToMatchAllProperties(updatedTenantPropertyPreferences);
    }

    @Test
    @Transactional
    void putNonExistingTenantPropertyPreferences() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenantPropertyPreferences.setId(longCount.incrementAndGet());

        // Create the TenantPropertyPreferences
        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO = tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenantPropertyPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tenantPropertyPreferencesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tenantPropertyPreferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TenantPropertyPreferences in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTenantPropertyPreferences() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenantPropertyPreferences.setId(longCount.incrementAndGet());

        // Create the TenantPropertyPreferences
        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO = tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantPropertyPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tenantPropertyPreferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TenantPropertyPreferences in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTenantPropertyPreferences() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenantPropertyPreferences.setId(longCount.incrementAndGet());

        // Create the TenantPropertyPreferences
        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO = tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantPropertyPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantPropertyPreferencesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TenantPropertyPreferences in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTenantPropertyPreferencesWithPatch() throws Exception {
        // Initialize the database
        tenantPropertyPreferencesRepository.saveAndFlush(tenantPropertyPreferences);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenantPropertyPreferences using partial update
        TenantPropertyPreferences partialUpdatedTenantPropertyPreferences = new TenantPropertyPreferences();
        partialUpdatedTenantPropertyPreferences.setId(tenantPropertyPreferences.getId());

        partialUpdatedTenantPropertyPreferences.reminder(UPDATED_REMINDER).reminderDate(UPDATED_REMINDER_DATE);

        restTenantPropertyPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTenantPropertyPreferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTenantPropertyPreferences))
            )
            .andExpect(status().isOk());

        // Validate the TenantPropertyPreferences in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTenantPropertyPreferencesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTenantPropertyPreferences, tenantPropertyPreferences),
            getPersistedTenantPropertyPreferences(tenantPropertyPreferences)
        );
    }

    @Test
    @Transactional
    void fullUpdateTenantPropertyPreferencesWithPatch() throws Exception {
        // Initialize the database
        tenantPropertyPreferencesRepository.saveAndFlush(tenantPropertyPreferences);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenantPropertyPreferences using partial update
        TenantPropertyPreferences partialUpdatedTenantPropertyPreferences = new TenantPropertyPreferences();
        partialUpdatedTenantPropertyPreferences.setId(tenantPropertyPreferences.getId());

        partialUpdatedTenantPropertyPreferences
            .favorite(UPDATED_FAVORITE)
            .favoriteDate(UPDATED_FAVORITE_DATE)
            .reminder(UPDATED_REMINDER)
            .reminderDate(UPDATED_REMINDER_DATE)
            .deleted(UPDATED_DELETED);

        restTenantPropertyPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTenantPropertyPreferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTenantPropertyPreferences))
            )
            .andExpect(status().isOk());

        // Validate the TenantPropertyPreferences in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTenantPropertyPreferencesUpdatableFieldsEquals(
            partialUpdatedTenantPropertyPreferences,
            getPersistedTenantPropertyPreferences(partialUpdatedTenantPropertyPreferences)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTenantPropertyPreferences() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenantPropertyPreferences.setId(longCount.incrementAndGet());

        // Create the TenantPropertyPreferences
        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO = tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenantPropertyPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tenantPropertyPreferencesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tenantPropertyPreferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TenantPropertyPreferences in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTenantPropertyPreferences() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenantPropertyPreferences.setId(longCount.incrementAndGet());

        // Create the TenantPropertyPreferences
        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO = tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantPropertyPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tenantPropertyPreferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TenantPropertyPreferences in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTenantPropertyPreferences() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenantPropertyPreferences.setId(longCount.incrementAndGet());

        // Create the TenantPropertyPreferences
        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO = tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantPropertyPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tenantPropertyPreferencesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TenantPropertyPreferences in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTenantPropertyPreferences() throws Exception {
        // Initialize the database
        tenantPropertyPreferencesRepository.saveAndFlush(tenantPropertyPreferences);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tenantPropertyPreferences
        restTenantPropertyPreferencesMockMvc
            .perform(delete(ENTITY_API_URL_ID, tenantPropertyPreferences.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tenantPropertyPreferencesRepository.count();
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

    protected TenantPropertyPreferences getPersistedTenantPropertyPreferences(TenantPropertyPreferences tenantPropertyPreferences) {
        return tenantPropertyPreferencesRepository.findById(tenantPropertyPreferences.getId()).orElseThrow();
    }

    protected void assertPersistedTenantPropertyPreferencesToMatchAllProperties(
        TenantPropertyPreferences expectedTenantPropertyPreferences
    ) {
        assertTenantPropertyPreferencesAllPropertiesEquals(
            expectedTenantPropertyPreferences,
            getPersistedTenantPropertyPreferences(expectedTenantPropertyPreferences)
        );
    }

    protected void assertPersistedTenantPropertyPreferencesToMatchUpdatableProperties(
        TenantPropertyPreferences expectedTenantPropertyPreferences
    ) {
        assertTenantPropertyPreferencesAllUpdatablePropertiesEquals(
            expectedTenantPropertyPreferences,
            getPersistedTenantPropertyPreferences(expectedTenantPropertyPreferences)
        );
    }
}

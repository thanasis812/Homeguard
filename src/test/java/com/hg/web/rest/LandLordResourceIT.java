package com.hg.web.rest;

import static com.hg.domain.LandLordAsserts.*;
import static com.hg.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.IntegrationTest;
import com.hg.domain.LandLord;
import com.hg.domain.enumeration.TenantStatusEnum;
import com.hg.domain.enumeration.UserCategoryEnum;
import com.hg.repository.LandLordRepository;
import com.hg.service.dto.LandLordDTO;
import com.hg.service.mapper.LandLordMapper;
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
 * Integration tests for the {@link LandLordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LandLordResourceIT {

    private static final UserCategoryEnum DEFAULT_CATEGORY = UserCategoryEnum.NORMAL;
    private static final UserCategoryEnum UPDATED_CATEGORY = UserCategoryEnum.OTHER;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final TenantStatusEnum DEFAULT_STATUS = TenantStatusEnum.ACTIVE;
    private static final TenantStatusEnum UPDATED_STATUS = TenantStatusEnum.INACTIVE;

    private static final String DEFAULT_SETTINGS_METADATA = "AAAAAAAAAA";
    private static final String UPDATED_SETTINGS_METADATA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String ENTITY_API_URL = "/api/land-lords";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LandLordRepository landLordRepository;

    @Autowired
    private LandLordMapper landLordMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLandLordMockMvc;

    private LandLord landLord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LandLord createEntity(EntityManager em) {
        LandLord landLord = new LandLord()
            .category(DEFAULT_CATEGORY)
            .createdDate(DEFAULT_CREATED_DATE)
            .status(DEFAULT_STATUS)
            .settingsMetadata(DEFAULT_SETTINGS_METADATA)
            .deleted(DEFAULT_DELETED);
        return landLord;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LandLord createUpdatedEntity(EntityManager em) {
        LandLord landLord = new LandLord()
            .category(UPDATED_CATEGORY)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .settingsMetadata(UPDATED_SETTINGS_METADATA)
            .deleted(UPDATED_DELETED);
        return landLord;
    }

    @BeforeEach
    public void initTest() {
        landLord = createEntity(em);
    }

    @Test
    @Transactional
    void createLandLord() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LandLord
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);
        var returnedLandLordDTO = om.readValue(
            restLandLordMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(landLordDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LandLordDTO.class
        );

        // Validate the LandLord in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLandLord = landLordMapper.toEntity(returnedLandLordDTO);
        assertLandLordUpdatableFieldsEquals(returnedLandLord, getPersistedLandLord(returnedLandLord));
    }

    @Test
    @Transactional
    void createLandLordWithExistingId() throws Exception {
        // Create the LandLord with an existing ID
        landLord.setId(1L);
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLandLordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(landLordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LandLord in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        landLord.setStatus(null);

        // Create the LandLord, which fails.
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);

        restLandLordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(landLordDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLandLords() throws Exception {
        // Initialize the database
        landLordRepository.saveAndFlush(landLord);

        // Get all the landLordList
        restLandLordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(landLord.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].settingsMetadata").value(hasItem(DEFAULT_SETTINGS_METADATA)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getLandLord() throws Exception {
        // Initialize the database
        landLordRepository.saveAndFlush(landLord);

        // Get the landLord
        restLandLordMockMvc
            .perform(get(ENTITY_API_URL_ID, landLord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(landLord.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.settingsMetadata").value(DEFAULT_SETTINGS_METADATA))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingLandLord() throws Exception {
        // Get the landLord
        restLandLordMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLandLord() throws Exception {
        // Initialize the database
        landLordRepository.saveAndFlush(landLord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the landLord
        LandLord updatedLandLord = landLordRepository.findById(landLord.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLandLord are not directly saved in db
        em.detach(updatedLandLord);
        updatedLandLord
            .category(UPDATED_CATEGORY)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .settingsMetadata(UPDATED_SETTINGS_METADATA)
            .deleted(UPDATED_DELETED);
        LandLordDTO landLordDTO = landLordMapper.toDto(updatedLandLord);

        restLandLordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landLordDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(landLordDTO))
            )
            .andExpect(status().isOk());

        // Validate the LandLord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLandLordToMatchAllProperties(updatedLandLord);
    }

    @Test
    @Transactional
    void putNonExistingLandLord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landLord.setId(longCount.incrementAndGet());

        // Create the LandLord
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandLordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landLordDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(landLordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandLord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLandLord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landLord.setId(longCount.incrementAndGet());

        // Create the LandLord
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandLordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(landLordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandLord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLandLord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landLord.setId(longCount.incrementAndGet());

        // Create the LandLord
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandLordMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(landLordDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LandLord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLandLordWithPatch() throws Exception {
        // Initialize the database
        landLordRepository.saveAndFlush(landLord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the landLord using partial update
        LandLord partialUpdatedLandLord = new LandLord();
        partialUpdatedLandLord.setId(landLord.getId());

        partialUpdatedLandLord
            .category(UPDATED_CATEGORY)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .settingsMetadata(UPDATED_SETTINGS_METADATA);

        restLandLordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandLord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLandLord))
            )
            .andExpect(status().isOk());

        // Validate the LandLord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLandLordUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLandLord, landLord), getPersistedLandLord(landLord));
    }

    @Test
    @Transactional
    void fullUpdateLandLordWithPatch() throws Exception {
        // Initialize the database
        landLordRepository.saveAndFlush(landLord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the landLord using partial update
        LandLord partialUpdatedLandLord = new LandLord();
        partialUpdatedLandLord.setId(landLord.getId());

        partialUpdatedLandLord
            .category(UPDATED_CATEGORY)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .settingsMetadata(UPDATED_SETTINGS_METADATA)
            .deleted(UPDATED_DELETED);

        restLandLordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandLord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLandLord))
            )
            .andExpect(status().isOk());

        // Validate the LandLord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLandLordUpdatableFieldsEquals(partialUpdatedLandLord, getPersistedLandLord(partialUpdatedLandLord));
    }

    @Test
    @Transactional
    void patchNonExistingLandLord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landLord.setId(longCount.incrementAndGet());

        // Create the LandLord
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandLordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, landLordDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(landLordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandLord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLandLord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landLord.setId(longCount.incrementAndGet());

        // Create the LandLord
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandLordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(landLordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandLord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLandLord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landLord.setId(longCount.incrementAndGet());

        // Create the LandLord
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandLordMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(landLordDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LandLord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLandLord() throws Exception {
        // Initialize the database
        landLordRepository.saveAndFlush(landLord);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the landLord
        restLandLordMockMvc
            .perform(delete(ENTITY_API_URL_ID, landLord.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return landLordRepository.count();
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

    protected LandLord getPersistedLandLord(LandLord landLord) {
        return landLordRepository.findById(landLord.getId()).orElseThrow();
    }

    protected void assertPersistedLandLordToMatchAllProperties(LandLord expectedLandLord) {
        assertLandLordAllPropertiesEquals(expectedLandLord, getPersistedLandLord(expectedLandLord));
    }

    protected void assertPersistedLandLordToMatchUpdatableProperties(LandLord expectedLandLord) {
        assertLandLordAllUpdatablePropertiesEquals(expectedLandLord, getPersistedLandLord(expectedLandLord));
    }
}

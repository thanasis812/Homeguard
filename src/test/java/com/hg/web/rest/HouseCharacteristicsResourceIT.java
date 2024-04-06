package com.hg.web.rest;

import static com.hg.domain.HouseCharacteristicsAsserts.*;
import static com.hg.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.IntegrationTest;
import com.hg.domain.HouseCharacteristics;
import com.hg.domain.enumeration.HouseCharacteristicsEnum;
import com.hg.domain.enumeration.HouseCharacteristicsGroupEnum;
import com.hg.repository.HouseCharacteristicsRepository;
import com.hg.service.dto.HouseCharacteristicsDTO;
import com.hg.service.mapper.HouseCharacteristicsMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link HouseCharacteristicsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HouseCharacteristicsResourceIT {

    private static final HouseCharacteristicsEnum DEFAULT_CODE = HouseCharacteristicsEnum.FREEZER;
    private static final HouseCharacteristicsEnum UPDATED_CODE = HouseCharacteristicsEnum.OVEN;

    private static final HouseCharacteristicsGroupEnum DEFAULT_GROUP = HouseCharacteristicsGroupEnum.PARKING;
    private static final HouseCharacteristicsGroupEnum UPDATED_GROUP = HouseCharacteristicsGroupEnum.HOUSE;

    private static final Boolean DEFAULT_PRIMARY_TO_USER = false;
    private static final Boolean UPDATED_PRIMARY_TO_USER = true;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String ENTITY_API_URL = "/api/house-characteristics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HouseCharacteristicsRepository houseCharacteristicsRepository;

    @Autowired
    private HouseCharacteristicsMapper houseCharacteristicsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHouseCharacteristicsMockMvc;

    private HouseCharacteristics houseCharacteristics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HouseCharacteristics createEntity(EntityManager em) {
        HouseCharacteristics houseCharacteristics = new HouseCharacteristics()
            .code(DEFAULT_CODE)
            .group(DEFAULT_GROUP)
            .primaryToUser(DEFAULT_PRIMARY_TO_USER)
            .deleted(DEFAULT_DELETED);
        return houseCharacteristics;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HouseCharacteristics createUpdatedEntity(EntityManager em) {
        HouseCharacteristics houseCharacteristics = new HouseCharacteristics()
            .code(UPDATED_CODE)
            .group(UPDATED_GROUP)
            .primaryToUser(UPDATED_PRIMARY_TO_USER)
            .deleted(UPDATED_DELETED);
        return houseCharacteristics;
    }

    @BeforeEach
    public void initTest() {
        houseCharacteristics = createEntity(em);
    }

    @Test
    @Transactional
    void createHouseCharacteristics() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HouseCharacteristics
        HouseCharacteristicsDTO houseCharacteristicsDTO = houseCharacteristicsMapper.toDto(houseCharacteristics);
        var returnedHouseCharacteristicsDTO = om.readValue(
            restHouseCharacteristicsMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(houseCharacteristicsDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HouseCharacteristicsDTO.class
        );

        // Validate the HouseCharacteristics in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHouseCharacteristics = houseCharacteristicsMapper.toEntity(returnedHouseCharacteristicsDTO);
        assertHouseCharacteristicsUpdatableFieldsEquals(
            returnedHouseCharacteristics,
            getPersistedHouseCharacteristics(returnedHouseCharacteristics)
        );
    }

    @Test
    @Transactional
    void createHouseCharacteristicsWithExistingId() throws Exception {
        // Create the HouseCharacteristics with an existing ID
        houseCharacteristics.setId(1L);
        HouseCharacteristicsDTO houseCharacteristicsDTO = houseCharacteristicsMapper.toDto(houseCharacteristics);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHouseCharacteristicsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(houseCharacteristicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HouseCharacteristics in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        houseCharacteristics.setCode(null);

        // Create the HouseCharacteristics, which fails.
        HouseCharacteristicsDTO houseCharacteristicsDTO = houseCharacteristicsMapper.toDto(houseCharacteristics);

        restHouseCharacteristicsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(houseCharacteristicsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHouseCharacteristics() throws Exception {
        // Initialize the database
        houseCharacteristicsRepository.saveAndFlush(houseCharacteristics);

        // Get all the houseCharacteristicsList
        restHouseCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(houseCharacteristics.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP.toString())))
            .andExpect(jsonPath("$.[*].primaryToUser").value(hasItem(DEFAULT_PRIMARY_TO_USER.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getHouseCharacteristics() throws Exception {
        // Initialize the database
        houseCharacteristicsRepository.saveAndFlush(houseCharacteristics);

        // Get the houseCharacteristics
        restHouseCharacteristicsMockMvc
            .perform(get(ENTITY_API_URL_ID, houseCharacteristics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(houseCharacteristics.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP.toString()))
            .andExpect(jsonPath("$.primaryToUser").value(DEFAULT_PRIMARY_TO_USER.booleanValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingHouseCharacteristics() throws Exception {
        // Get the houseCharacteristics
        restHouseCharacteristicsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHouseCharacteristics() throws Exception {
        // Initialize the database
        houseCharacteristicsRepository.saveAndFlush(houseCharacteristics);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the houseCharacteristics
        HouseCharacteristics updatedHouseCharacteristics = houseCharacteristicsRepository
            .findById(houseCharacteristics.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedHouseCharacteristics are not directly saved in db
        em.detach(updatedHouseCharacteristics);
        updatedHouseCharacteristics.code(UPDATED_CODE).group(UPDATED_GROUP).primaryToUser(UPDATED_PRIMARY_TO_USER).deleted(UPDATED_DELETED);
        HouseCharacteristicsDTO houseCharacteristicsDTO = houseCharacteristicsMapper.toDto(updatedHouseCharacteristics);

        restHouseCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, houseCharacteristicsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(houseCharacteristicsDTO))
            )
            .andExpect(status().isOk());

        // Validate the HouseCharacteristics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHouseCharacteristicsToMatchAllProperties(updatedHouseCharacteristics);
    }

    @Test
    @Transactional
    void putNonExistingHouseCharacteristics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        houseCharacteristics.setId(longCount.incrementAndGet());

        // Create the HouseCharacteristics
        HouseCharacteristicsDTO houseCharacteristicsDTO = houseCharacteristicsMapper.toDto(houseCharacteristics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHouseCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, houseCharacteristicsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(houseCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HouseCharacteristics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHouseCharacteristics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        houseCharacteristics.setId(longCount.incrementAndGet());

        // Create the HouseCharacteristics
        HouseCharacteristicsDTO houseCharacteristicsDTO = houseCharacteristicsMapper.toDto(houseCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHouseCharacteristicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(houseCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HouseCharacteristics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHouseCharacteristics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        houseCharacteristics.setId(longCount.incrementAndGet());

        // Create the HouseCharacteristics
        HouseCharacteristicsDTO houseCharacteristicsDTO = houseCharacteristicsMapper.toDto(houseCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHouseCharacteristicsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(houseCharacteristicsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HouseCharacteristics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHouseCharacteristicsWithPatch() throws Exception {
        // Initialize the database
        houseCharacteristicsRepository.saveAndFlush(houseCharacteristics);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the houseCharacteristics using partial update
        HouseCharacteristics partialUpdatedHouseCharacteristics = new HouseCharacteristics();
        partialUpdatedHouseCharacteristics.setId(houseCharacteristics.getId());

        partialUpdatedHouseCharacteristics
            .code(UPDATED_CODE)
            .group(UPDATED_GROUP)
            .primaryToUser(UPDATED_PRIMARY_TO_USER)
            .deleted(UPDATED_DELETED);

        restHouseCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHouseCharacteristics.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHouseCharacteristics))
            )
            .andExpect(status().isOk());

        // Validate the HouseCharacteristics in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHouseCharacteristicsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHouseCharacteristics, houseCharacteristics),
            getPersistedHouseCharacteristics(houseCharacteristics)
        );
    }

    @Test
    @Transactional
    void fullUpdateHouseCharacteristicsWithPatch() throws Exception {
        // Initialize the database
        houseCharacteristicsRepository.saveAndFlush(houseCharacteristics);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the houseCharacteristics using partial update
        HouseCharacteristics partialUpdatedHouseCharacteristics = new HouseCharacteristics();
        partialUpdatedHouseCharacteristics.setId(houseCharacteristics.getId());

        partialUpdatedHouseCharacteristics
            .code(UPDATED_CODE)
            .group(UPDATED_GROUP)
            .primaryToUser(UPDATED_PRIMARY_TO_USER)
            .deleted(UPDATED_DELETED);

        restHouseCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHouseCharacteristics.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHouseCharacteristics))
            )
            .andExpect(status().isOk());

        // Validate the HouseCharacteristics in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHouseCharacteristicsUpdatableFieldsEquals(
            partialUpdatedHouseCharacteristics,
            getPersistedHouseCharacteristics(partialUpdatedHouseCharacteristics)
        );
    }

    @Test
    @Transactional
    void patchNonExistingHouseCharacteristics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        houseCharacteristics.setId(longCount.incrementAndGet());

        // Create the HouseCharacteristics
        HouseCharacteristicsDTO houseCharacteristicsDTO = houseCharacteristicsMapper.toDto(houseCharacteristics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHouseCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, houseCharacteristicsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(houseCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HouseCharacteristics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHouseCharacteristics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        houseCharacteristics.setId(longCount.incrementAndGet());

        // Create the HouseCharacteristics
        HouseCharacteristicsDTO houseCharacteristicsDTO = houseCharacteristicsMapper.toDto(houseCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHouseCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(houseCharacteristicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HouseCharacteristics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHouseCharacteristics() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        houseCharacteristics.setId(longCount.incrementAndGet());

        // Create the HouseCharacteristics
        HouseCharacteristicsDTO houseCharacteristicsDTO = houseCharacteristicsMapper.toDto(houseCharacteristics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHouseCharacteristicsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(houseCharacteristicsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HouseCharacteristics in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHouseCharacteristics() throws Exception {
        // Initialize the database
        houseCharacteristicsRepository.saveAndFlush(houseCharacteristics);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the houseCharacteristics
        restHouseCharacteristicsMockMvc
            .perform(delete(ENTITY_API_URL_ID, houseCharacteristics.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return houseCharacteristicsRepository.count();
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

    protected HouseCharacteristics getPersistedHouseCharacteristics(HouseCharacteristics houseCharacteristics) {
        return houseCharacteristicsRepository.findById(houseCharacteristics.getId()).orElseThrow();
    }

    protected void assertPersistedHouseCharacteristicsToMatchAllProperties(HouseCharacteristics expectedHouseCharacteristics) {
        assertHouseCharacteristicsAllPropertiesEquals(
            expectedHouseCharacteristics,
            getPersistedHouseCharacteristics(expectedHouseCharacteristics)
        );
    }

    protected void assertPersistedHouseCharacteristicsToMatchUpdatableProperties(HouseCharacteristics expectedHouseCharacteristics) {
        assertHouseCharacteristicsAllUpdatablePropertiesEquals(
            expectedHouseCharacteristics,
            getPersistedHouseCharacteristics(expectedHouseCharacteristics)
        );
    }
}

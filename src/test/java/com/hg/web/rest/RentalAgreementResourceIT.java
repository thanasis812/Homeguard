package com.hg.web.rest;

import static com.hg.domain.RentalAgreementAsserts.*;
import static com.hg.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.IntegrationTest;
import com.hg.domain.RentalAgreement;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import com.hg.repository.RentalAgreementRepository;
import com.hg.service.dto.RentalAgreementDTO;
import com.hg.service.mapper.RentalAgreementMapper;
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
 * Integration tests for the {@link RentalAgreementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RentalAgreementResourceIT {

    private static final String DEFAULT_AGREEMENTS = "AAAAAAAAAA";
    private static final String UPDATED_AGREEMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_PROTOCOL = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_PROTOCOL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TENANT_SIGN = false;
    private static final Boolean UPDATED_TENANT_SIGN = true;

    private static final Boolean DEFAULT_LAND_LORD_SIGNED = false;
    private static final Boolean UPDATED_LAND_LORD_SIGNED = true;

    private static final RentalAgreementStatusEnum DEFAULT_STATUS = RentalAgreementStatusEnum.EXPIRED;
    private static final RentalAgreementStatusEnum UPDATED_STATUS = RentalAgreementStatusEnum.CANCELED;

    private static final LocalDate DEFAULT_EXPIRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LATEST = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LATEST = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String ENTITY_API_URL = "/api/rental-agreements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RentalAgreementRepository rentalAgreementRepository;

    @Autowired
    private RentalAgreementMapper rentalAgreementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRentalAgreementMockMvc;

    private RentalAgreement rentalAgreement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentalAgreement createEntity(EntityManager em) {
        RentalAgreement rentalAgreement = new RentalAgreement()
            .agreements(DEFAULT_AGREEMENTS)
            .deliveryProtocol(DEFAULT_DELIVERY_PROTOCOL)
            .tenantSign(DEFAULT_TENANT_SIGN)
            .landLordSigned(DEFAULT_LAND_LORD_SIGNED)
            .status(DEFAULT_STATUS)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .latest(DEFAULT_LATEST)
            .deleted(DEFAULT_DELETED);
        return rentalAgreement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentalAgreement createUpdatedEntity(EntityManager em) {
        RentalAgreement rentalAgreement = new RentalAgreement()
            .agreements(UPDATED_AGREEMENTS)
            .deliveryProtocol(UPDATED_DELIVERY_PROTOCOL)
            .tenantSign(UPDATED_TENANT_SIGN)
            .landLordSigned(UPDATED_LAND_LORD_SIGNED)
            .status(UPDATED_STATUS)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .latest(UPDATED_LATEST)
            .deleted(UPDATED_DELETED);
        return rentalAgreement;
    }

    @BeforeEach
    public void initTest() {
        rentalAgreement = createEntity(em);
    }

    @Test
    @Transactional
    void createRentalAgreement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RentalAgreement
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);
        var returnedRentalAgreementDTO = om.readValue(
            restRentalAgreementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalAgreementDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RentalAgreementDTO.class
        );

        // Validate the RentalAgreement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRentalAgreement = rentalAgreementMapper.toEntity(returnedRentalAgreementDTO);
        assertRentalAgreementUpdatableFieldsEquals(returnedRentalAgreement, getPersistedRentalAgreement(returnedRentalAgreement));
    }

    @Test
    @Transactional
    void createRentalAgreementWithExistingId() throws Exception {
        // Create the RentalAgreement with an existing ID
        rentalAgreement.setId(1L);
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentalAgreementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalAgreementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RentalAgreement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenantSignIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rentalAgreement.setTenantSign(null);

        // Create the RentalAgreement, which fails.
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        restRentalAgreementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalAgreementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rentalAgreement.setStatus(null);

        // Create the RentalAgreement, which fails.
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        restRentalAgreementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalAgreementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpirationDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rentalAgreement.setExpirationDate(null);

        // Create the RentalAgreement, which fails.
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        restRentalAgreementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalAgreementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rentalAgreement.setCreatedDate(null);

        // Create the RentalAgreement, which fails.
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        restRentalAgreementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalAgreementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLatestIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rentalAgreement.setLatest(null);

        // Create the RentalAgreement, which fails.
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        restRentalAgreementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalAgreementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRentalAgreements() throws Exception {
        // Initialize the database
        rentalAgreementRepository.saveAndFlush(rentalAgreement);

        // Get all the rentalAgreementList
        restRentalAgreementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rentalAgreement.getId().intValue())))
            .andExpect(jsonPath("$.[*].agreements").value(hasItem(DEFAULT_AGREEMENTS.toString())))
            .andExpect(jsonPath("$.[*].deliveryProtocol").value(hasItem(DEFAULT_DELIVERY_PROTOCOL.toString())))
            .andExpect(jsonPath("$.[*].tenantSign").value(hasItem(DEFAULT_TENANT_SIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].landLordSigned").value(hasItem(DEFAULT_LAND_LORD_SIGNED.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].latest").value(hasItem(DEFAULT_LATEST.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getRentalAgreement() throws Exception {
        // Initialize the database
        rentalAgreementRepository.saveAndFlush(rentalAgreement);

        // Get the rentalAgreement
        restRentalAgreementMockMvc
            .perform(get(ENTITY_API_URL_ID, rentalAgreement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rentalAgreement.getId().intValue()))
            .andExpect(jsonPath("$.agreements").value(DEFAULT_AGREEMENTS.toString()))
            .andExpect(jsonPath("$.deliveryProtocol").value(DEFAULT_DELIVERY_PROTOCOL.toString()))
            .andExpect(jsonPath("$.tenantSign").value(DEFAULT_TENANT_SIGN.booleanValue()))
            .andExpect(jsonPath("$.landLordSigned").value(DEFAULT_LAND_LORD_SIGNED.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.latest").value(DEFAULT_LATEST.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingRentalAgreement() throws Exception {
        // Get the rentalAgreement
        restRentalAgreementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRentalAgreement() throws Exception {
        // Initialize the database
        rentalAgreementRepository.saveAndFlush(rentalAgreement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rentalAgreement
        RentalAgreement updatedRentalAgreement = rentalAgreementRepository.findById(rentalAgreement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRentalAgreement are not directly saved in db
        em.detach(updatedRentalAgreement);
        updatedRentalAgreement
            .agreements(UPDATED_AGREEMENTS)
            .deliveryProtocol(UPDATED_DELIVERY_PROTOCOL)
            .tenantSign(UPDATED_TENANT_SIGN)
            .landLordSigned(UPDATED_LAND_LORD_SIGNED)
            .status(UPDATED_STATUS)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .latest(UPDATED_LATEST)
            .deleted(UPDATED_DELETED);
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(updatedRentalAgreement);

        restRentalAgreementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentalAgreementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentalAgreementDTO))
            )
            .andExpect(status().isOk());

        // Validate the RentalAgreement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRentalAgreementToMatchAllProperties(updatedRentalAgreement);
    }

    @Test
    @Transactional
    void putNonExistingRentalAgreement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalAgreement.setId(longCount.incrementAndGet());

        // Create the RentalAgreement
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentalAgreementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentalAgreementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentalAgreementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentalAgreement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRentalAgreement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalAgreement.setId(longCount.incrementAndGet());

        // Create the RentalAgreement
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalAgreementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentalAgreementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentalAgreement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRentalAgreement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalAgreement.setId(longCount.incrementAndGet());

        // Create the RentalAgreement
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalAgreementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalAgreementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RentalAgreement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRentalAgreementWithPatch() throws Exception {
        // Initialize the database
        rentalAgreementRepository.saveAndFlush(rentalAgreement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rentalAgreement using partial update
        RentalAgreement partialUpdatedRentalAgreement = new RentalAgreement();
        partialUpdatedRentalAgreement.setId(rentalAgreement.getId());

        partialUpdatedRentalAgreement
            .tenantSign(UPDATED_TENANT_SIGN)
            .status(UPDATED_STATUS)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .latest(UPDATED_LATEST)
            .deleted(UPDATED_DELETED);

        restRentalAgreementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRentalAgreement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRentalAgreement))
            )
            .andExpect(status().isOk());

        // Validate the RentalAgreement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentalAgreementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRentalAgreement, rentalAgreement),
            getPersistedRentalAgreement(rentalAgreement)
        );
    }

    @Test
    @Transactional
    void fullUpdateRentalAgreementWithPatch() throws Exception {
        // Initialize the database
        rentalAgreementRepository.saveAndFlush(rentalAgreement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rentalAgreement using partial update
        RentalAgreement partialUpdatedRentalAgreement = new RentalAgreement();
        partialUpdatedRentalAgreement.setId(rentalAgreement.getId());

        partialUpdatedRentalAgreement
            .agreements(UPDATED_AGREEMENTS)
            .deliveryProtocol(UPDATED_DELIVERY_PROTOCOL)
            .tenantSign(UPDATED_TENANT_SIGN)
            .landLordSigned(UPDATED_LAND_LORD_SIGNED)
            .status(UPDATED_STATUS)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .latest(UPDATED_LATEST)
            .deleted(UPDATED_DELETED);

        restRentalAgreementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRentalAgreement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRentalAgreement))
            )
            .andExpect(status().isOk());

        // Validate the RentalAgreement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentalAgreementUpdatableFieldsEquals(
            partialUpdatedRentalAgreement,
            getPersistedRentalAgreement(partialUpdatedRentalAgreement)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRentalAgreement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalAgreement.setId(longCount.incrementAndGet());

        // Create the RentalAgreement
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentalAgreementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rentalAgreementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentalAgreementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentalAgreement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRentalAgreement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalAgreement.setId(longCount.incrementAndGet());

        // Create the RentalAgreement
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalAgreementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentalAgreementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentalAgreement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRentalAgreement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalAgreement.setId(longCount.incrementAndGet());

        // Create the RentalAgreement
        RentalAgreementDTO rentalAgreementDTO = rentalAgreementMapper.toDto(rentalAgreement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalAgreementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rentalAgreementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RentalAgreement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRentalAgreement() throws Exception {
        // Initialize the database
        rentalAgreementRepository.saveAndFlush(rentalAgreement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rentalAgreement
        restRentalAgreementMockMvc
            .perform(delete(ENTITY_API_URL_ID, rentalAgreement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rentalAgreementRepository.count();
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

    protected RentalAgreement getPersistedRentalAgreement(RentalAgreement rentalAgreement) {
        return rentalAgreementRepository.findById(rentalAgreement.getId()).orElseThrow();
    }

    protected void assertPersistedRentalAgreementToMatchAllProperties(RentalAgreement expectedRentalAgreement) {
        assertRentalAgreementAllPropertiesEquals(expectedRentalAgreement, getPersistedRentalAgreement(expectedRentalAgreement));
    }

    protected void assertPersistedRentalAgreementToMatchUpdatableProperties(RentalAgreement expectedRentalAgreement) {
        assertRentalAgreementAllUpdatablePropertiesEquals(expectedRentalAgreement, getPersistedRentalAgreement(expectedRentalAgreement));
    }
}

package com.hg.web.rest;

import static com.hg.domain.ApplicationRequestAsserts.*;
import static com.hg.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.IntegrationTest;
import com.hg.domain.ApplicationRequest;
import com.hg.domain.enumeration.ApplicationRequestEnum;
import com.hg.domain.enumeration.TApplicationRequestEnum;
import com.hg.repository.ApplicationRequestRepository;
import com.hg.service.dto.ApplicationRequestDTO;
import com.hg.service.mapper.ApplicationRequestMapper;
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
 * Integration tests for the {@link ApplicationRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApplicationRequestResourceIT {

    private static final String DEFAULT_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_PAYLOAD = "BBBBBBBBBB";

    private static final TApplicationRequestEnum DEFAULT_TYPE = TApplicationRequestEnum.SALARY_REQUEST;
    private static final TApplicationRequestEnum UPDATED_TYPE = TApplicationRequestEnum.TAX_ID_REQUEST;

    private static final ApplicationRequestEnum DEFAULT_STATUS = ApplicationRequestEnum.COMPLETED;
    private static final ApplicationRequestEnum UPDATED_STATUS = ApplicationRequestEnum.ACTIVE;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/application-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;

    @Autowired
    private ApplicationRequestMapper applicationRequestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationRequestMockMvc;

    private ApplicationRequest applicationRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationRequest createEntity(EntityManager em) {
        ApplicationRequest applicationRequest = new ApplicationRequest()
            .payload(DEFAULT_PAYLOAD)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return applicationRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationRequest createUpdatedEntity(EntityManager em) {
        ApplicationRequest applicationRequest = new ApplicationRequest()
            .payload(UPDATED_PAYLOAD)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return applicationRequest;
    }

    @BeforeEach
    public void initTest() {
        applicationRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicationRequest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ApplicationRequest
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(applicationRequest);
        var returnedApplicationRequestDTO = om.readValue(
            restApplicationRequestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicationRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ApplicationRequestDTO.class
        );

        // Validate the ApplicationRequest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedApplicationRequest = applicationRequestMapper.toEntity(returnedApplicationRequestDTO);
        assertApplicationRequestUpdatableFieldsEquals(
            returnedApplicationRequest,
            getPersistedApplicationRequest(returnedApplicationRequest)
        );
    }

    @Test
    @Transactional
    void createApplicationRequestWithExistingId() throws Exception {
        // Create the ApplicationRequest with an existing ID
        applicationRequest.setId(1L);
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(applicationRequest);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicationRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        applicationRequest.setType(null);

        // Create the ApplicationRequest, which fails.
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(applicationRequest);

        restApplicationRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicationRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        applicationRequest.setStatus(null);

        // Create the ApplicationRequest, which fails.
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(applicationRequest);

        restApplicationRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicationRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        applicationRequest.setCreatedDate(null);

        // Create the ApplicationRequest, which fails.
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(applicationRequest);

        restApplicationRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicationRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApplicationRequests() throws Exception {
        // Initialize the database
        applicationRequestRepository.saveAndFlush(applicationRequest);

        // Get all the applicationRequestList
        restApplicationRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getApplicationRequest() throws Exception {
        // Initialize the database
        applicationRequestRepository.saveAndFlush(applicationRequest);

        // Get the applicationRequest
        restApplicationRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationRequest.getId().intValue()))
            .andExpect(jsonPath("$.payload").value(DEFAULT_PAYLOAD))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApplicationRequest() throws Exception {
        // Get the applicationRequest
        restApplicationRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApplicationRequest() throws Exception {
        // Initialize the database
        applicationRequestRepository.saveAndFlush(applicationRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the applicationRequest
        ApplicationRequest updatedApplicationRequest = applicationRequestRepository.findById(applicationRequest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApplicationRequest are not directly saved in db
        em.detach(updatedApplicationRequest);
        updatedApplicationRequest
            .payload(UPDATED_PAYLOAD)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(updatedApplicationRequest);

        restApplicationRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(applicationRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedApplicationRequestToMatchAllProperties(updatedApplicationRequest);
    }

    @Test
    @Transactional
    void putNonExistingApplicationRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationRequest.setId(longCount.incrementAndGet());

        // Create the ApplicationRequest
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(applicationRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(applicationRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationRequest.setId(longCount.incrementAndGet());

        // Create the ApplicationRequest
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(applicationRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(applicationRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationRequest.setId(longCount.incrementAndGet());

        // Create the ApplicationRequest
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(applicationRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicationRequestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationRequestWithPatch() throws Exception {
        // Initialize the database
        applicationRequestRepository.saveAndFlush(applicationRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the applicationRequest using partial update
        ApplicationRequest partialUpdatedApplicationRequest = new ApplicationRequest();
        partialUpdatedApplicationRequest.setId(applicationRequest.getId());

        partialUpdatedApplicationRequest.payload(UPDATED_PAYLOAD).type(UPDATED_TYPE).createdDate(UPDATED_CREATED_DATE);

        restApplicationRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApplicationRequest))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApplicationRequestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedApplicationRequest, applicationRequest),
            getPersistedApplicationRequest(applicationRequest)
        );
    }

    @Test
    @Transactional
    void fullUpdateApplicationRequestWithPatch() throws Exception {
        // Initialize the database
        applicationRequestRepository.saveAndFlush(applicationRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the applicationRequest using partial update
        ApplicationRequest partialUpdatedApplicationRequest = new ApplicationRequest();
        partialUpdatedApplicationRequest.setId(applicationRequest.getId());

        partialUpdatedApplicationRequest
            .payload(UPDATED_PAYLOAD)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restApplicationRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApplicationRequest))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApplicationRequestUpdatableFieldsEquals(
            partialUpdatedApplicationRequest,
            getPersistedApplicationRequest(partialUpdatedApplicationRequest)
        );
    }

    @Test
    @Transactional
    void patchNonExistingApplicationRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationRequest.setId(longCount.incrementAndGet());

        // Create the ApplicationRequest
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(applicationRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(applicationRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationRequest.setId(longCount.incrementAndGet());

        // Create the ApplicationRequest
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(applicationRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(applicationRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationRequest.setId(longCount.incrementAndGet());

        // Create the ApplicationRequest
        ApplicationRequestDTO applicationRequestDTO = applicationRequestMapper.toDto(applicationRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationRequestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(applicationRequestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationRequest() throws Exception {
        // Initialize the database
        applicationRequestRepository.saveAndFlush(applicationRequest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the applicationRequest
        restApplicationRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return applicationRequestRepository.count();
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

    protected ApplicationRequest getPersistedApplicationRequest(ApplicationRequest applicationRequest) {
        return applicationRequestRepository.findById(applicationRequest.getId()).orElseThrow();
    }

    protected void assertPersistedApplicationRequestToMatchAllProperties(ApplicationRequest expectedApplicationRequest) {
        assertApplicationRequestAllPropertiesEquals(expectedApplicationRequest, getPersistedApplicationRequest(expectedApplicationRequest));
    }

    protected void assertPersistedApplicationRequestToMatchUpdatableProperties(ApplicationRequest expectedApplicationRequest) {
        assertApplicationRequestAllUpdatablePropertiesEquals(
            expectedApplicationRequest,
            getPersistedApplicationRequest(expectedApplicationRequest)
        );
    }
}

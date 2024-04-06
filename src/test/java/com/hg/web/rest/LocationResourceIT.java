package com.hg.web.rest;

import static com.hg.domain.LocationAsserts.*;
import static com.hg.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.IntegrationTest;
import com.hg.domain.Location;
import com.hg.repository.LocationRepository;
import com.hg.service.dto.LocationDTO;
import com.hg.service.mapper.LocationMapper;
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
 * Integration tests for the {@link LocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocationResourceIT {

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_APARTMENT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_APARTMENT_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE_REGION = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final String DEFAULT_LOCAL_GEOGRAPHIC_DIVISION = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_GEOGRAPHIC_DIVISION = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPAL_COMMUNITY = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPAL_COMMUNITY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_ZIP_CODE = 1;
    private static final Integer UPDATED_ZIP_CODE = 2;

    private static final Integer DEFAULT_FLOOR = 1;
    private static final Integer UPDATED_FLOOR = 2;

    private static final String DEFAULT_DIRECTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationMockMvc;

    private Location location;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .apartmentUnit(DEFAULT_APARTMENT_UNIT)
            .city(DEFAULT_CITY)
            .stateProvinceRegion(DEFAULT_STATE_PROVINCE_REGION)
            .postalCode(DEFAULT_POSTAL_CODE)
            .country(DEFAULT_COUNTRY)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .localGeographicDivision(DEFAULT_LOCAL_GEOGRAPHIC_DIVISION)
            .municipalCommunity(DEFAULT_MUNICIPAL_COMMUNITY)
            .additionalNotes(DEFAULT_ADDITIONAL_NOTES)
            .number(DEFAULT_NUMBER)
            .zipCode(DEFAULT_ZIP_CODE)
            .floor(DEFAULT_FLOOR)
            .directions(DEFAULT_DIRECTIONS);
        return location;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createUpdatedEntity(EntityManager em) {
        Location location = new Location()
            .streetAddress(UPDATED_STREET_ADDRESS)
            .apartmentUnit(UPDATED_APARTMENT_UNIT)
            .city(UPDATED_CITY)
            .stateProvinceRegion(UPDATED_STATE_PROVINCE_REGION)
            .postalCode(UPDATED_POSTAL_CODE)
            .country(UPDATED_COUNTRY)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .localGeographicDivision(UPDATED_LOCAL_GEOGRAPHIC_DIVISION)
            .municipalCommunity(UPDATED_MUNICIPAL_COMMUNITY)
            .additionalNotes(UPDATED_ADDITIONAL_NOTES)
            .number(UPDATED_NUMBER)
            .zipCode(UPDATED_ZIP_CODE)
            .floor(UPDATED_FLOOR)
            .directions(UPDATED_DIRECTIONS);
        return location;
    }

    @BeforeEach
    public void initTest() {
        location = createEntity(em);
    }

    @Test
    @Transactional
    void createLocation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);
        var returnedLocationDTO = om.readValue(
            restLocationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LocationDTO.class
        );

        // Validate the Location in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLocation = locationMapper.toEntity(returnedLocationDTO);
        assertLocationUpdatableFieldsEquals(returnedLocation, getPersistedLocation(returnedLocation));
    }

    @Test
    @Transactional
    void createLocationWithExistingId() throws Exception {
        // Create the Location with an existing ID
        location.setId(1L);
        LocationDTO locationDTO = locationMapper.toDto(location);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStreetAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setStreetAddress(null);

        // Create the Location, which fails.
        LocationDTO locationDTO = locationMapper.toDto(location);

        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setCity(null);

        // Create the Location, which fails.
        LocationDTO locationDTO = locationMapper.toDto(location);

        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostalCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setPostalCode(null);

        // Create the Location, which fails.
        LocationDTO locationDTO = locationMapper.toDto(location);

        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setCountry(null);

        // Create the Location, which fails.
        LocationDTO locationDTO = locationMapper.toDto(location);

        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLatitudeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setLatitude(null);

        // Create the Location, which fails.
        LocationDTO locationDTO = locationMapper.toDto(location);

        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLongitudeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setLongitude(null);

        // Create the Location, which fails.
        LocationDTO locationDTO = locationMapper.toDto(location);

        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkZipCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setZipCode(null);

        // Create the Location, which fails.
        LocationDTO locationDTO = locationMapper.toDto(location);

        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList
        restLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].apartmentUnit").value(hasItem(DEFAULT_APARTMENT_UNIT)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvinceRegion").value(hasItem(DEFAULT_STATE_PROVINCE_REGION)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].localGeographicDivision").value(hasItem(DEFAULT_LOCAL_GEOGRAPHIC_DIVISION)))
            .andExpect(jsonPath("$.[*].municipalCommunity").value(hasItem(DEFAULT_MUNICIPAL_COMMUNITY)))
            .andExpect(jsonPath("$.[*].additionalNotes").value(hasItem(DEFAULT_ADDITIONAL_NOTES.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].directions").value(hasItem(DEFAULT_DIRECTIONS)));
    }

    @Test
    @Transactional
    void getLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get the location
        restLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId().intValue()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.apartmentUnit").value(DEFAULT_APARTMENT_UNIT))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateProvinceRegion").value(DEFAULT_STATE_PROVINCE_REGION))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.localGeographicDivision").value(DEFAULT_LOCAL_GEOGRAPHIC_DIVISION))
            .andExpect(jsonPath("$.municipalCommunity").value(DEFAULT_MUNICIPAL_COMMUNITY))
            .andExpect(jsonPath("$.additionalNotes").value(DEFAULT_ADDITIONAL_NOTES.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR))
            .andExpect(jsonPath("$.directions").value(DEFAULT_DIRECTIONS));
    }

    @Test
    @Transactional
    void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location
        Location updatedLocation = locationRepository.findById(location.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLocation are not directly saved in db
        em.detach(updatedLocation);
        updatedLocation
            .streetAddress(UPDATED_STREET_ADDRESS)
            .apartmentUnit(UPDATED_APARTMENT_UNIT)
            .city(UPDATED_CITY)
            .stateProvinceRegion(UPDATED_STATE_PROVINCE_REGION)
            .postalCode(UPDATED_POSTAL_CODE)
            .country(UPDATED_COUNTRY)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .localGeographicDivision(UPDATED_LOCAL_GEOGRAPHIC_DIVISION)
            .municipalCommunity(UPDATED_MUNICIPAL_COMMUNITY)
            .additionalNotes(UPDATED_ADDITIONAL_NOTES)
            .number(UPDATED_NUMBER)
            .zipCode(UPDATED_ZIP_CODE)
            .floor(UPDATED_FLOOR)
            .directions(UPDATED_DIRECTIONS);
        LocationDTO locationDTO = locationMapper.toDto(updatedLocation);

        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocationToMatchAllProperties(updatedLocation);
    }

    @Test
    @Transactional
    void putNonExistingLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .apartmentUnit(UPDATED_APARTMENT_UNIT)
            .postalCode(UPDATED_POSTAL_CODE)
            .longitude(UPDATED_LONGITUDE)
            .localGeographicDivision(UPDATED_LOCAL_GEOGRAPHIC_DIVISION)
            .floor(UPDATED_FLOOR);

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLocation, location), getPersistedLocation(location));
    }

    @Test
    @Transactional
    void fullUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .streetAddress(UPDATED_STREET_ADDRESS)
            .apartmentUnit(UPDATED_APARTMENT_UNIT)
            .city(UPDATED_CITY)
            .stateProvinceRegion(UPDATED_STATE_PROVINCE_REGION)
            .postalCode(UPDATED_POSTAL_CODE)
            .country(UPDATED_COUNTRY)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .localGeographicDivision(UPDATED_LOCAL_GEOGRAPHIC_DIVISION)
            .municipalCommunity(UPDATED_MUNICIPAL_COMMUNITY)
            .additionalNotes(UPDATED_ADDITIONAL_NOTES)
            .number(UPDATED_NUMBER)
            .zipCode(UPDATED_ZIP_CODE)
            .floor(UPDATED_FLOOR)
            .directions(UPDATED_DIRECTIONS);

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationUpdatableFieldsEquals(partialUpdatedLocation, getPersistedLocation(partialUpdatedLocation));
    }

    @Test
    @Transactional
    void patchNonExistingLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(locationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the location
        restLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, location.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locationRepository.count();
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

    protected Location getPersistedLocation(Location location) {
        return locationRepository.findById(location.getId()).orElseThrow();
    }

    protected void assertPersistedLocationToMatchAllProperties(Location expectedLocation) {
        assertLocationAllPropertiesEquals(expectedLocation, getPersistedLocation(expectedLocation));
    }

    protected void assertPersistedLocationToMatchUpdatableProperties(Location expectedLocation) {
        assertLocationAllUpdatablePropertiesEquals(expectedLocation, getPersistedLocation(expectedLocation));
    }
}

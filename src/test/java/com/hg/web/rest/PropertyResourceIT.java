package com.hg.web.rest;

import static com.hg.domain.PropertyAsserts.*;
import static com.hg.web.rest.TestUtil.createUpdateProxyForBean;
import static com.hg.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.IntegrationTest;
import com.hg.domain.LandLord;
import com.hg.domain.Location;
import com.hg.domain.Property;
import com.hg.domain.enumeration.ConstructionEnum;
import com.hg.repository.PropertyRepository;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.mapper.PropertyMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link PropertyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PropertyResourceIT {

    private static final Boolean DEFAULT_VERIFIED = false;
    private static final Boolean UPDATED_VERIFIED = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SQUARE_METERS = new BigDecimal(1);
    private static final BigDecimal UPDATED_SQUARE_METERS = new BigDecimal(2);
    private static final BigDecimal SMALLER_SQUARE_METERS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PLOT_SQUARE_METERS = new BigDecimal(1);
    private static final BigDecimal UPDATED_PLOT_SQUARE_METERS = new BigDecimal(2);
    private static final BigDecimal SMALLER_PLOT_SQUARE_METERS = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_NUMBER_OF_BATHROOMS = 1;
    private static final Integer UPDATED_NUMBER_OF_BATHROOMS = 2;
    private static final Integer SMALLER_NUMBER_OF_BATHROOMS = 1 - 1;

    private static final Integer DEFAULT_NUMBER_OF_BEDROOMS = 1;
    private static final Integer UPDATED_NUMBER_OF_BEDROOMS = 2;
    private static final Integer SMALLER_NUMBER_OF_BEDROOMS = 1 - 1;

    private static final Integer DEFAULT_NUMBER_OF_KITCHENS = 1;
    private static final Integer UPDATED_NUMBER_OF_KITCHENS = 2;
    private static final Integer SMALLER_NUMBER_OF_KITCHENS = 1 - 1;

    private static final Integer DEFAULT_NUMBER_OF_AIR_CONDITIONER = 1;
    private static final Integer UPDATED_NUMBER_OF_AIR_CONDITIONER = 2;
    private static final Integer SMALLER_NUMBER_OF_AIR_CONDITIONER = 1 - 1;

    private static final String DEFAULT_HOUSE_RULES = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_RULES = "BBBBBBBBBB";

    private static final Integer DEFAULT_CONTRACT_YEARS = 1;
    private static final Integer UPDATED_CONTRACT_YEARS = 2;
    private static final Integer SMALLER_CONTRACT_YEARS = 1 - 1;

    private static final LocalDate DEFAULT_NEXT_AVAILABLE_DATE_FOR_RENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NEXT_AVAILABLE_DATE_FOR_RENT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_NEXT_AVAILABLE_DATE_FOR_RENT = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_THUMBNAIL = 1L;
    private static final Long UPDATED_THUMBNAIL = 2L;
    private static final Long SMALLER_THUMBNAIL = 1L - 1L;

    private static final String DEFAULT_HOUSE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_FLOOR = 1;
    private static final Integer UPDATED_FLOOR = 2;
    private static final Integer SMALLER_FLOOR = 1 - 1;

    private static final Integer DEFAULT_NUMBER_OF_FLATS = 1;
    private static final Integer UPDATED_NUMBER_OF_FLATS = 2;
    private static final Integer SMALLER_NUMBER_OF_FLATS = 1 - 1;

    private static final String DEFAULT_ENERGY_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_ENERGY_CLASS = "BBBBBBBBBB";

    private static final ConstructionEnum DEFAULT_CONSTRUCTION = ConstructionEnum.APARTMENT;
    private static final ConstructionEnum UPDATED_CONSTRUCTION = ConstructionEnum.FLAT;

    private static final Integer DEFAULT_YEAR_OF_MANUFACTURE = 1;
    private static final Integer UPDATED_YEAR_OF_MANUFACTURE = 2;
    private static final Integer SMALLER_YEAR_OF_MANUFACTURE = 1 - 1;

    private static final Integer DEFAULT_YEAR_OF_RENOVATION = 1;
    private static final Integer UPDATED_YEAR_OF_RENOVATION = 2;
    private static final Integer SMALLER_YEAR_OF_RENOVATION = 1 - 1;

    private static final String DEFAULT_PROPERTY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FURNITURED = false;
    private static final Boolean UPDATED_FURNITURED = true;

    private static final String DEFAULT_FURNITURED_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FURNITURED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String ENTITY_API_URL = "/api/properties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPropertyMockMvc;

    private Property property;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createEntity(EntityManager em) {
        Property property = new Property()
            .verified(DEFAULT_VERIFIED)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .squareMeters(DEFAULT_SQUARE_METERS)
            .plotSquareMeters(DEFAULT_PLOT_SQUARE_METERS)
            .numberOfBathrooms(DEFAULT_NUMBER_OF_BATHROOMS)
            .numberOfBedrooms(DEFAULT_NUMBER_OF_BEDROOMS)
            .numberOfKitchens(DEFAULT_NUMBER_OF_KITCHENS)
            .numberOfAirConditioner(DEFAULT_NUMBER_OF_AIR_CONDITIONER)
            .houseRules(DEFAULT_HOUSE_RULES)
            .contractYears(DEFAULT_CONTRACT_YEARS)
            .nextAvailableDateForRent(DEFAULT_NEXT_AVAILABLE_DATE_FOR_RENT)
            .thumbnail(DEFAULT_THUMBNAIL)
            .houseType(DEFAULT_HOUSE_TYPE)
            .floor(DEFAULT_FLOOR)
            .numberOfFlats(DEFAULT_NUMBER_OF_FLATS)
            .energyClass(DEFAULT_ENERGY_CLASS)
            .construction(DEFAULT_CONSTRUCTION)
            .yearOfManufacture(DEFAULT_YEAR_OF_MANUFACTURE)
            .yearOfRenovation(DEFAULT_YEAR_OF_RENOVATION)
            .propertyCode(DEFAULT_PROPERTY_CODE)
            .furnitured(DEFAULT_FURNITURED)
            .furnituredDescription(DEFAULT_FURNITURED_DESCRIPTION)
            .deleted(DEFAULT_DELETED);
        return property;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createUpdatedEntity(EntityManager em) {
        Property property = new Property()
            .verified(UPDATED_VERIFIED)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .squareMeters(UPDATED_SQUARE_METERS)
            .plotSquareMeters(UPDATED_PLOT_SQUARE_METERS)
            .numberOfBathrooms(UPDATED_NUMBER_OF_BATHROOMS)
            .numberOfBedrooms(UPDATED_NUMBER_OF_BEDROOMS)
            .numberOfKitchens(UPDATED_NUMBER_OF_KITCHENS)
            .numberOfAirConditioner(UPDATED_NUMBER_OF_AIR_CONDITIONER)
            .houseRules(UPDATED_HOUSE_RULES)
            .contractYears(UPDATED_CONTRACT_YEARS)
            .nextAvailableDateForRent(UPDATED_NEXT_AVAILABLE_DATE_FOR_RENT)
            .thumbnail(UPDATED_THUMBNAIL)
            .houseType(UPDATED_HOUSE_TYPE)
            .floor(UPDATED_FLOOR)
            .numberOfFlats(UPDATED_NUMBER_OF_FLATS)
            .energyClass(UPDATED_ENERGY_CLASS)
            .construction(UPDATED_CONSTRUCTION)
            .yearOfManufacture(UPDATED_YEAR_OF_MANUFACTURE)
            .yearOfRenovation(UPDATED_YEAR_OF_RENOVATION)
            .propertyCode(UPDATED_PROPERTY_CODE)
            .furnitured(UPDATED_FURNITURED)
            .furnituredDescription(UPDATED_FURNITURED_DESCRIPTION)
            .deleted(UPDATED_DELETED);
        return property;
    }

    @BeforeEach
    public void initTest() {
        property = createEntity(em);
    }

    @Test
    @Transactional
    void createProperty() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);
        var returnedPropertyDTO = om.readValue(
            restPropertyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PropertyDTO.class
        );

        // Validate the Property in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProperty = propertyMapper.toEntity(returnedPropertyDTO);
        assertPropertyUpdatableFieldsEquals(returnedProperty, getPersistedProperty(returnedProperty));
    }

    @Test
    @Transactional
    void createPropertyWithExistingId() throws Exception {
        // Create the Property with an existing ID
        property.setId(1L);
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        property.setPrice(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList
        restPropertyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].squareMeters").value(hasItem(sameNumber(DEFAULT_SQUARE_METERS))))
            .andExpect(jsonPath("$.[*].plotSquareMeters").value(hasItem(sameNumber(DEFAULT_PLOT_SQUARE_METERS))))
            .andExpect(jsonPath("$.[*].numberOfBathrooms").value(hasItem(DEFAULT_NUMBER_OF_BATHROOMS)))
            .andExpect(jsonPath("$.[*].numberOfBedrooms").value(hasItem(DEFAULT_NUMBER_OF_BEDROOMS)))
            .andExpect(jsonPath("$.[*].numberOfKitchens").value(hasItem(DEFAULT_NUMBER_OF_KITCHENS)))
            .andExpect(jsonPath("$.[*].numberOfAirConditioner").value(hasItem(DEFAULT_NUMBER_OF_AIR_CONDITIONER)))
            .andExpect(jsonPath("$.[*].houseRules").value(hasItem(DEFAULT_HOUSE_RULES.toString())))
            .andExpect(jsonPath("$.[*].contractYears").value(hasItem(DEFAULT_CONTRACT_YEARS)))
            .andExpect(jsonPath("$.[*].nextAvailableDateForRent").value(hasItem(DEFAULT_NEXT_AVAILABLE_DATE_FOR_RENT.toString())))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL.intValue())))
            .andExpect(jsonPath("$.[*].houseType").value(hasItem(DEFAULT_HOUSE_TYPE)))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].numberOfFlats").value(hasItem(DEFAULT_NUMBER_OF_FLATS)))
            .andExpect(jsonPath("$.[*].energyClass").value(hasItem(DEFAULT_ENERGY_CLASS)))
            .andExpect(jsonPath("$.[*].construction").value(hasItem(DEFAULT_CONSTRUCTION.toString())))
            .andExpect(jsonPath("$.[*].yearOfManufacture").value(hasItem(DEFAULT_YEAR_OF_MANUFACTURE)))
            .andExpect(jsonPath("$.[*].yearOfRenovation").value(hasItem(DEFAULT_YEAR_OF_RENOVATION)))
            .andExpect(jsonPath("$.[*].propertyCode").value(hasItem(DEFAULT_PROPERTY_CODE)))
            .andExpect(jsonPath("$.[*].furnitured").value(hasItem(DEFAULT_FURNITURED.booleanValue())))
            .andExpect(jsonPath("$.[*].furnituredDescription").value(hasItem(DEFAULT_FURNITURED_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get the property
        restPropertyMockMvc
            .perform(get(ENTITY_API_URL_ID, property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(property.getId().intValue()))
            .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.squareMeters").value(sameNumber(DEFAULT_SQUARE_METERS)))
            .andExpect(jsonPath("$.plotSquareMeters").value(sameNumber(DEFAULT_PLOT_SQUARE_METERS)))
            .andExpect(jsonPath("$.numberOfBathrooms").value(DEFAULT_NUMBER_OF_BATHROOMS))
            .andExpect(jsonPath("$.numberOfBedrooms").value(DEFAULT_NUMBER_OF_BEDROOMS))
            .andExpect(jsonPath("$.numberOfKitchens").value(DEFAULT_NUMBER_OF_KITCHENS))
            .andExpect(jsonPath("$.numberOfAirConditioner").value(DEFAULT_NUMBER_OF_AIR_CONDITIONER))
            .andExpect(jsonPath("$.houseRules").value(DEFAULT_HOUSE_RULES.toString()))
            .andExpect(jsonPath("$.contractYears").value(DEFAULT_CONTRACT_YEARS))
            .andExpect(jsonPath("$.nextAvailableDateForRent").value(DEFAULT_NEXT_AVAILABLE_DATE_FOR_RENT.toString()))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL.intValue()))
            .andExpect(jsonPath("$.houseType").value(DEFAULT_HOUSE_TYPE))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR))
            .andExpect(jsonPath("$.numberOfFlats").value(DEFAULT_NUMBER_OF_FLATS))
            .andExpect(jsonPath("$.energyClass").value(DEFAULT_ENERGY_CLASS))
            .andExpect(jsonPath("$.construction").value(DEFAULT_CONSTRUCTION.toString()))
            .andExpect(jsonPath("$.yearOfManufacture").value(DEFAULT_YEAR_OF_MANUFACTURE))
            .andExpect(jsonPath("$.yearOfRenovation").value(DEFAULT_YEAR_OF_RENOVATION))
            .andExpect(jsonPath("$.propertyCode").value(DEFAULT_PROPERTY_CODE))
            .andExpect(jsonPath("$.furnitured").value(DEFAULT_FURNITURED.booleanValue()))
            .andExpect(jsonPath("$.furnituredDescription").value(DEFAULT_FURNITURED_DESCRIPTION))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getPropertiesByIdFiltering() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        Long id = property.getId();

        defaultPropertyFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPropertyFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPropertyFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPropertiesByVerifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where verified equals to
        defaultPropertyFiltering("verified.equals=" + DEFAULT_VERIFIED, "verified.equals=" + UPDATED_VERIFIED);
    }

    @Test
    @Transactional
    void getAllPropertiesByVerifiedIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where verified in
        defaultPropertyFiltering("verified.in=" + DEFAULT_VERIFIED + "," + UPDATED_VERIFIED, "verified.in=" + UPDATED_VERIFIED);
    }

    @Test
    @Transactional
    void getAllPropertiesByVerifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where verified is not null
        defaultPropertyFiltering("verified.specified=true", "verified.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where name equals to
        defaultPropertyFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPropertiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where name in
        defaultPropertyFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPropertiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where name is not null
        defaultPropertyFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByNameContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where name contains
        defaultPropertyFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPropertiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where name does not contain
        defaultPropertyFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllPropertiesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where description equals to
        defaultPropertyFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPropertiesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where description in
        defaultPropertyFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where description is not null
        defaultPropertyFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where description contains
        defaultPropertyFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPropertiesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where description does not contain
        defaultPropertyFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPropertiesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where price equals to
        defaultPropertyFiltering("price.equals=" + DEFAULT_PRICE, "price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllPropertiesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where price in
        defaultPropertyFiltering("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE, "price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllPropertiesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where price is not null
        defaultPropertyFiltering("price.specified=true", "price.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where price is greater than or equal to
        defaultPropertyFiltering("price.greaterThanOrEqual=" + DEFAULT_PRICE, "price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllPropertiesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where price is less than or equal to
        defaultPropertyFiltering("price.lessThanOrEqual=" + DEFAULT_PRICE, "price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllPropertiesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where price is less than
        defaultPropertyFiltering("price.lessThan=" + UPDATED_PRICE, "price.lessThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllPropertiesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where price is greater than
        defaultPropertyFiltering("price.greaterThan=" + SMALLER_PRICE, "price.greaterThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllPropertiesBySquareMetersIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where squareMeters equals to
        defaultPropertyFiltering("squareMeters.equals=" + DEFAULT_SQUARE_METERS, "squareMeters.equals=" + UPDATED_SQUARE_METERS);
    }

    @Test
    @Transactional
    void getAllPropertiesBySquareMetersIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where squareMeters in
        defaultPropertyFiltering(
            "squareMeters.in=" + DEFAULT_SQUARE_METERS + "," + UPDATED_SQUARE_METERS,
            "squareMeters.in=" + UPDATED_SQUARE_METERS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesBySquareMetersIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where squareMeters is not null
        defaultPropertyFiltering("squareMeters.specified=true", "squareMeters.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesBySquareMetersIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where squareMeters is greater than or equal to
        defaultPropertyFiltering(
            "squareMeters.greaterThanOrEqual=" + DEFAULT_SQUARE_METERS,
            "squareMeters.greaterThanOrEqual=" + UPDATED_SQUARE_METERS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesBySquareMetersIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where squareMeters is less than or equal to
        defaultPropertyFiltering(
            "squareMeters.lessThanOrEqual=" + DEFAULT_SQUARE_METERS,
            "squareMeters.lessThanOrEqual=" + SMALLER_SQUARE_METERS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesBySquareMetersIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where squareMeters is less than
        defaultPropertyFiltering("squareMeters.lessThan=" + UPDATED_SQUARE_METERS, "squareMeters.lessThan=" + DEFAULT_SQUARE_METERS);
    }

    @Test
    @Transactional
    void getAllPropertiesBySquareMetersIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where squareMeters is greater than
        defaultPropertyFiltering("squareMeters.greaterThan=" + SMALLER_SQUARE_METERS, "squareMeters.greaterThan=" + DEFAULT_SQUARE_METERS);
    }

    @Test
    @Transactional
    void getAllPropertiesByPlotSquareMetersIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where plotSquareMeters equals to
        defaultPropertyFiltering(
            "plotSquareMeters.equals=" + DEFAULT_PLOT_SQUARE_METERS,
            "plotSquareMeters.equals=" + UPDATED_PLOT_SQUARE_METERS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByPlotSquareMetersIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where plotSquareMeters in
        defaultPropertyFiltering(
            "plotSquareMeters.in=" + DEFAULT_PLOT_SQUARE_METERS + "," + UPDATED_PLOT_SQUARE_METERS,
            "plotSquareMeters.in=" + UPDATED_PLOT_SQUARE_METERS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByPlotSquareMetersIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where plotSquareMeters is not null
        defaultPropertyFiltering("plotSquareMeters.specified=true", "plotSquareMeters.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByPlotSquareMetersIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where plotSquareMeters is greater than or equal to
        defaultPropertyFiltering(
            "plotSquareMeters.greaterThanOrEqual=" + DEFAULT_PLOT_SQUARE_METERS,
            "plotSquareMeters.greaterThanOrEqual=" + UPDATED_PLOT_SQUARE_METERS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByPlotSquareMetersIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where plotSquareMeters is less than or equal to
        defaultPropertyFiltering(
            "plotSquareMeters.lessThanOrEqual=" + DEFAULT_PLOT_SQUARE_METERS,
            "plotSquareMeters.lessThanOrEqual=" + SMALLER_PLOT_SQUARE_METERS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByPlotSquareMetersIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where plotSquareMeters is less than
        defaultPropertyFiltering(
            "plotSquareMeters.lessThan=" + UPDATED_PLOT_SQUARE_METERS,
            "plotSquareMeters.lessThan=" + DEFAULT_PLOT_SQUARE_METERS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByPlotSquareMetersIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where plotSquareMeters is greater than
        defaultPropertyFiltering(
            "plotSquareMeters.greaterThan=" + SMALLER_PLOT_SQUARE_METERS,
            "plotSquareMeters.greaterThan=" + DEFAULT_PLOT_SQUARE_METERS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBathroomsIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBathrooms equals to
        defaultPropertyFiltering(
            "numberOfBathrooms.equals=" + DEFAULT_NUMBER_OF_BATHROOMS,
            "numberOfBathrooms.equals=" + UPDATED_NUMBER_OF_BATHROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBathroomsIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBathrooms in
        defaultPropertyFiltering(
            "numberOfBathrooms.in=" + DEFAULT_NUMBER_OF_BATHROOMS + "," + UPDATED_NUMBER_OF_BATHROOMS,
            "numberOfBathrooms.in=" + UPDATED_NUMBER_OF_BATHROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBathroomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBathrooms is not null
        defaultPropertyFiltering("numberOfBathrooms.specified=true", "numberOfBathrooms.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBathroomsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBathrooms is greater than or equal to
        defaultPropertyFiltering(
            "numberOfBathrooms.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_BATHROOMS,
            "numberOfBathrooms.greaterThanOrEqual=" + UPDATED_NUMBER_OF_BATHROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBathroomsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBathrooms is less than or equal to
        defaultPropertyFiltering(
            "numberOfBathrooms.lessThanOrEqual=" + DEFAULT_NUMBER_OF_BATHROOMS,
            "numberOfBathrooms.lessThanOrEqual=" + SMALLER_NUMBER_OF_BATHROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBathroomsIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBathrooms is less than
        defaultPropertyFiltering(
            "numberOfBathrooms.lessThan=" + UPDATED_NUMBER_OF_BATHROOMS,
            "numberOfBathrooms.lessThan=" + DEFAULT_NUMBER_OF_BATHROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBathroomsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBathrooms is greater than
        defaultPropertyFiltering(
            "numberOfBathrooms.greaterThan=" + SMALLER_NUMBER_OF_BATHROOMS,
            "numberOfBathrooms.greaterThan=" + DEFAULT_NUMBER_OF_BATHROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBedroomsIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBedrooms equals to
        defaultPropertyFiltering(
            "numberOfBedrooms.equals=" + DEFAULT_NUMBER_OF_BEDROOMS,
            "numberOfBedrooms.equals=" + UPDATED_NUMBER_OF_BEDROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBedroomsIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBedrooms in
        defaultPropertyFiltering(
            "numberOfBedrooms.in=" + DEFAULT_NUMBER_OF_BEDROOMS + "," + UPDATED_NUMBER_OF_BEDROOMS,
            "numberOfBedrooms.in=" + UPDATED_NUMBER_OF_BEDROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBedroomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBedrooms is not null
        defaultPropertyFiltering("numberOfBedrooms.specified=true", "numberOfBedrooms.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBedroomsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBedrooms is greater than or equal to
        defaultPropertyFiltering(
            "numberOfBedrooms.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_BEDROOMS,
            "numberOfBedrooms.greaterThanOrEqual=" + UPDATED_NUMBER_OF_BEDROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBedroomsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBedrooms is less than or equal to
        defaultPropertyFiltering(
            "numberOfBedrooms.lessThanOrEqual=" + DEFAULT_NUMBER_OF_BEDROOMS,
            "numberOfBedrooms.lessThanOrEqual=" + SMALLER_NUMBER_OF_BEDROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBedroomsIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBedrooms is less than
        defaultPropertyFiltering(
            "numberOfBedrooms.lessThan=" + UPDATED_NUMBER_OF_BEDROOMS,
            "numberOfBedrooms.lessThan=" + DEFAULT_NUMBER_OF_BEDROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfBedroomsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfBedrooms is greater than
        defaultPropertyFiltering(
            "numberOfBedrooms.greaterThan=" + SMALLER_NUMBER_OF_BEDROOMS,
            "numberOfBedrooms.greaterThan=" + DEFAULT_NUMBER_OF_BEDROOMS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfKitchensIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfKitchens equals to
        defaultPropertyFiltering(
            "numberOfKitchens.equals=" + DEFAULT_NUMBER_OF_KITCHENS,
            "numberOfKitchens.equals=" + UPDATED_NUMBER_OF_KITCHENS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfKitchensIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfKitchens in
        defaultPropertyFiltering(
            "numberOfKitchens.in=" + DEFAULT_NUMBER_OF_KITCHENS + "," + UPDATED_NUMBER_OF_KITCHENS,
            "numberOfKitchens.in=" + UPDATED_NUMBER_OF_KITCHENS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfKitchensIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfKitchens is not null
        defaultPropertyFiltering("numberOfKitchens.specified=true", "numberOfKitchens.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfKitchensIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfKitchens is greater than or equal to
        defaultPropertyFiltering(
            "numberOfKitchens.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_KITCHENS,
            "numberOfKitchens.greaterThanOrEqual=" + UPDATED_NUMBER_OF_KITCHENS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfKitchensIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfKitchens is less than or equal to
        defaultPropertyFiltering(
            "numberOfKitchens.lessThanOrEqual=" + DEFAULT_NUMBER_OF_KITCHENS,
            "numberOfKitchens.lessThanOrEqual=" + SMALLER_NUMBER_OF_KITCHENS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfKitchensIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfKitchens is less than
        defaultPropertyFiltering(
            "numberOfKitchens.lessThan=" + UPDATED_NUMBER_OF_KITCHENS,
            "numberOfKitchens.lessThan=" + DEFAULT_NUMBER_OF_KITCHENS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfKitchensIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfKitchens is greater than
        defaultPropertyFiltering(
            "numberOfKitchens.greaterThan=" + SMALLER_NUMBER_OF_KITCHENS,
            "numberOfKitchens.greaterThan=" + DEFAULT_NUMBER_OF_KITCHENS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfAirConditionerIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfAirConditioner equals to
        defaultPropertyFiltering(
            "numberOfAirConditioner.equals=" + DEFAULT_NUMBER_OF_AIR_CONDITIONER,
            "numberOfAirConditioner.equals=" + UPDATED_NUMBER_OF_AIR_CONDITIONER
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfAirConditionerIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfAirConditioner in
        defaultPropertyFiltering(
            "numberOfAirConditioner.in=" + DEFAULT_NUMBER_OF_AIR_CONDITIONER + "," + UPDATED_NUMBER_OF_AIR_CONDITIONER,
            "numberOfAirConditioner.in=" + UPDATED_NUMBER_OF_AIR_CONDITIONER
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfAirConditionerIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfAirConditioner is not null
        defaultPropertyFiltering("numberOfAirConditioner.specified=true", "numberOfAirConditioner.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfAirConditionerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfAirConditioner is greater than or equal to
        defaultPropertyFiltering(
            "numberOfAirConditioner.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_AIR_CONDITIONER,
            "numberOfAirConditioner.greaterThanOrEqual=" + UPDATED_NUMBER_OF_AIR_CONDITIONER
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfAirConditionerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfAirConditioner is less than or equal to
        defaultPropertyFiltering(
            "numberOfAirConditioner.lessThanOrEqual=" + DEFAULT_NUMBER_OF_AIR_CONDITIONER,
            "numberOfAirConditioner.lessThanOrEqual=" + SMALLER_NUMBER_OF_AIR_CONDITIONER
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfAirConditionerIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfAirConditioner is less than
        defaultPropertyFiltering(
            "numberOfAirConditioner.lessThan=" + UPDATED_NUMBER_OF_AIR_CONDITIONER,
            "numberOfAirConditioner.lessThan=" + DEFAULT_NUMBER_OF_AIR_CONDITIONER
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfAirConditionerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfAirConditioner is greater than
        defaultPropertyFiltering(
            "numberOfAirConditioner.greaterThan=" + SMALLER_NUMBER_OF_AIR_CONDITIONER,
            "numberOfAirConditioner.greaterThan=" + DEFAULT_NUMBER_OF_AIR_CONDITIONER
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByContractYearsIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contractYears equals to
        defaultPropertyFiltering("contractYears.equals=" + DEFAULT_CONTRACT_YEARS, "contractYears.equals=" + UPDATED_CONTRACT_YEARS);
    }

    @Test
    @Transactional
    void getAllPropertiesByContractYearsIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contractYears in
        defaultPropertyFiltering(
            "contractYears.in=" + DEFAULT_CONTRACT_YEARS + "," + UPDATED_CONTRACT_YEARS,
            "contractYears.in=" + UPDATED_CONTRACT_YEARS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByContractYearsIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contractYears is not null
        defaultPropertyFiltering("contractYears.specified=true", "contractYears.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByContractYearsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contractYears is greater than or equal to
        defaultPropertyFiltering(
            "contractYears.greaterThanOrEqual=" + DEFAULT_CONTRACT_YEARS,
            "contractYears.greaterThanOrEqual=" + UPDATED_CONTRACT_YEARS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByContractYearsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contractYears is less than or equal to
        defaultPropertyFiltering(
            "contractYears.lessThanOrEqual=" + DEFAULT_CONTRACT_YEARS,
            "contractYears.lessThanOrEqual=" + SMALLER_CONTRACT_YEARS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByContractYearsIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contractYears is less than
        defaultPropertyFiltering("contractYears.lessThan=" + UPDATED_CONTRACT_YEARS, "contractYears.lessThan=" + DEFAULT_CONTRACT_YEARS);
    }

    @Test
    @Transactional
    void getAllPropertiesByContractYearsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where contractYears is greater than
        defaultPropertyFiltering(
            "contractYears.greaterThan=" + SMALLER_CONTRACT_YEARS,
            "contractYears.greaterThan=" + DEFAULT_CONTRACT_YEARS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNextAvailableDateForRentIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where nextAvailableDateForRent equals to
        defaultPropertyFiltering(
            "nextAvailableDateForRent.equals=" + DEFAULT_NEXT_AVAILABLE_DATE_FOR_RENT,
            "nextAvailableDateForRent.equals=" + UPDATED_NEXT_AVAILABLE_DATE_FOR_RENT
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNextAvailableDateForRentIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where nextAvailableDateForRent in
        defaultPropertyFiltering(
            "nextAvailableDateForRent.in=" + DEFAULT_NEXT_AVAILABLE_DATE_FOR_RENT + "," + UPDATED_NEXT_AVAILABLE_DATE_FOR_RENT,
            "nextAvailableDateForRent.in=" + UPDATED_NEXT_AVAILABLE_DATE_FOR_RENT
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNextAvailableDateForRentIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where nextAvailableDateForRent is not null
        defaultPropertyFiltering("nextAvailableDateForRent.specified=true", "nextAvailableDateForRent.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByNextAvailableDateForRentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where nextAvailableDateForRent is greater than or equal to
        defaultPropertyFiltering(
            "nextAvailableDateForRent.greaterThanOrEqual=" + DEFAULT_NEXT_AVAILABLE_DATE_FOR_RENT,
            "nextAvailableDateForRent.greaterThanOrEqual=" + UPDATED_NEXT_AVAILABLE_DATE_FOR_RENT
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNextAvailableDateForRentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where nextAvailableDateForRent is less than or equal to
        defaultPropertyFiltering(
            "nextAvailableDateForRent.lessThanOrEqual=" + DEFAULT_NEXT_AVAILABLE_DATE_FOR_RENT,
            "nextAvailableDateForRent.lessThanOrEqual=" + SMALLER_NEXT_AVAILABLE_DATE_FOR_RENT
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNextAvailableDateForRentIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where nextAvailableDateForRent is less than
        defaultPropertyFiltering(
            "nextAvailableDateForRent.lessThan=" + UPDATED_NEXT_AVAILABLE_DATE_FOR_RENT,
            "nextAvailableDateForRent.lessThan=" + DEFAULT_NEXT_AVAILABLE_DATE_FOR_RENT
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNextAvailableDateForRentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where nextAvailableDateForRent is greater than
        defaultPropertyFiltering(
            "nextAvailableDateForRent.greaterThan=" + SMALLER_NEXT_AVAILABLE_DATE_FOR_RENT,
            "nextAvailableDateForRent.greaterThan=" + DEFAULT_NEXT_AVAILABLE_DATE_FOR_RENT
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByThumbnailIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where thumbnail equals to
        defaultPropertyFiltering("thumbnail.equals=" + DEFAULT_THUMBNAIL, "thumbnail.equals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllPropertiesByThumbnailIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where thumbnail in
        defaultPropertyFiltering("thumbnail.in=" + DEFAULT_THUMBNAIL + "," + UPDATED_THUMBNAIL, "thumbnail.in=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllPropertiesByThumbnailIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where thumbnail is not null
        defaultPropertyFiltering("thumbnail.specified=true", "thumbnail.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByThumbnailIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where thumbnail is greater than or equal to
        defaultPropertyFiltering("thumbnail.greaterThanOrEqual=" + DEFAULT_THUMBNAIL, "thumbnail.greaterThanOrEqual=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllPropertiesByThumbnailIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where thumbnail is less than or equal to
        defaultPropertyFiltering("thumbnail.lessThanOrEqual=" + DEFAULT_THUMBNAIL, "thumbnail.lessThanOrEqual=" + SMALLER_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllPropertiesByThumbnailIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where thumbnail is less than
        defaultPropertyFiltering("thumbnail.lessThan=" + UPDATED_THUMBNAIL, "thumbnail.lessThan=" + DEFAULT_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllPropertiesByThumbnailIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where thumbnail is greater than
        defaultPropertyFiltering("thumbnail.greaterThan=" + SMALLER_THUMBNAIL, "thumbnail.greaterThan=" + DEFAULT_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllPropertiesByHouseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where houseType equals to
        defaultPropertyFiltering("houseType.equals=" + DEFAULT_HOUSE_TYPE, "houseType.equals=" + UPDATED_HOUSE_TYPE);
    }

    @Test
    @Transactional
    void getAllPropertiesByHouseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where houseType in
        defaultPropertyFiltering("houseType.in=" + DEFAULT_HOUSE_TYPE + "," + UPDATED_HOUSE_TYPE, "houseType.in=" + UPDATED_HOUSE_TYPE);
    }

    @Test
    @Transactional
    void getAllPropertiesByHouseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where houseType is not null
        defaultPropertyFiltering("houseType.specified=true", "houseType.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByHouseTypeContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where houseType contains
        defaultPropertyFiltering("houseType.contains=" + DEFAULT_HOUSE_TYPE, "houseType.contains=" + UPDATED_HOUSE_TYPE);
    }

    @Test
    @Transactional
    void getAllPropertiesByHouseTypeNotContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where houseType does not contain
        defaultPropertyFiltering("houseType.doesNotContain=" + UPDATED_HOUSE_TYPE, "houseType.doesNotContain=" + DEFAULT_HOUSE_TYPE);
    }

    @Test
    @Transactional
    void getAllPropertiesByFloorIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where floor equals to
        defaultPropertyFiltering("floor.equals=" + DEFAULT_FLOOR, "floor.equals=" + UPDATED_FLOOR);
    }

    @Test
    @Transactional
    void getAllPropertiesByFloorIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where floor in
        defaultPropertyFiltering("floor.in=" + DEFAULT_FLOOR + "," + UPDATED_FLOOR, "floor.in=" + UPDATED_FLOOR);
    }

    @Test
    @Transactional
    void getAllPropertiesByFloorIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where floor is not null
        defaultPropertyFiltering("floor.specified=true", "floor.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByFloorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where floor is greater than or equal to
        defaultPropertyFiltering("floor.greaterThanOrEqual=" + DEFAULT_FLOOR, "floor.greaterThanOrEqual=" + UPDATED_FLOOR);
    }

    @Test
    @Transactional
    void getAllPropertiesByFloorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where floor is less than or equal to
        defaultPropertyFiltering("floor.lessThanOrEqual=" + DEFAULT_FLOOR, "floor.lessThanOrEqual=" + SMALLER_FLOOR);
    }

    @Test
    @Transactional
    void getAllPropertiesByFloorIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where floor is less than
        defaultPropertyFiltering("floor.lessThan=" + UPDATED_FLOOR, "floor.lessThan=" + DEFAULT_FLOOR);
    }

    @Test
    @Transactional
    void getAllPropertiesByFloorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where floor is greater than
        defaultPropertyFiltering("floor.greaterThan=" + SMALLER_FLOOR, "floor.greaterThan=" + DEFAULT_FLOOR);
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfFlatsIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfFlats equals to
        defaultPropertyFiltering("numberOfFlats.equals=" + DEFAULT_NUMBER_OF_FLATS, "numberOfFlats.equals=" + UPDATED_NUMBER_OF_FLATS);
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfFlatsIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfFlats in
        defaultPropertyFiltering(
            "numberOfFlats.in=" + DEFAULT_NUMBER_OF_FLATS + "," + UPDATED_NUMBER_OF_FLATS,
            "numberOfFlats.in=" + UPDATED_NUMBER_OF_FLATS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfFlatsIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfFlats is not null
        defaultPropertyFiltering("numberOfFlats.specified=true", "numberOfFlats.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfFlatsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfFlats is greater than or equal to
        defaultPropertyFiltering(
            "numberOfFlats.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_FLATS,
            "numberOfFlats.greaterThanOrEqual=" + UPDATED_NUMBER_OF_FLATS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfFlatsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfFlats is less than or equal to
        defaultPropertyFiltering(
            "numberOfFlats.lessThanOrEqual=" + DEFAULT_NUMBER_OF_FLATS,
            "numberOfFlats.lessThanOrEqual=" + SMALLER_NUMBER_OF_FLATS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfFlatsIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfFlats is less than
        defaultPropertyFiltering("numberOfFlats.lessThan=" + UPDATED_NUMBER_OF_FLATS, "numberOfFlats.lessThan=" + DEFAULT_NUMBER_OF_FLATS);
    }

    @Test
    @Transactional
    void getAllPropertiesByNumberOfFlatsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where numberOfFlats is greater than
        defaultPropertyFiltering(
            "numberOfFlats.greaterThan=" + SMALLER_NUMBER_OF_FLATS,
            "numberOfFlats.greaterThan=" + DEFAULT_NUMBER_OF_FLATS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByEnergyClassIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where energyClass equals to
        defaultPropertyFiltering("energyClass.equals=" + DEFAULT_ENERGY_CLASS, "energyClass.equals=" + UPDATED_ENERGY_CLASS);
    }

    @Test
    @Transactional
    void getAllPropertiesByEnergyClassIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where energyClass in
        defaultPropertyFiltering(
            "energyClass.in=" + DEFAULT_ENERGY_CLASS + "," + UPDATED_ENERGY_CLASS,
            "energyClass.in=" + UPDATED_ENERGY_CLASS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByEnergyClassIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where energyClass is not null
        defaultPropertyFiltering("energyClass.specified=true", "energyClass.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByEnergyClassContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where energyClass contains
        defaultPropertyFiltering("energyClass.contains=" + DEFAULT_ENERGY_CLASS, "energyClass.contains=" + UPDATED_ENERGY_CLASS);
    }

    @Test
    @Transactional
    void getAllPropertiesByEnergyClassNotContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where energyClass does not contain
        defaultPropertyFiltering(
            "energyClass.doesNotContain=" + UPDATED_ENERGY_CLASS,
            "energyClass.doesNotContain=" + DEFAULT_ENERGY_CLASS
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByConstructionIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where construction equals to
        defaultPropertyFiltering("construction.equals=" + DEFAULT_CONSTRUCTION, "construction.equals=" + UPDATED_CONSTRUCTION);
    }

    @Test
    @Transactional
    void getAllPropertiesByConstructionIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where construction in
        defaultPropertyFiltering(
            "construction.in=" + DEFAULT_CONSTRUCTION + "," + UPDATED_CONSTRUCTION,
            "construction.in=" + UPDATED_CONSTRUCTION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByConstructionIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where construction is not null
        defaultPropertyFiltering("construction.specified=true", "construction.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfManufactureIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfManufacture equals to
        defaultPropertyFiltering(
            "yearOfManufacture.equals=" + DEFAULT_YEAR_OF_MANUFACTURE,
            "yearOfManufacture.equals=" + UPDATED_YEAR_OF_MANUFACTURE
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfManufactureIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfManufacture in
        defaultPropertyFiltering(
            "yearOfManufacture.in=" + DEFAULT_YEAR_OF_MANUFACTURE + "," + UPDATED_YEAR_OF_MANUFACTURE,
            "yearOfManufacture.in=" + UPDATED_YEAR_OF_MANUFACTURE
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfManufactureIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfManufacture is not null
        defaultPropertyFiltering("yearOfManufacture.specified=true", "yearOfManufacture.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfManufactureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfManufacture is greater than or equal to
        defaultPropertyFiltering(
            "yearOfManufacture.greaterThanOrEqual=" + DEFAULT_YEAR_OF_MANUFACTURE,
            "yearOfManufacture.greaterThanOrEqual=" + UPDATED_YEAR_OF_MANUFACTURE
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfManufactureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfManufacture is less than or equal to
        defaultPropertyFiltering(
            "yearOfManufacture.lessThanOrEqual=" + DEFAULT_YEAR_OF_MANUFACTURE,
            "yearOfManufacture.lessThanOrEqual=" + SMALLER_YEAR_OF_MANUFACTURE
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfManufactureIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfManufacture is less than
        defaultPropertyFiltering(
            "yearOfManufacture.lessThan=" + UPDATED_YEAR_OF_MANUFACTURE,
            "yearOfManufacture.lessThan=" + DEFAULT_YEAR_OF_MANUFACTURE
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfManufactureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfManufacture is greater than
        defaultPropertyFiltering(
            "yearOfManufacture.greaterThan=" + SMALLER_YEAR_OF_MANUFACTURE,
            "yearOfManufacture.greaterThan=" + DEFAULT_YEAR_OF_MANUFACTURE
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfRenovationIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfRenovation equals to
        defaultPropertyFiltering(
            "yearOfRenovation.equals=" + DEFAULT_YEAR_OF_RENOVATION,
            "yearOfRenovation.equals=" + UPDATED_YEAR_OF_RENOVATION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfRenovationIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfRenovation in
        defaultPropertyFiltering(
            "yearOfRenovation.in=" + DEFAULT_YEAR_OF_RENOVATION + "," + UPDATED_YEAR_OF_RENOVATION,
            "yearOfRenovation.in=" + UPDATED_YEAR_OF_RENOVATION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfRenovationIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfRenovation is not null
        defaultPropertyFiltering("yearOfRenovation.specified=true", "yearOfRenovation.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfRenovationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfRenovation is greater than or equal to
        defaultPropertyFiltering(
            "yearOfRenovation.greaterThanOrEqual=" + DEFAULT_YEAR_OF_RENOVATION,
            "yearOfRenovation.greaterThanOrEqual=" + UPDATED_YEAR_OF_RENOVATION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfRenovationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfRenovation is less than or equal to
        defaultPropertyFiltering(
            "yearOfRenovation.lessThanOrEqual=" + DEFAULT_YEAR_OF_RENOVATION,
            "yearOfRenovation.lessThanOrEqual=" + SMALLER_YEAR_OF_RENOVATION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfRenovationIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfRenovation is less than
        defaultPropertyFiltering(
            "yearOfRenovation.lessThan=" + UPDATED_YEAR_OF_RENOVATION,
            "yearOfRenovation.lessThan=" + DEFAULT_YEAR_OF_RENOVATION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByYearOfRenovationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearOfRenovation is greater than
        defaultPropertyFiltering(
            "yearOfRenovation.greaterThan=" + SMALLER_YEAR_OF_RENOVATION,
            "yearOfRenovation.greaterThan=" + DEFAULT_YEAR_OF_RENOVATION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByPropertyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where propertyCode equals to
        defaultPropertyFiltering("propertyCode.equals=" + DEFAULT_PROPERTY_CODE, "propertyCode.equals=" + UPDATED_PROPERTY_CODE);
    }

    @Test
    @Transactional
    void getAllPropertiesByPropertyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where propertyCode in
        defaultPropertyFiltering(
            "propertyCode.in=" + DEFAULT_PROPERTY_CODE + "," + UPDATED_PROPERTY_CODE,
            "propertyCode.in=" + UPDATED_PROPERTY_CODE
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByPropertyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where propertyCode is not null
        defaultPropertyFiltering("propertyCode.specified=true", "propertyCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByPropertyCodeContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where propertyCode contains
        defaultPropertyFiltering("propertyCode.contains=" + DEFAULT_PROPERTY_CODE, "propertyCode.contains=" + UPDATED_PROPERTY_CODE);
    }

    @Test
    @Transactional
    void getAllPropertiesByPropertyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where propertyCode does not contain
        defaultPropertyFiltering(
            "propertyCode.doesNotContain=" + UPDATED_PROPERTY_CODE,
            "propertyCode.doesNotContain=" + DEFAULT_PROPERTY_CODE
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByFurnituredIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where furnitured equals to
        defaultPropertyFiltering("furnitured.equals=" + DEFAULT_FURNITURED, "furnitured.equals=" + UPDATED_FURNITURED);
    }

    @Test
    @Transactional
    void getAllPropertiesByFurnituredIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where furnitured in
        defaultPropertyFiltering("furnitured.in=" + DEFAULT_FURNITURED + "," + UPDATED_FURNITURED, "furnitured.in=" + UPDATED_FURNITURED);
    }

    @Test
    @Transactional
    void getAllPropertiesByFurnituredIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where furnitured is not null
        defaultPropertyFiltering("furnitured.specified=true", "furnitured.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByFurnituredDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where furnituredDescription equals to
        defaultPropertyFiltering(
            "furnituredDescription.equals=" + DEFAULT_FURNITURED_DESCRIPTION,
            "furnituredDescription.equals=" + UPDATED_FURNITURED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByFurnituredDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where furnituredDescription in
        defaultPropertyFiltering(
            "furnituredDescription.in=" + DEFAULT_FURNITURED_DESCRIPTION + "," + UPDATED_FURNITURED_DESCRIPTION,
            "furnituredDescription.in=" + UPDATED_FURNITURED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByFurnituredDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where furnituredDescription is not null
        defaultPropertyFiltering("furnituredDescription.specified=true", "furnituredDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByFurnituredDescriptionContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where furnituredDescription contains
        defaultPropertyFiltering(
            "furnituredDescription.contains=" + DEFAULT_FURNITURED_DESCRIPTION,
            "furnituredDescription.contains=" + UPDATED_FURNITURED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByFurnituredDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where furnituredDescription does not contain
        defaultPropertyFiltering(
            "furnituredDescription.doesNotContain=" + UPDATED_FURNITURED_DESCRIPTION,
            "furnituredDescription.doesNotContain=" + DEFAULT_FURNITURED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllPropertiesByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where deleted equals to
        defaultPropertyFiltering("deleted.equals=" + DEFAULT_DELETED, "deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllPropertiesByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where deleted in
        defaultPropertyFiltering("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED, "deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllPropertiesByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where deleted is not null
        defaultPropertyFiltering("deleted.specified=true", "deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllPropertiesByLocationIsEqualToSomething() throws Exception {
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            propertyRepository.saveAndFlush(property);
            location = LocationResourceIT.createEntity(em);
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        em.persist(location);
        em.flush();
        property.setLocation(location);
        propertyRepository.saveAndFlush(property);
        Long locationId = location.getId();
        // Get all the propertyList where location equals to locationId
        defaultPropertyShouldBeFound("locationId.equals=" + locationId);

        // Get all the propertyList where location equals to (locationId + 1)
        defaultPropertyShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }

    @Test
    @Transactional
    void getAllPropertiesByLandLordIsEqualToSomething() throws Exception {
        LandLord landLord;
        if (TestUtil.findAll(em, LandLord.class).isEmpty()) {
            propertyRepository.saveAndFlush(property);
            landLord = LandLordResourceIT.createEntity(em);
        } else {
            landLord = TestUtil.findAll(em, LandLord.class).get(0);
        }
        em.persist(landLord);
        em.flush();
        property.setLandLord(landLord);
        propertyRepository.saveAndFlush(property);
        Long landLordId = landLord.getId();
        // Get all the propertyList where landLord equals to landLordId
        defaultPropertyShouldBeFound("landLordId.equals=" + landLordId);

        // Get all the propertyList where landLord equals to (landLordId + 1)
        defaultPropertyShouldNotBeFound("landLordId.equals=" + (landLordId + 1));
    }

    private void defaultPropertyFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPropertyShouldBeFound(shouldBeFound);
        defaultPropertyShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPropertyShouldBeFound(String filter) throws Exception {
        restPropertyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].squareMeters").value(hasItem(sameNumber(DEFAULT_SQUARE_METERS))))
            .andExpect(jsonPath("$.[*].plotSquareMeters").value(hasItem(sameNumber(DEFAULT_PLOT_SQUARE_METERS))))
            .andExpect(jsonPath("$.[*].numberOfBathrooms").value(hasItem(DEFAULT_NUMBER_OF_BATHROOMS)))
            .andExpect(jsonPath("$.[*].numberOfBedrooms").value(hasItem(DEFAULT_NUMBER_OF_BEDROOMS)))
            .andExpect(jsonPath("$.[*].numberOfKitchens").value(hasItem(DEFAULT_NUMBER_OF_KITCHENS)))
            .andExpect(jsonPath("$.[*].numberOfAirConditioner").value(hasItem(DEFAULT_NUMBER_OF_AIR_CONDITIONER)))
            .andExpect(jsonPath("$.[*].houseRules").value(hasItem(DEFAULT_HOUSE_RULES.toString())))
            .andExpect(jsonPath("$.[*].contractYears").value(hasItem(DEFAULT_CONTRACT_YEARS)))
            .andExpect(jsonPath("$.[*].nextAvailableDateForRent").value(hasItem(DEFAULT_NEXT_AVAILABLE_DATE_FOR_RENT.toString())))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL.intValue())))
            .andExpect(jsonPath("$.[*].houseType").value(hasItem(DEFAULT_HOUSE_TYPE)))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].numberOfFlats").value(hasItem(DEFAULT_NUMBER_OF_FLATS)))
            .andExpect(jsonPath("$.[*].energyClass").value(hasItem(DEFAULT_ENERGY_CLASS)))
            .andExpect(jsonPath("$.[*].construction").value(hasItem(DEFAULT_CONSTRUCTION.toString())))
            .andExpect(jsonPath("$.[*].yearOfManufacture").value(hasItem(DEFAULT_YEAR_OF_MANUFACTURE)))
            .andExpect(jsonPath("$.[*].yearOfRenovation").value(hasItem(DEFAULT_YEAR_OF_RENOVATION)))
            .andExpect(jsonPath("$.[*].propertyCode").value(hasItem(DEFAULT_PROPERTY_CODE)))
            .andExpect(jsonPath("$.[*].furnitured").value(hasItem(DEFAULT_FURNITURED.booleanValue())))
            .andExpect(jsonPath("$.[*].furnituredDescription").value(hasItem(DEFAULT_FURNITURED_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restPropertyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPropertyShouldNotBeFound(String filter) throws Exception {
        restPropertyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPropertyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the property
        Property updatedProperty = propertyRepository.findById(property.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProperty are not directly saved in db
        em.detach(updatedProperty);
        updatedProperty
            .verified(UPDATED_VERIFIED)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .squareMeters(UPDATED_SQUARE_METERS)
            .plotSquareMeters(UPDATED_PLOT_SQUARE_METERS)
            .numberOfBathrooms(UPDATED_NUMBER_OF_BATHROOMS)
            .numberOfBedrooms(UPDATED_NUMBER_OF_BEDROOMS)
            .numberOfKitchens(UPDATED_NUMBER_OF_KITCHENS)
            .numberOfAirConditioner(UPDATED_NUMBER_OF_AIR_CONDITIONER)
            .houseRules(UPDATED_HOUSE_RULES)
            .contractYears(UPDATED_CONTRACT_YEARS)
            .nextAvailableDateForRent(UPDATED_NEXT_AVAILABLE_DATE_FOR_RENT)
            .thumbnail(UPDATED_THUMBNAIL)
            .houseType(UPDATED_HOUSE_TYPE)
            .floor(UPDATED_FLOOR)
            .numberOfFlats(UPDATED_NUMBER_OF_FLATS)
            .energyClass(UPDATED_ENERGY_CLASS)
            .construction(UPDATED_CONSTRUCTION)
            .yearOfManufacture(UPDATED_YEAR_OF_MANUFACTURE)
            .yearOfRenovation(UPDATED_YEAR_OF_RENOVATION)
            .propertyCode(UPDATED_PROPERTY_CODE)
            .furnitured(UPDATED_FURNITURED)
            .furnituredDescription(UPDATED_FURNITURED_DESCRIPTION)
            .deleted(UPDATED_DELETED);
        PropertyDTO propertyDTO = propertyMapper.toDto(updatedProperty);

        restPropertyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, propertyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(propertyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPropertyToMatchAllProperties(updatedProperty);
    }

    @Test
    @Transactional
    void putNonExistingProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, propertyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(propertyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(propertyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePropertyWithPatch() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the property using partial update
        Property partialUpdatedProperty = new Property();
        partialUpdatedProperty.setId(property.getId());

        partialUpdatedProperty
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .squareMeters(UPDATED_SQUARE_METERS)
            .numberOfBathrooms(UPDATED_NUMBER_OF_BATHROOMS)
            .numberOfBedrooms(UPDATED_NUMBER_OF_BEDROOMS)
            .numberOfKitchens(UPDATED_NUMBER_OF_KITCHENS)
            .nextAvailableDateForRent(UPDATED_NEXT_AVAILABLE_DATE_FOR_RENT)
            .thumbnail(UPDATED_THUMBNAIL)
            .floor(UPDATED_FLOOR)
            .numberOfFlats(UPDATED_NUMBER_OF_FLATS)
            .yearOfRenovation(UPDATED_YEAR_OF_RENOVATION)
            .propertyCode(UPDATED_PROPERTY_CODE)
            .furnitured(UPDATED_FURNITURED);

        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProperty.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProperty))
            )
            .andExpect(status().isOk());

        // Validate the Property in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPropertyUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProperty, property), getPersistedProperty(property));
    }

    @Test
    @Transactional
    void fullUpdatePropertyWithPatch() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the property using partial update
        Property partialUpdatedProperty = new Property();
        partialUpdatedProperty.setId(property.getId());

        partialUpdatedProperty
            .verified(UPDATED_VERIFIED)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .squareMeters(UPDATED_SQUARE_METERS)
            .plotSquareMeters(UPDATED_PLOT_SQUARE_METERS)
            .numberOfBathrooms(UPDATED_NUMBER_OF_BATHROOMS)
            .numberOfBedrooms(UPDATED_NUMBER_OF_BEDROOMS)
            .numberOfKitchens(UPDATED_NUMBER_OF_KITCHENS)
            .numberOfAirConditioner(UPDATED_NUMBER_OF_AIR_CONDITIONER)
            .houseRules(UPDATED_HOUSE_RULES)
            .contractYears(UPDATED_CONTRACT_YEARS)
            .nextAvailableDateForRent(UPDATED_NEXT_AVAILABLE_DATE_FOR_RENT)
            .thumbnail(UPDATED_THUMBNAIL)
            .houseType(UPDATED_HOUSE_TYPE)
            .floor(UPDATED_FLOOR)
            .numberOfFlats(UPDATED_NUMBER_OF_FLATS)
            .energyClass(UPDATED_ENERGY_CLASS)
            .construction(UPDATED_CONSTRUCTION)
            .yearOfManufacture(UPDATED_YEAR_OF_MANUFACTURE)
            .yearOfRenovation(UPDATED_YEAR_OF_RENOVATION)
            .propertyCode(UPDATED_PROPERTY_CODE)
            .furnitured(UPDATED_FURNITURED)
            .furnituredDescription(UPDATED_FURNITURED_DESCRIPTION)
            .deleted(UPDATED_DELETED);

        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProperty.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProperty))
            )
            .andExpect(status().isOk());

        // Validate the Property in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPropertyUpdatableFieldsEquals(partialUpdatedProperty, getPersistedProperty(partialUpdatedProperty));
    }

    @Test
    @Transactional
    void patchNonExistingProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, propertyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(propertyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(propertyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the property
        restPropertyMockMvc
            .perform(delete(ENTITY_API_URL_ID, property.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return propertyRepository.count();
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

    protected Property getPersistedProperty(Property property) {
        return propertyRepository.findById(property.getId()).orElseThrow();
    }

    protected void assertPersistedPropertyToMatchAllProperties(Property expectedProperty) {
        assertPropertyAllPropertiesEquals(expectedProperty, getPersistedProperty(expectedProperty));
    }

    protected void assertPersistedPropertyToMatchUpdatableProperties(Property expectedProperty) {
        assertPropertyAllUpdatablePropertiesEquals(expectedProperty, getPersistedProperty(expectedProperty));
    }
}

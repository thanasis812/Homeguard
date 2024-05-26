package com.hg.service;

import com.hg.domain.*; // for static metamodels
import com.hg.domain.Property;
import com.hg.repository.PropertyRepository;
import com.hg.service.criteria.PropertyCriteria;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.mydto.PropertyDossierDTO;
import com.hg.service.mapper.PropertyMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Property} entities in the database.
 * The main input is a {@link PropertyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PropertyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PropertyQueryService extends QueryService<Property> {

    private final Logger log = LoggerFactory.getLogger(PropertyQueryService.class);

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    public PropertyQueryService(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    /**
     * Return a {@link Page} of {@link PropertyDossierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PropertyDossierDTO> findByCriteria(PropertyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Property> specification = createSpecification(criteria);
        return propertyRepository.findAll(specification, page).map(propertyMapper::toUiDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PropertyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Property> specification = createSpecification(criteria);
        return propertyRepository.count(specification);
    }

    /**
     * Function to convert {@link PropertyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Property> createSpecification(PropertyCriteria criteria) {
        Specification<Property> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getVerified(), Property_.verified));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Property_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Property_.description));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Property_.price));
            }
            if (criteria.getSquareMeters() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSquareMeters(), Property_.squareMeters));
            }
            if (criteria.getPlotSquareMeters() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlotSquareMeters(), Property_.plotSquareMeters));
            }
            if (criteria.getNumberOfBathrooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfBathrooms(), Property_.numberOfBathrooms));
            }
            if (criteria.getNumberOfBedrooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfBedrooms(), Property_.numberOfBedrooms));
            }
            if (criteria.getNumberOfKitchens() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfKitchens(), Property_.numberOfKitchens));
            }
            if (criteria.getNumberOfAirConditioner() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getNumberOfAirConditioner(), Property_.numberOfAirConditioner)
                );
            }
            if (criteria.getContractYears() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractYears(), Property_.contractYears));
            }
            if (criteria.getNextAvailableDateForRent() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getNextAvailableDateForRent(), Property_.nextAvailableDateForRent)
                );
            }
            if (criteria.getHouseType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHouseType(), Property_.houseType));
            }
            if (criteria.getFloor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloor(), Property_.floor));
            }
            if (criteria.getNumberOfFlats() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfFlats(), Property_.numberOfFlats));
            }
            if (criteria.getEnergyClass() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnergyClass(), Property_.energyClass));
            }
            if (criteria.getConstruction() != null) {
                specification = specification.and(buildSpecification(criteria.getConstruction(), Property_.construction));
            }
            if (criteria.getYearOfManufacture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearOfManufacture(), Property_.yearOfManufacture));
            }
            if (criteria.getYearOfRenovation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearOfRenovation(), Property_.yearOfRenovation));
            }
            if (criteria.getPropertyCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPropertyCode(), Property_.propertyCode));
            }
            if (criteria.getFurnitured() != null) {
                specification = specification.and(buildSpecification(criteria.getFurnitured(), Property_.furnitured));
            }
            if (criteria.getFurnituredDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getFurnituredDescription(), Property_.furnituredDescription)
                );
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), Property_.deleted));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getLocationId(), root -> root.join(Property_.location, JoinType.LEFT).get(Location_.id))
                );
            }
            //            if (criteria.getHouseCharacteristicId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getHouseCharacteristicId(),
            //                        root -> root.join(Property_.houseCharacteristics, JoinType.LEFT).get(HouseCharacteristics_.id)
            //                    )
            //                );
            //            }
            //            if (criteria.getReviewsId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(criteria.getReviewsId(), root -> root.join(Property_.reviews, JoinType.LEFT).get(Review_.id))
            //                );
            //            }
            //            if (criteria.getPropertysPhotoId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getPropertysPhotoId(),
            //                        root -> root.join(Property_.propertysPhotos, JoinType.LEFT).get(Image_.id)
            //                    )
            //                );
            //            }
            //            if (criteria.getTenantPropertyPreferencesId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getTenantPropertyPreferencesId(),
            //                        root -> root.join(Property_.tenantPropertyPreferences, JoinType.LEFT).get(TenantPropertyPreferences_.id)
            //                    )
            //                );
            //            }
        }
        return specification;
    }
}

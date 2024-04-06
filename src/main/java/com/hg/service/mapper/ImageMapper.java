package com.hg.service.mapper;

import com.hg.domain.Image;
import com.hg.domain.Property;
import com.hg.domain.Review;
import com.hg.service.dto.ImageDTO;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.ReviewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Mapper(componentModel = "spring")
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    @Mapping(target = "review", source = "review", qualifiedByName = "reviewId")
    ImageDTO toDto(Image s);

    @Named("propertyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PropertyDTO toDtoPropertyId(Property property);

    @Named("reviewId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReviewDTO toDtoReviewId(Review review);
}

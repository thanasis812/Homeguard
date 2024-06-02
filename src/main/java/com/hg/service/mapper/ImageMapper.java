package com.hg.service.mapper;

import com.hg.domain.HouseCharacteristics;
import com.hg.domain.Image;
import com.hg.domain.Property;
import com.hg.domain.Review;
import com.hg.service.dto.HouseCharacteristicsDTO;
import com.hg.service.dto.ImageDTO;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.ReviewDTO;
import com.hg.service.dto.mydto.UploadImageDTO;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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

    /**
     * Convert a list of Image object of the db to a list of just images
     * @param images list of image of db
     * @return list of numbers
     */
    @Named("toByteList")
    default Set<byte[]> toByteList(Set<Image> images) {
        return Objects.nonNull(images) && !images.isEmpty()
            ? images.stream().map(Image::getImageFile).collect(Collectors.toSet())
            : Collections.emptySet();
    }

    @Named("reviewId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReviewDTO toDtoReviewId(Review review);

    @Mapping(target = "objectURL", source = "path")
    @Mapping(target = "lastModified", source = "updatedDate")
    @Mapping(target = "type", source = "imageFileContentType")
    UploadImageDTO toSharedDto(Image image);
}

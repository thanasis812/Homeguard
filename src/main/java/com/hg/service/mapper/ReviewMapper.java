package com.hg.service.mapper;

import com.hg.domain.*;
import com.hg.service.dto.LandLordDTO;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.ReviewDTO;
import com.hg.service.dto.TenantDTO;
import com.hg.service.dto.mydto.UserReviewDTO;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Review} and its DTO {@link ReviewDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantId")
    @Mapping(target = "landLord", source = "landLord", qualifiedByName = "landLordId")
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    ReviewDTO toDto(Review s);

    @Named("tenantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TenantDTO toDtoTenantId(Tenant tenant);

    @Named("landLordId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LandLordDTO toDtoLandLordId(LandLord landLord);

    @Named("propertyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PropertyDTO toDtoPropertyId(Property property);

    List<UserReviewDTO> toUserDTOList(List<Review> review);

    @Mapping(target = "reviewDate", source = "review", qualifiedByName = "getLatestReviewDate")
    @Mapping(target = "userId", source = "review", qualifiedByName = "getReviewId")
    @Mapping(target = "userImage", source = "review", qualifiedByName = "getUserImage")
    @Mapping(target = "images", source = "review", qualifiedByName = "getReviewImages")
    @Mapping(target = "name", source = "review", qualifiedByName = "getUserName")
    UserReviewDTO toUserDto(Review review);

    @Named("getLatestReviewDate")
    default LocalDate getLatestReviewDate(Review review) {
        return review.getUpdatedDate() == null ? review.getCreatedDate() : review.getUpdatedDate();
    }

    @Named("getUserName")
    default String getUserName(Review review) {
        return Objects.nonNull(review.getTenant()) ? review.getTenant().getFirstName() : null;
    }

    @Named("getReviewId")
    default Long getReviewId(Review review) {
        return Objects.nonNull(review.getTenant()) ? review.getTenant().getId() : null;
    }

    @Named("getUserImage")
    default byte[] getUserImage(Review review) {
        return Objects.nonNull(review.getTenant()) && Objects.nonNull(review.getTenant().getTenantImage())
            ? review.getTenant().getTenantImage().getImageFile()
            : null;
    }

    @Named("getReviewImages")
    default Set<byte[]> getReviewImages(Review review) {
        return Objects.nonNull(review.getImages()) && !review.getImages().isEmpty()
            ? review.getImages().stream().map(Image::getImageFile).collect(Collectors.toSet())
            : Collections.emptySet();
    }
}

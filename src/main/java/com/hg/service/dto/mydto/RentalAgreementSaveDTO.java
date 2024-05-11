package com.hg.service.dto.mydto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hg.domain.RentalAgreement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public record RentalAgreementSaveDTO(Long propertyId, Long landLordId) implements Serializable {}

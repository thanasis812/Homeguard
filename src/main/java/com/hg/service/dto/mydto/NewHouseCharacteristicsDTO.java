package com.hg.service.dto.mydto;

import com.hg.domain.enumeration.HouseCharacteristicsEnum;
import com.hg.domain.enumeration.HouseCharacteristicsGroupEnum;
import com.hg.service.dto.PropertyDTO;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.HouseCharacteristics} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NewHouseCharacteristicsDTO implements Serializable {

    List<Objects> internal;
    List<Objects> external;
    List<Objects> construction;
    List<Objects> suitableFor;
    List<Objects> parkingAndAmeinities;
    List<Objects> view;
}

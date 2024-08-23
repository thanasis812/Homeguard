package com.hg.service.dto.mydto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "of")
public class AdminSalaryCertificateDTO {

    private Long id;
    private Long userId;
    private Long houseId;
    private List<byte[]> salarySlips;
    private List<byte[]> guarantorSalarySlips;
    private String userName;
    private boolean guarantorChecked;
}

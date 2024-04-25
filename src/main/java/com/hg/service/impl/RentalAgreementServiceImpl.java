package com.hg.service.impl;

import com.hg.domain.RentalAgreement;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import com.hg.repository.RentalAgreementRepository;
import com.hg.repository.TenantRepository;
import com.hg.service.RentalAgreementService;
import com.hg.service.dto.RentalAgreementDTO;
import com.hg.service.mapper.RentalAgreementMapper;
import com.hg.web.rest.errors.BaseException;
import com.hg.web.rest.errors.NotFoundException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.hg.domain.RentalAgreement}.
 */
@Service
@Transactional
public class RentalAgreementServiceImpl implements RentalAgreementService {

    private final Logger log = LoggerFactory.getLogger(RentalAgreementServiceImpl.class);

    private final RentalAgreementRepository rentalAgreementRepository;

    private final RentalAgreementMapper rentalAgreementMapper;
    private final TenantRepository tenantRepository;

    public RentalAgreementServiceImpl(
        RentalAgreementRepository rentalAgreementRepository,
        RentalAgreementMapper rentalAgreementMapper,
        TenantRepository tenantRepository
    ) {
        this.rentalAgreementRepository = rentalAgreementRepository;
        this.rentalAgreementMapper = rentalAgreementMapper;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public RentalAgreementDTO save(RentalAgreementDTO rentalAgreementDTO) {
        log.debug("Request to save RentalAgreement : {}", rentalAgreementDTO);
        RentalAgreement rentalAgreement = rentalAgreementMapper.toEntity(rentalAgreementDTO);
        rentalAgreement = rentalAgreementRepository.save(rentalAgreement);
        return rentalAgreementMapper.toDto(rentalAgreement);
    }

    @Override
    public RentalAgreementDTO update(RentalAgreementDTO rentalAgreementDTO) {
        log.debug("Request to update RentalAgreement : {}", rentalAgreementDTO);
        RentalAgreement rentalAgreement = rentalAgreementMapper.toEntity(rentalAgreementDTO);
        rentalAgreement = rentalAgreementRepository.save(rentalAgreement);
        return rentalAgreementMapper.toDto(rentalAgreement);
    }

    @Override
    public Optional<RentalAgreementDTO> partialUpdate(RentalAgreementDTO rentalAgreementDTO) {
        log.debug("Request to partially update RentalAgreement : {}", rentalAgreementDTO);

        return rentalAgreementRepository
            .findById(rentalAgreementDTO.getId())
            .map(existingRentalAgreement -> {
                rentalAgreementMapper.partialUpdate(existingRentalAgreement, rentalAgreementDTO);

                return existingRentalAgreement;
            })
            .map(rentalAgreementRepository::save)
            .map(rentalAgreementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RentalAgreementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RentalAgreements");
        return rentalAgreementRepository.findAll(pageable).map(rentalAgreementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RentalAgreementDTO> findOne(Long id) {
        log.debug("Request to get RentalAgreement : {}", id);
        return rentalAgreementRepository.findById(id).map(rentalAgreementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RentalAgreement : {}", id);
        rentalAgreementRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RentalAgreementDTO> findLatestActiveByTenant(Long tenantId) {
        log.debug("Request to get latest RentalAgreement for tenant id : {}", tenantId);
        return rentalAgreementRepository
            .findByStatusAndTenant(
                RentalAgreementStatusEnum.ACTIVE,
                tenantRepository
                    .findById(tenantId)
                    .orElseThrow(() -> new NotFoundException(String.format("Cant find Tenant with Tenant id : %d", tenantId)))
            )
            .map(rentalAgreementMapper::toDto);
    }
}

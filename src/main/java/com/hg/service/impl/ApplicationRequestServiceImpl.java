package com.hg.service.impl;

import com.hg.domain.ApplicationRequest;
import com.hg.repository.ApplicationRequestRepository;
import com.hg.service.ApplicationRequestService;
import com.hg.service.dto.ApplicationRequestDTO;
import com.hg.service.mapper.ApplicationRequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.hg.domain.ApplicationRequest}.
 */
@Service
@Transactional
public class ApplicationRequestServiceImpl implements ApplicationRequestService {

    private final Logger log = LoggerFactory.getLogger(ApplicationRequestServiceImpl.class);

    private final ApplicationRequestRepository applicationRequestRepository;

    private final ApplicationRequestMapper applicationRequestMapper;

    public ApplicationRequestServiceImpl(
        ApplicationRequestRepository applicationRequestRepository,
        ApplicationRequestMapper applicationRequestMapper
    ) {
        this.applicationRequestRepository = applicationRequestRepository;
        this.applicationRequestMapper = applicationRequestMapper;
    }

    @Override
    public ApplicationRequestDTO save(ApplicationRequestDTO applicationRequestDTO) {
        log.debug("Request to save ApplicationRequest : {}", applicationRequestDTO);
        ApplicationRequest applicationRequest = applicationRequestMapper.toEntity(applicationRequestDTO);
        applicationRequest = applicationRequestRepository.save(applicationRequest);
        return applicationRequestMapper.toDto(applicationRequest);
    }

    @Override
    public ApplicationRequestDTO update(ApplicationRequestDTO applicationRequestDTO) {
        log.debug("Request to update ApplicationRequest : {}", applicationRequestDTO);
        ApplicationRequest applicationRequest = applicationRequestMapper.toEntity(applicationRequestDTO);
        applicationRequest = applicationRequestRepository.save(applicationRequest);
        return applicationRequestMapper.toDto(applicationRequest);
    }

    @Override
    public Optional<ApplicationRequestDTO> partialUpdate(ApplicationRequestDTO applicationRequestDTO) {
        log.debug("Request to partially update ApplicationRequest : {}", applicationRequestDTO);

        return applicationRequestRepository
            .findById(applicationRequestDTO.getId())
            .map(existingApplicationRequest -> {
                applicationRequestMapper.partialUpdate(existingApplicationRequest, applicationRequestDTO);

                return existingApplicationRequest;
            })
            .map(applicationRequestRepository::save)
            .map(applicationRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationRequests");
        return applicationRequestRepository.findAll(pageable).map(applicationRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationRequestDTO> findOne(Long id) {
        log.debug("Request to get ApplicationRequest : {}", id);
        return applicationRequestRepository.findById(id).map(applicationRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationRequest : {}", id);
        applicationRequestRepository.deleteById(id);
    }
}

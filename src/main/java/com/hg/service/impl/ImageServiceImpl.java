package com.hg.service.impl;

import com.hg.domain.Image;
import com.hg.repository.ImageRepository;
import com.hg.service.ImageService;
import com.hg.service.dto.ImageDTO;
import com.hg.service.mapper.ImageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.hg.domain.Image}.
 */
@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    public ImageServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public ImageDTO save(ImageDTO imageDTO) {
        log.debug("Request to save Image : {}", imageDTO);
        Image image = imageMapper.toEntity(imageDTO);
        image = imageRepository.save(image);
        return imageMapper.toDto(image);
    }

    @Override
    public ImageDTO update(ImageDTO imageDTO) {
        log.debug("Request to update Image : {}", imageDTO);
        Image image = imageMapper.toEntity(imageDTO);
        image = imageRepository.save(image);
        return imageMapper.toDto(image);
    }

    @Override
    public Optional<ImageDTO> partialUpdate(ImageDTO imageDTO) {
        log.debug("Request to partially update Image : {}", imageDTO);

        return imageRepository
            .findById(imageDTO.getId())
            .map(existingImage -> {
                imageMapper.partialUpdate(existingImage, imageDTO);

                return existingImage;
            })
            .map(imageRepository::save)
            .map(imageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        return imageRepository.findAll(pageable).map(imageMapper::toDto);
    }

    /**
     *  Get all the images where Tenant is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ImageDTO> findAllWhereTenantIsNull() {
        log.debug("Request to get all images where Tenant is null");
        return StreamSupport.stream(imageRepository.findAll().spliterator(), false)
            .filter(image -> image.getTenant() == null)
            .map(imageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the images where LandLord is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ImageDTO> findAllWhereLandLordIsNull() {
        log.debug("Request to get all images where LandLord is null");
        return StreamSupport.stream(imageRepository.findAll().spliterator(), false)
            .filter(image -> image.getLandLord() == null)
            .map(imageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImageDTO> findOne(Long id) {
        log.debug("Request to get Image : {}", id);
        return imageRepository.findById(id).map(imageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Image : {}", id);
        imageRepository.deleteById(id);
    }
}

package com.hg.web.rest;

import com.hg.repository.ImageRepository;
import com.hg.service.ImageService;
import com.hg.service.dto.ImageDTO;
import com.hg.service.dto.mydto.UploadImageDTO;
import com.hg.web.rest.errors.BaseException;
import io.swagger.v3.oas.annotations.Hidden;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hg.domain.Image}.
 */
@Hidden
@RestController
@RequestMapping("/api/images")
public class ImageResource {

    private final Logger log = LoggerFactory.getLogger(ImageResource.class);

    private static final String ENTITY_NAME = "image";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImageService imageService;

    private final ImageRepository imageRepository;

    public ImageResource(ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    /**
     * {@code POST  /images} : Create a new image.
     *
     * @param imageDTO the imageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imageDTO, or with status {@code 400 (Bad Request)} if the image has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) throws URISyntaxException {
        log.debug("REST request to save Image : {}", imageDTO);
        if (imageDTO.getId() != null) {
            throw new BaseException("A new image cannot already have an ID", ENTITY_NAME, "idexists");
        }
        imageDTO = imageService.save(imageDTO);
        return ResponseEntity.created(new URI("/api/images/" + imageDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, imageDTO.getId().toString()))
            .body(imageDTO);
    }

    /**
     * {@code PUT  /images/:id} : Updates an existing image.
     *
     * @param id the id of the imageDTO to save.
     * @param imageDTO the imageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imageDTO,
     * or with status {@code 400 (Bad Request)} if the imageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updateImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ImageDTO imageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Image : {}, {}", id, imageDTO);
        if (imageDTO.getId() == null) {
            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imageDTO.getId())) {
            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imageRepository.existsById(id)) {
            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        imageDTO = imageService.update(imageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imageDTO.getId().toString()))
            .body(imageDTO);
    }

    /**
     * {@code PATCH  /images/:id} : Partial updates given fields of an existing image, field will ignore if it is null
     *
     * @param id the id of the imageDTO to save.
     * @param imageDTO the imageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imageDTO,
     * or with status {@code 400 (Bad Request)} if the imageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the imageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the imageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImageDTO> partialUpdateImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ImageDTO imageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Image partially : {}, {}", id, imageDTO);
        if (imageDTO.getId() == null) {
            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imageDTO.getId())) {
            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imageRepository.existsById(id)) {
            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImageDTO> result = imageService.partialUpdate(imageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /images} : get all the images.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of images in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ImageDTO>> getAllImages(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("tenant-is-null".equals(filter)) {
            log.debug("REST request to get all Images where tenant is null");
            return new ResponseEntity<>(imageService.findAllWhereTenantIsNull(), HttpStatus.OK);
        }

        if ("landlord-is-null".equals(filter)) {
            log.debug("REST request to get all Images where landLord is null");
            return new ResponseEntity<>(imageService.findAllWhereLandLordIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Images");
        Page<ImageDTO> page = imageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /images/:id} : get the "id" image.
     *
     * @param id the id of the imageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImage(@PathVariable("id") Long id) {
        log.debug("REST request to get Image : {}", id);
        Optional<ImageDTO> imageDTO = imageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imageDTO);
    }

    /**
     * {@code GET  /images/:id} : get the "id" image.
     * shared/image
     * @param id the id of the imageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shared/{id}")
    public ResponseEntity<UploadImageDTO> getSharedImage(@PathVariable("id") Long id) {
        log.debug("REST request to get Image : {}", id);
        Optional<UploadImageDTO> imageDTO = imageService.findOneShared(id);
        return ResponseUtil.wrapOrNotFound(imageDTO);
    }

    /**
     * {@code DELETE  /images/:id} : delete the "id" image.
     *
     * @param id the id of the imageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id) {
        log.debug("REST request to delete Image : {}", id);
        imageService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.hg.web.rest;

import com.hg.service.ImageService;
import com.hg.service.dto.mydto.UploadImageDTO;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hg.domain.Image}.
 */
@Hidden
@RestController
@RequestMapping("/api/shared")
public class SharedResource {

    private final Logger log = LoggerFactory.getLogger(SharedResource.class);

    private final ImageService imageService;

    public SharedResource(ImageService imageService) {
        this.imageService = imageService;
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
     * {@code GET  /images/:id} : get the "id" image.
     * shared/image
     * @param id the id of the imageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imageDTO, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/shared/{id}")
    public ResponseEntity<UploadImageDTO> postSharedImages(@PathVariable("id") Long id) {
        log.debug("REST request to get Image : {}", id);
        Optional<UploadImageDTO> imageDTO = imageService.findOneShared(id);
        return ResponseUtil.wrapOrNotFound(imageDTO);
    }
}

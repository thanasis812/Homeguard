package com.hg.web.rest;

import com.hg.repository.ImageRepository;
import com.hg.service.ImageService;
import com.hg.service.dto.mydto.UploadImageDTO;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hg.domain.Image}.
 */
@Hidden
@RestController
@RequestMapping("/api/images")
public class ImageResource {

    private final Logger log = LoggerFactory.getLogger(ImageResource.class);

    private final ImageService imageService;

    public ImageResource(ImageService imageService) {
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
}

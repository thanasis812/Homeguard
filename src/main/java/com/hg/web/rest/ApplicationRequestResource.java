package com.hg.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.hg.domain.ApplicationRequest}.
 */
@RestController
@RequestMapping("/api/application-requests")
public class ApplicationRequestResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationRequestResource.class);
    //    private final ApplicationRequestService applicationRequestService;
    //
    //    private final ApplicationRequestRepository applicationRequestRepository;

}

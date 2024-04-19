package com.hg.web.rest.errors;

import java.net.URI;

public class NotFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(ErrorConstants.ENTITY_NOT_FOUND, "Entity Not Found", "userManagement", message);
    }
}

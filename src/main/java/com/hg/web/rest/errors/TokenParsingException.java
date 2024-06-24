package com.hg.web.rest.errors;

public class TokenParsingException extends BaseException {

    private static final long serialVersionUID = 1L;

    public TokenParsingException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage, entityName, errorKey);
    }
}

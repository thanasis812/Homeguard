package com.hg.web.rest;

import com.hg.web.rest.errors.TokenParsingException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HGTokenService {

    private final Logger log = LoggerFactory.getLogger(HGTokenService.class);

    private Map<String, Object> getClaims(HttpServletRequest request) {
        try {
            String token = extractToken(request);
            if (token == null) return null;

            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            return claimsSet.getClaims();
        } catch (ParseException e) {
            log.error("Error parsing JWT token", e);
            throw new TokenParsingException("Error parsing JWT token", "Token parsing", "tokenFailure");
        }
    }

    private static String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7);
    }

    public Long getTenantId(HttpServletRequest request) {
        Object tenantIdObj = getClaims(request).get(CustomClaimEnum.tenantId.name());
        if (tenantIdObj instanceof Long) {
            return (Long) tenantIdObj;
        }
        throw new TokenParsingException("Error parsing JWT token. missing claim", "Token parsing", "tokenFailure");
    }

    private enum CustomClaimEnum {
        tenantId,
    }
}

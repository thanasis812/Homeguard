package com.hg.service;

import java.util.Date;

public interface UserPrincipalService {
    Long getTenantId();

    Long getUserId();

    Date getTokenExpirationDate();
}

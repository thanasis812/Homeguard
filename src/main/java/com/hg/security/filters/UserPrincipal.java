package com.hg.security.filters;

import com.hg.domain.User;
import com.hg.service.UserPrincipalService;
import com.hg.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class UserPrincipal implements UserPrincipalService {

    private final UserService userService;
    private final HGTokenService tokenService;
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public void intiUserContextForUser(HttpServletRequest request) {
        var userId = tokenService.getUserId(request);
        var user = userService.findByIdOptional(userId).orElseThrow(() -> new RuntimeException("UserNot exit"));
        currentUser.set(user);
        log.info("Current User : {}", user);
    }

    public Long getTenantId() {
        return currentUser.get().getTenant().getId();
    }

    @Override
    public Long getUserId() {
        return currentUser.get().getId();
    }
}

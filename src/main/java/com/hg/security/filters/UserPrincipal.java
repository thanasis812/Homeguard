package com.hg.security.filters;

import com.hg.domain.User;
import com.hg.service.UserPrincipalService;
import com.hg.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class UserPrincipal implements UserPrincipalService {

    private final UserService userService;
    private final HGTokenService tokenService;
    private static final ThreadLocal<CurrentUser> currentUser = new ThreadLocal<>();

    //    @Cacheable(value = "userCache", key = "#request.getSession().getId()") todo add to cache
    public void intiUserContextForUser(HttpServletRequest request) {
        var userId = tokenService.getUserId(request);
        var user = userService.findByIdOptional(userId).orElseThrow(() -> new RuntimeException("UserNot exit"));
        var expDate = tokenService.getExpDate(request);

        currentUser.set(CurrentUser.builder().ofUser(user).ofExpDate(expDate).build());
        log.info("Current User : {}", user);
    }

    public Long getTenantId() {
        return currentUser.get().getUser().getTenant().getId();
    }

    @Override
    public Long getUserId() {
        return currentUser.get().getUser().getId();
    }

    @Override
    public Date getTokenExpirationDate() {
        return currentUser.get().getExpDate();
    }

    @Getter
    @Setter
    @Builder(setterPrefix = "of")
    private static class CurrentUser {

        private User user;
        private Date expDate;
    }
}

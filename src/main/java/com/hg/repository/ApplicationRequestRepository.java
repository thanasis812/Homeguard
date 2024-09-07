package com.hg.repository;

import com.hg.domain.ApplicationRequest;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApplicationRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {
    @Query(
        "select applicationRequest from ApplicationRequest applicationRequest where applicationRequest.user.login = ?#{authentication.name}"
    )
    List<ApplicationRequest> findByUserIsCurrentUser();
}

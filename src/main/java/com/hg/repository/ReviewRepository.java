package com.hg.repository;

import com.hg.domain.Review;
import com.hg.domain.Tenant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Review entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTenantAndDeletedOrderByCreatedDate(Tenant searchTenant, boolean deleted);
}

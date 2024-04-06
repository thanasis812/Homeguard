package com.hg.repository;

import com.hg.domain.TenantPropertyPreferences;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TenantPropertyPreferences entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantPropertyPreferencesRepository extends JpaRepository<TenantPropertyPreferences, Long> {}

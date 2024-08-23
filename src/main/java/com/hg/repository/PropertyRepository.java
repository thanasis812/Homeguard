package com.hg.repository;

import com.hg.domain.LandLord;
import com.hg.domain.Property;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Property entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    List<Property> findByLandLord(LandLord landlordId);

    @Query("SELECT tpp FROM Property tpp where tpp.landLord.user.id= :id")
    List<Property> findByUserId(Long id);
}

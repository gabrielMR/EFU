package com.umsa.efu.repository;

import com.umsa.efu.domain.Premio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Premio entity.
 */
@Repository
public interface PremioRepository extends JpaRepository<Premio, Long> {

    @Query(value = "select distinct premio from Premio premio left join fetch premio.nombres",
        countQuery = "select count(distinct premio) from Premio premio")
    Page<Premio> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct premio from Premio premio left join fetch premio.nombres")
    List<Premio> findAllWithEagerRelationships();

    @Query("select premio from Premio premio left join fetch premio.nombres where premio.id =:id")
    Optional<Premio> findOneWithEagerRelationships(@Param("id") Long id);

}

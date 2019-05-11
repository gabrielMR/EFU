package com.umsa.efu.repository;

import com.umsa.efu.domain.Fraternidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Fraternidad entity.
 */
@Repository
public interface FraternidadRepository extends JpaRepository<Fraternidad, Long> {

    @Query(value = "select distinct fraternidad from Fraternidad fraternidad left join fetch fraternidad.nombres",
        countQuery = "select count(distinct fraternidad) from Fraternidad fraternidad")
    Page<Fraternidad> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct fraternidad from Fraternidad fraternidad left join fetch fraternidad.nombres")
    List<Fraternidad> findAllWithEagerRelationships();

    @Query("select fraternidad from Fraternidad fraternidad left join fetch fraternidad.nombres where fraternidad.id =:id")
    Optional<Fraternidad> findOneWithEagerRelationships(@Param("id") Long id);

}

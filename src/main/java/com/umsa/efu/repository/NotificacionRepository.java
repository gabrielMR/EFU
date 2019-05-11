package com.umsa.efu.repository;

import com.umsa.efu.domain.Notificacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Notificacion entity.
 */
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    @Query(value = "select distinct notificacion from Notificacion notificacion left join fetch notificacion.nombres",
        countQuery = "select count(distinct notificacion) from Notificacion notificacion")
    Page<Notificacion> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct notificacion from Notificacion notificacion left join fetch notificacion.nombres")
    List<Notificacion> findAllWithEagerRelationships();

    @Query("select notificacion from Notificacion notificacion left join fetch notificacion.nombres where notificacion.id =:id")
    Optional<Notificacion> findOneWithEagerRelationships(@Param("id") Long id);

}

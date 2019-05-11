package com.umsa.efu.repository;

import com.umsa.efu.domain.Comunicado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Comunicado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComunicadoRepository extends JpaRepository<Comunicado, Long> {

}

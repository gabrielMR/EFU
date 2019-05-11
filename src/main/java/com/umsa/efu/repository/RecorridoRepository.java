package com.umsa.efu.repository;

import com.umsa.efu.domain.Recorrido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Recorrido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecorridoRepository extends JpaRepository<Recorrido, Long> {

}

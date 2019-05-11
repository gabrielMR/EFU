package com.umsa.efu.repository;

import com.umsa.efu.domain.Delegado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Delegado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DelegadoRepository extends JpaRepository<Delegado, Long> {

}

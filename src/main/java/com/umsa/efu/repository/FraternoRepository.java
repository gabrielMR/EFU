package com.umsa.efu.repository;

import com.umsa.efu.domain.Fraterno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Fraterno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FraternoRepository extends JpaRepository<Fraterno, Long> {

}

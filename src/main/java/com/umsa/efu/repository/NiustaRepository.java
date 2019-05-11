package com.umsa.efu.repository;

import com.umsa.efu.domain.Niusta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Niusta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NiustaRepository extends JpaRepository<Niusta, Long> {

}

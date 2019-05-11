package com.umsa.efu.web.rest;

import com.umsa.efu.domain.Fraterno;
import com.umsa.efu.repository.FraternoRepository;
import com.umsa.efu.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.umsa.efu.domain.Fraterno}.
 */
@RestController
@RequestMapping("/api")
public class FraternoResource {

    private final Logger log = LoggerFactory.getLogger(FraternoResource.class);

    private static final String ENTITY_NAME = "fraterno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FraternoRepository fraternoRepository;

    public FraternoResource(FraternoRepository fraternoRepository) {
        this.fraternoRepository = fraternoRepository;
    }

    /**
     * {@code POST  /fraternos} : Create a new fraterno.
     *
     * @param fraterno the fraterno to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fraterno, or with status {@code 400 (Bad Request)} if the fraterno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fraternos")
    public ResponseEntity<Fraterno> createFraterno(@RequestBody Fraterno fraterno) throws URISyntaxException {
        log.debug("REST request to save Fraterno : {}", fraterno);
        if (fraterno.getId() != null) {
            throw new BadRequestAlertException("A new fraterno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fraterno result = fraternoRepository.save(fraterno);
        return ResponseEntity.created(new URI("/api/fraternos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fraternos} : Updates an existing fraterno.
     *
     * @param fraterno the fraterno to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraterno,
     * or with status {@code 400 (Bad Request)} if the fraterno is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fraterno couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fraternos")
    public ResponseEntity<Fraterno> updateFraterno(@RequestBody Fraterno fraterno) throws URISyntaxException {
        log.debug("REST request to update Fraterno : {}", fraterno);
        if (fraterno.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fraterno result = fraternoRepository.save(fraterno);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fraterno.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fraternos} : get all the fraternos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fraternos in body.
     */
    @GetMapping("/fraternos")
    public List<Fraterno> getAllFraternos() {
        log.debug("REST request to get all Fraternos");
        return fraternoRepository.findAll();
    }

    /**
     * {@code GET  /fraternos/:id} : get the "id" fraterno.
     *
     * @param id the id of the fraterno to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fraterno, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fraternos/{id}")
    public ResponseEntity<Fraterno> getFraterno(@PathVariable Long id) {
        log.debug("REST request to get Fraterno : {}", id);
        Optional<Fraterno> fraterno = fraternoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fraterno);
    }

    /**
     * {@code DELETE  /fraternos/:id} : delete the "id" fraterno.
     *
     * @param id the id of the fraterno to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fraternos/{id}")
    public ResponseEntity<Void> deleteFraterno(@PathVariable Long id) {
        log.debug("REST request to delete Fraterno : {}", id);
        fraternoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

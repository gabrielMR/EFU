package com.umsa.efu.web.rest;

import com.umsa.efu.domain.Niusta;
import com.umsa.efu.repository.NiustaRepository;
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
 * REST controller for managing {@link com.umsa.efu.domain.Niusta}.
 */
@RestController
@RequestMapping("/api")
public class NiustaResource {

    private final Logger log = LoggerFactory.getLogger(NiustaResource.class);

    private static final String ENTITY_NAME = "niusta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NiustaRepository niustaRepository;

    public NiustaResource(NiustaRepository niustaRepository) {
        this.niustaRepository = niustaRepository;
    }

    /**
     * {@code POST  /niustas} : Create a new niusta.
     *
     * @param niusta the niusta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new niusta, or with status {@code 400 (Bad Request)} if the niusta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/niustas")
    public ResponseEntity<Niusta> createNiusta(@RequestBody Niusta niusta) throws URISyntaxException {
        log.debug("REST request to save Niusta : {}", niusta);
        if (niusta.getId() != null) {
            throw new BadRequestAlertException("A new niusta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Niusta result = niustaRepository.save(niusta);
        return ResponseEntity.created(new URI("/api/niustas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /niustas} : Updates an existing niusta.
     *
     * @param niusta the niusta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated niusta,
     * or with status {@code 400 (Bad Request)} if the niusta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the niusta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/niustas")
    public ResponseEntity<Niusta> updateNiusta(@RequestBody Niusta niusta) throws URISyntaxException {
        log.debug("REST request to update Niusta : {}", niusta);
        if (niusta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Niusta result = niustaRepository.save(niusta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, niusta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /niustas} : get all the niustas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of niustas in body.
     */
    @GetMapping("/niustas")
    public List<Niusta> getAllNiustas() {
        log.debug("REST request to get all Niustas");
        return niustaRepository.findAll();
    }

    /**
     * {@code GET  /niustas/:id} : get the "id" niusta.
     *
     * @param id the id of the niusta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the niusta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/niustas/{id}")
    public ResponseEntity<Niusta> getNiusta(@PathVariable Long id) {
        log.debug("REST request to get Niusta : {}", id);
        Optional<Niusta> niusta = niustaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(niusta);
    }

    /**
     * {@code DELETE  /niustas/:id} : delete the "id" niusta.
     *
     * @param id the id of the niusta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/niustas/{id}")
    public ResponseEntity<Void> deleteNiusta(@PathVariable Long id) {
        log.debug("REST request to delete Niusta : {}", id);
        niustaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

package com.umsa.efu.web.rest;

import com.umsa.efu.domain.Delegado;
import com.umsa.efu.repository.DelegadoRepository;
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
 * REST controller for managing {@link com.umsa.efu.domain.Delegado}.
 */
@RestController
@RequestMapping("/api")
public class DelegadoResource {

    private final Logger log = LoggerFactory.getLogger(DelegadoResource.class);

    private static final String ENTITY_NAME = "delegado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DelegadoRepository delegadoRepository;

    public DelegadoResource(DelegadoRepository delegadoRepository) {
        this.delegadoRepository = delegadoRepository;
    }

    /**
     * {@code POST  /delegados} : Create a new delegado.
     *
     * @param delegado the delegado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new delegado, or with status {@code 400 (Bad Request)} if the delegado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delegados")
    public ResponseEntity<Delegado> createDelegado(@RequestBody Delegado delegado) throws URISyntaxException {
        log.debug("REST request to save Delegado : {}", delegado);
        if (delegado.getId() != null) {
            throw new BadRequestAlertException("A new delegado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Delegado result = delegadoRepository.save(delegado);
        return ResponseEntity.created(new URI("/api/delegados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delegados} : Updates an existing delegado.
     *
     * @param delegado the delegado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delegado,
     * or with status {@code 400 (Bad Request)} if the delegado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the delegado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delegados")
    public ResponseEntity<Delegado> updateDelegado(@RequestBody Delegado delegado) throws URISyntaxException {
        log.debug("REST request to update Delegado : {}", delegado);
        if (delegado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Delegado result = delegadoRepository.save(delegado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, delegado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delegados} : get all the delegados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of delegados in body.
     */
    @GetMapping("/delegados")
    public List<Delegado> getAllDelegados() {
        log.debug("REST request to get all Delegados");
        return delegadoRepository.findAll();
    }

    /**
     * {@code GET  /delegados/:id} : get the "id" delegado.
     *
     * @param id the id of the delegado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the delegado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delegados/{id}")
    public ResponseEntity<Delegado> getDelegado(@PathVariable Long id) {
        log.debug("REST request to get Delegado : {}", id);
        Optional<Delegado> delegado = delegadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(delegado);
    }

    /**
     * {@code DELETE  /delegados/:id} : delete the "id" delegado.
     *
     * @param id the id of the delegado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delegados/{id}")
    public ResponseEntity<Void> deleteDelegado(@PathVariable Long id) {
        log.debug("REST request to delete Delegado : {}", id);
        delegadoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

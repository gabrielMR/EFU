package com.umsa.efu.web.rest;

import com.umsa.efu.domain.Fraternidad;
import com.umsa.efu.repository.FraternidadRepository;
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
 * REST controller for managing {@link com.umsa.efu.domain.Fraternidad}.
 */
@RestController
@RequestMapping("/api")
public class FraternidadResource {

    private final Logger log = LoggerFactory.getLogger(FraternidadResource.class);

    private static final String ENTITY_NAME = "fraternidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FraternidadRepository fraternidadRepository;

    public FraternidadResource(FraternidadRepository fraternidadRepository) {
        this.fraternidadRepository = fraternidadRepository;
    }

    /**
     * {@code POST  /fraternidads} : Create a new fraternidad.
     *
     * @param fraternidad the fraternidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fraternidad, or with status {@code 400 (Bad Request)} if the fraternidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fraternidads")
    public ResponseEntity<Fraternidad> createFraternidad(@RequestBody Fraternidad fraternidad) throws URISyntaxException {
        log.debug("REST request to save Fraternidad : {}", fraternidad);
        if (fraternidad.getId() != null) {
            throw new BadRequestAlertException("A new fraternidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fraternidad result = fraternidadRepository.save(fraternidad);
        return ResponseEntity.created(new URI("/api/fraternidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fraternidads} : Updates an existing fraternidad.
     *
     * @param fraternidad the fraternidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraternidad,
     * or with status {@code 400 (Bad Request)} if the fraternidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fraternidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fraternidads")
    public ResponseEntity<Fraternidad> updateFraternidad(@RequestBody Fraternidad fraternidad) throws URISyntaxException {
        log.debug("REST request to update Fraternidad : {}", fraternidad);
        if (fraternidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fraternidad result = fraternidadRepository.save(fraternidad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fraternidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fraternidads} : get all the fraternidads.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fraternidads in body.
     */
    @GetMapping("/fraternidads")
    public List<Fraternidad> getAllFraternidads(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Fraternidads");
        return fraternidadRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /fraternidads/:id} : get the "id" fraternidad.
     *
     * @param id the id of the fraternidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fraternidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fraternidads/{id}")
    public ResponseEntity<Fraternidad> getFraternidad(@PathVariable Long id) {
        log.debug("REST request to get Fraternidad : {}", id);
        Optional<Fraternidad> fraternidad = fraternidadRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(fraternidad);
    }

    /**
     * {@code DELETE  /fraternidads/:id} : delete the "id" fraternidad.
     *
     * @param id the id of the fraternidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fraternidads/{id}")
    public ResponseEntity<Void> deleteFraternidad(@PathVariable Long id) {
        log.debug("REST request to delete Fraternidad : {}", id);
        fraternidadRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

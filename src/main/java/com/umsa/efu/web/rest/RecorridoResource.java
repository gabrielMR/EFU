package com.umsa.efu.web.rest;

import com.umsa.efu.domain.Recorrido;
import com.umsa.efu.repository.RecorridoRepository;
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
 * REST controller for managing {@link com.umsa.efu.domain.Recorrido}.
 */
@RestController
@RequestMapping("/api")
public class RecorridoResource {

    private final Logger log = LoggerFactory.getLogger(RecorridoResource.class);

    private static final String ENTITY_NAME = "recorrido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecorridoRepository recorridoRepository;

    public RecorridoResource(RecorridoRepository recorridoRepository) {
        this.recorridoRepository = recorridoRepository;
    }

    /**
     * {@code POST  /recorridos} : Create a new recorrido.
     *
     * @param recorrido the recorrido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recorrido, or with status {@code 400 (Bad Request)} if the recorrido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recorridos")
    public ResponseEntity<Recorrido> createRecorrido(@RequestBody Recorrido recorrido) throws URISyntaxException {
        log.debug("REST request to save Recorrido : {}", recorrido);
        if (recorrido.getId() != null) {
            throw new BadRequestAlertException("A new recorrido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recorrido result = recorridoRepository.save(recorrido);
        return ResponseEntity.created(new URI("/api/recorridos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recorridos} : Updates an existing recorrido.
     *
     * @param recorrido the recorrido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recorrido,
     * or with status {@code 400 (Bad Request)} if the recorrido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recorrido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recorridos")
    public ResponseEntity<Recorrido> updateRecorrido(@RequestBody Recorrido recorrido) throws URISyntaxException {
        log.debug("REST request to update Recorrido : {}", recorrido);
        if (recorrido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Recorrido result = recorridoRepository.save(recorrido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recorrido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recorridos} : get all the recorridos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recorridos in body.
     */
    @GetMapping("/recorridos")
    public List<Recorrido> getAllRecorridos() {
        log.debug("REST request to get all Recorridos");
        return recorridoRepository.findAll();
    }

    /**
     * {@code GET  /recorridos/:id} : get the "id" recorrido.
     *
     * @param id the id of the recorrido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recorrido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recorridos/{id}")
    public ResponseEntity<Recorrido> getRecorrido(@PathVariable Long id) {
        log.debug("REST request to get Recorrido : {}", id);
        Optional<Recorrido> recorrido = recorridoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recorrido);
    }

    /**
     * {@code DELETE  /recorridos/:id} : delete the "id" recorrido.
     *
     * @param id the id of the recorrido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recorridos/{id}")
    public ResponseEntity<Void> deleteRecorrido(@PathVariable Long id) {
        log.debug("REST request to delete Recorrido : {}", id);
        recorridoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

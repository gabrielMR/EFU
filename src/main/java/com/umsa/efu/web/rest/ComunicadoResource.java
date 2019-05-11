package com.umsa.efu.web.rest;

import com.umsa.efu.domain.Comunicado;
import com.umsa.efu.repository.ComunicadoRepository;
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
 * REST controller for managing {@link com.umsa.efu.domain.Comunicado}.
 */
@RestController
@RequestMapping("/api")
public class ComunicadoResource {

    private final Logger log = LoggerFactory.getLogger(ComunicadoResource.class);

    private static final String ENTITY_NAME = "comunicado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComunicadoRepository comunicadoRepository;

    public ComunicadoResource(ComunicadoRepository comunicadoRepository) {
        this.comunicadoRepository = comunicadoRepository;
    }

    /**
     * {@code POST  /comunicados} : Create a new comunicado.
     *
     * @param comunicado the comunicado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comunicado, or with status {@code 400 (Bad Request)} if the comunicado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comunicados")
    public ResponseEntity<Comunicado> createComunicado(@RequestBody Comunicado comunicado) throws URISyntaxException {
        log.debug("REST request to save Comunicado : {}", comunicado);
        if (comunicado.getId() != null) {
            throw new BadRequestAlertException("A new comunicado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comunicado result = comunicadoRepository.save(comunicado);
        return ResponseEntity.created(new URI("/api/comunicados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comunicados} : Updates an existing comunicado.
     *
     * @param comunicado the comunicado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comunicado,
     * or with status {@code 400 (Bad Request)} if the comunicado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comunicado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comunicados")
    public ResponseEntity<Comunicado> updateComunicado(@RequestBody Comunicado comunicado) throws URISyntaxException {
        log.debug("REST request to update Comunicado : {}", comunicado);
        if (comunicado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Comunicado result = comunicadoRepository.save(comunicado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, comunicado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comunicados} : get all the comunicados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comunicados in body.
     */
    @GetMapping("/comunicados")
    public List<Comunicado> getAllComunicados() {
        log.debug("REST request to get all Comunicados");
        return comunicadoRepository.findAll();
    }

    /**
     * {@code GET  /comunicados/:id} : get the "id" comunicado.
     *
     * @param id the id of the comunicado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comunicado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comunicados/{id}")
    public ResponseEntity<Comunicado> getComunicado(@PathVariable Long id) {
        log.debug("REST request to get Comunicado : {}", id);
        Optional<Comunicado> comunicado = comunicadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(comunicado);
    }

    /**
     * {@code DELETE  /comunicados/:id} : delete the "id" comunicado.
     *
     * @param id the id of the comunicado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comunicados/{id}")
    public ResponseEntity<Void> deleteComunicado(@PathVariable Long id) {
        log.debug("REST request to delete Comunicado : {}", id);
        comunicadoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

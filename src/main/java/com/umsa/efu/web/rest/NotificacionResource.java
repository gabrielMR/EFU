package com.umsa.efu.web.rest;

import com.umsa.efu.domain.Notificacion;
import com.umsa.efu.repository.NotificacionRepository;
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
 * REST controller for managing {@link com.umsa.efu.domain.Notificacion}.
 */
@RestController
@RequestMapping("/api")
public class NotificacionResource {

    private final Logger log = LoggerFactory.getLogger(NotificacionResource.class);

    private static final String ENTITY_NAME = "notificacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificacionRepository notificacionRepository;

    public NotificacionResource(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    /**
     * {@code POST  /notificacions} : Create a new notificacion.
     *
     * @param notificacion the notificacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificacion, or with status {@code 400 (Bad Request)} if the notificacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notificacions")
    public ResponseEntity<Notificacion> createNotificacion(@RequestBody Notificacion notificacion) throws URISyntaxException {
        log.debug("REST request to save Notificacion : {}", notificacion);
        if (notificacion.getId() != null) {
            throw new BadRequestAlertException("A new notificacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Notificacion result = notificacionRepository.save(notificacion);
        return ResponseEntity.created(new URI("/api/notificacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notificacions} : Updates an existing notificacion.
     *
     * @param notificacion the notificacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificacion,
     * or with status {@code 400 (Bad Request)} if the notificacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notificacions")
    public ResponseEntity<Notificacion> updateNotificacion(@RequestBody Notificacion notificacion) throws URISyntaxException {
        log.debug("REST request to update Notificacion : {}", notificacion);
        if (notificacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Notificacion result = notificacionRepository.save(notificacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notificacions} : get all the notificacions.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificacions in body.
     */
    @GetMapping("/notificacions")
    public List<Notificacion> getAllNotificacions(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Notificacions");
        return notificacionRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /notificacions/:id} : get the "id" notificacion.
     *
     * @param id the id of the notificacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notificacions/{id}")
    public ResponseEntity<Notificacion> getNotificacion(@PathVariable Long id) {
        log.debug("REST request to get Notificacion : {}", id);
        Optional<Notificacion> notificacion = notificacionRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(notificacion);
    }

    /**
     * {@code DELETE  /notificacions/:id} : delete the "id" notificacion.
     *
     * @param id the id of the notificacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notificacions/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Long id) {
        log.debug("REST request to delete Notificacion : {}", id);
        notificacionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

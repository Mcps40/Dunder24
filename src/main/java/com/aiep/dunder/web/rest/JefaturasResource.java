package com.aiep.dunder.web.rest;

import com.aiep.dunder.domain.Jefaturas;
import com.aiep.dunder.repository.JefaturasRepository;
import com.aiep.dunder.service.JefaturasService;
import com.aiep.dunder.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aiep.dunder.domain.Jefaturas}.
 */
@RestController
@RequestMapping("/api/jefaturas")
public class JefaturasResource {

    private static final Logger LOG = LoggerFactory.getLogger(JefaturasResource.class);

    private static final String ENTITY_NAME = "jefaturas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JefaturasService jefaturasService;

    private final JefaturasRepository jefaturasRepository;

    public JefaturasResource(JefaturasService jefaturasService, JefaturasRepository jefaturasRepository) {
        this.jefaturasService = jefaturasService;
        this.jefaturasRepository = jefaturasRepository;
    }

    /**
     * {@code POST  /jefaturas} : Create a new jefaturas.
     *
     * @param jefaturas the jefaturas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jefaturas, or with status {@code 400 (Bad Request)} if the jefaturas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Jefaturas> createJefaturas(@RequestBody Jefaturas jefaturas) throws URISyntaxException {
        LOG.debug("REST request to save Jefaturas : {}", jefaturas);
        if (jefaturas.getId() != null) {
            throw new BadRequestAlertException("A new jefaturas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        jefaturas = jefaturasService.save(jefaturas);
        return ResponseEntity.created(new URI("/api/jefaturas/" + jefaturas.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, jefaturas.getId().toString()))
            .body(jefaturas);
    }

    /**
     * {@code PUT  /jefaturas/:id} : Updates an existing jefaturas.
     *
     * @param id the id of the jefaturas to save.
     * @param jefaturas the jefaturas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jefaturas,
     * or with status {@code 400 (Bad Request)} if the jefaturas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jefaturas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Jefaturas> updateJefaturas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Jefaturas jefaturas
    ) throws URISyntaxException {
        LOG.debug("REST request to update Jefaturas : {}, {}", id, jefaturas);
        if (jefaturas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jefaturas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jefaturasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        jefaturas = jefaturasService.update(jefaturas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jefaturas.getId().toString()))
            .body(jefaturas);
    }

    /**
     * {@code PATCH  /jefaturas/:id} : Partial updates given fields of an existing jefaturas, field will ignore if it is null
     *
     * @param id the id of the jefaturas to save.
     * @param jefaturas the jefaturas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jefaturas,
     * or with status {@code 400 (Bad Request)} if the jefaturas is not valid,
     * or with status {@code 404 (Not Found)} if the jefaturas is not found,
     * or with status {@code 500 (Internal Server Error)} if the jefaturas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Jefaturas> partialUpdateJefaturas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Jefaturas jefaturas
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Jefaturas partially : {}, {}", id, jefaturas);
        if (jefaturas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jefaturas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jefaturasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Jefaturas> result = jefaturasService.partialUpdate(jefaturas);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jefaturas.getId().toString())
        );
    }

    /**
     * {@code GET  /jefaturas} : get all the jefaturas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jefaturas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Jefaturas>> getAllJefaturas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Jefaturas");
        Page<Jefaturas> page;
        if (eagerload) {
            page = jefaturasService.findAllWithEagerRelationships(pageable);
        } else {
            page = jefaturasService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jefaturas/:id} : get the "id" jefaturas.
     *
     * @param id the id of the jefaturas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jefaturas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Jefaturas> getJefaturas(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Jefaturas : {}", id);
        Optional<Jefaturas> jefaturas = jefaturasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jefaturas);
    }

    /**
     * {@code DELETE  /jefaturas/:id} : delete the "id" jefaturas.
     *
     * @param id the id of the jefaturas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJefaturas(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Jefaturas : {}", id);
        jefaturasService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

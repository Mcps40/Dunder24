package com.aiep.dunder.web.rest;

import com.aiep.dunder.domain.Departamento;
import com.aiep.dunder.repository.DepartamentoRepository;
import com.aiep.dunder.service.DepartamentoService;
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
 * REST controller for managing {@link com.aiep.dunder.domain.Departamento}.
 */
@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoResource {

    private static final Logger LOG = LoggerFactory.getLogger(DepartamentoResource.class);

    private static final String ENTITY_NAME = "departamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartamentoService departamentoService;

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoResource(DepartamentoService departamentoService, DepartamentoRepository departamentoRepository) {
        this.departamentoService = departamentoService;
        this.departamentoRepository = departamentoRepository;
    }

    /**
     * {@code POST  /departamentos} : Create a new departamento.
     *
     * @param departamento the departamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departamento, or with status {@code 400 (Bad Request)} if the departamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Departamento> createDepartamento(@RequestBody Departamento departamento) throws URISyntaxException {
        LOG.debug("REST request to save Departamento : {}", departamento);
        if (departamento.getId() != null) {
            throw new BadRequestAlertException("A new departamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        departamento = departamentoService.save(departamento);
        return ResponseEntity.created(new URI("/api/departamentos/" + departamento.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, departamento.getId().toString()))
            .body(departamento);
    }

    /**
     * {@code PUT  /departamentos/:id} : Updates an existing departamento.
     *
     * @param id the id of the departamento to save.
     * @param departamento the departamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamento,
     * or with status {@code 400 (Bad Request)} if the departamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Departamento> updateDepartamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Departamento departamento
    ) throws URISyntaxException {
        LOG.debug("REST request to update Departamento : {}, {}", id, departamento);
        if (departamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        departamento = departamentoService.update(departamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamento.getId().toString()))
            .body(departamento);
    }

    /**
     * {@code PATCH  /departamentos/:id} : Partial updates given fields of an existing departamento, field will ignore if it is null
     *
     * @param id the id of the departamento to save.
     * @param departamento the departamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamento,
     * or with status {@code 400 (Bad Request)} if the departamento is not valid,
     * or with status {@code 404 (Not Found)} if the departamento is not found,
     * or with status {@code 500 (Internal Server Error)} if the departamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Departamento> partialUpdateDepartamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Departamento departamento
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Departamento partially : {}, {}", id, departamento);
        if (departamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Departamento> result = departamentoService.partialUpdate(departamento);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamento.getId().toString())
        );
    }

    /**
     * {@code GET  /departamentos} : get all the departamentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departamentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Departamento>> getAllDepartamentos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Departamentos");
        Page<Departamento> page = departamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /departamentos/:id} : get the "id" departamento.
     *
     * @param id the id of the departamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Departamento> getDepartamento(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Departamento : {}", id);
        Optional<Departamento> departamento = departamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departamento);
    }

    /**
     * {@code DELETE  /departamentos/:id} : delete the "id" departamento.
     *
     * @param id the id of the departamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Departamento : {}", id);
        departamentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

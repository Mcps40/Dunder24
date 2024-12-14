package com.aiep.dunder.service;

import com.aiep.dunder.domain.Empleados;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.aiep.dunder.domain.Empleados}.
 */
public interface EmpleadosService {
    /**
     * Save a empleados.
     *
     * @param empleados the entity to save.
     * @return the persisted entity.
     */
    Empleados save(Empleados empleados);

    /**
     * Updates a empleados.
     *
     * @param empleados the entity to update.
     * @return the persisted entity.
     */
    Empleados update(Empleados empleados);

    /**
     * Partially updates a empleados.
     *
     * @param empleados the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Empleados> partialUpdate(Empleados empleados);

    /**
     * Get all the empleados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Empleados> findAll(Pageable pageable);

    /**
     * Get all the empleados with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Empleados> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" empleados.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Empleados> findOne(Long id);

    /**
     * Delete the "id" empleados.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

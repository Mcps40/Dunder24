package com.aiep.dunder.service;

import com.aiep.dunder.domain.Jefaturas;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.aiep.dunder.domain.Jefaturas}.
 */
public interface JefaturasService {
    /**
     * Save a jefaturas.
     *
     * @param jefaturas the entity to save.
     * @return the persisted entity.
     */
    Jefaturas save(Jefaturas jefaturas);

    /**
     * Updates a jefaturas.
     *
     * @param jefaturas the entity to update.
     * @return the persisted entity.
     */
    Jefaturas update(Jefaturas jefaturas);

    /**
     * Partially updates a jefaturas.
     *
     * @param jefaturas the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Jefaturas> partialUpdate(Jefaturas jefaturas);

    /**
     * Get all the jefaturas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Jefaturas> findAll(Pageable pageable);

    /**
     * Get all the jefaturas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Jefaturas> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" jefaturas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Jefaturas> findOne(Long id);

    /**
     * Delete the "id" jefaturas.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

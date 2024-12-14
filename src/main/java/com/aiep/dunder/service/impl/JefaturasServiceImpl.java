package com.aiep.dunder.service.impl;

import com.aiep.dunder.domain.Jefaturas;
import com.aiep.dunder.repository.JefaturasRepository;
import com.aiep.dunder.service.JefaturasService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.dunder.domain.Jefaturas}.
 */
@Service
@Transactional
public class JefaturasServiceImpl implements JefaturasService {

    private static final Logger LOG = LoggerFactory.getLogger(JefaturasServiceImpl.class);

    private final JefaturasRepository jefaturasRepository;

    public JefaturasServiceImpl(JefaturasRepository jefaturasRepository) {
        this.jefaturasRepository = jefaturasRepository;
    }

    @Override
    public Jefaturas save(Jefaturas jefaturas) {
        LOG.debug("Request to save Jefaturas : {}", jefaturas);
        return jefaturasRepository.save(jefaturas);
    }

    @Override
    public Jefaturas update(Jefaturas jefaturas) {
        LOG.debug("Request to update Jefaturas : {}", jefaturas);
        return jefaturasRepository.save(jefaturas);
    }

    @Override
    public Optional<Jefaturas> partialUpdate(Jefaturas jefaturas) {
        LOG.debug("Request to partially update Jefaturas : {}", jefaturas);

        return jefaturasRepository
            .findById(jefaturas.getId())
            .map(existingJefaturas -> {
                if (jefaturas.getNombre_jefe() != null) {
                    existingJefaturas.setNombre_jefe(jefaturas.getNombre_jefe());
                }

                return existingJefaturas;
            })
            .map(jefaturasRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Jefaturas> findAll(Pageable pageable) {
        LOG.debug("Request to get all Jefaturas");
        return jefaturasRepository.findAll(pageable);
    }

    public Page<Jefaturas> findAllWithEagerRelationships(Pageable pageable) {
        return jefaturasRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Jefaturas> findOne(Long id) {
        LOG.debug("Request to get Jefaturas : {}", id);
        return jefaturasRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Jefaturas : {}", id);
        jefaturasRepository.deleteById(id);
    }
}

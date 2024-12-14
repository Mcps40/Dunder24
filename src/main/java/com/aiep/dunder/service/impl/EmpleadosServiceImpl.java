package com.aiep.dunder.service.impl;

import com.aiep.dunder.domain.Empleados;
import com.aiep.dunder.repository.EmpleadosRepository;
import com.aiep.dunder.service.EmpleadosService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.dunder.domain.Empleados}.
 */
@Service
@Transactional
public class EmpleadosServiceImpl implements EmpleadosService {

    private static final Logger LOG = LoggerFactory.getLogger(EmpleadosServiceImpl.class);

    private final EmpleadosRepository empleadosRepository;

    public EmpleadosServiceImpl(EmpleadosRepository empleadosRepository) {
        this.empleadosRepository = empleadosRepository;
    }

    @Override
    public Empleados save(Empleados empleados) {
        LOG.debug("Request to save Empleados : {}", empleados);
        return empleadosRepository.save(empleados);
    }

    @Override
    public Empleados update(Empleados empleados) {
        LOG.debug("Request to update Empleados : {}", empleados);
        return empleadosRepository.save(empleados);
    }

    @Override
    public Optional<Empleados> partialUpdate(Empleados empleados) {
        LOG.debug("Request to partially update Empleados : {}", empleados);

        return empleadosRepository
            .findById(empleados.getId())
            .map(existingEmpleados -> {
                if (empleados.getNombre_empleado() != null) {
                    existingEmpleados.setNombre_empleado(empleados.getNombre_empleado());
                }
                if (empleados.getApellido_empleado() != null) {
                    existingEmpleados.setApellido_empleado(empleados.getApellido_empleado());
                }
                if (empleados.getTelefono_empleado() != null) {
                    existingEmpleados.setTelefono_empleado(empleados.getTelefono_empleado());
                }
                if (empleados.getCorreo_empleado() != null) {
                    existingEmpleados.setCorreo_empleado(empleados.getCorreo_empleado());
                }

                return existingEmpleados;
            })
            .map(empleadosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Empleados> findAll(Pageable pageable) {
        LOG.debug("Request to get all Empleados");
        return empleadosRepository.findAll(pageable);
    }

    public Page<Empleados> findAllWithEagerRelationships(Pageable pageable) {
        return empleadosRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleados> findOne(Long id) {
        LOG.debug("Request to get Empleados : {}", id);
        return empleadosRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Empleados : {}", id);
        empleadosRepository.deleteById(id);
    }
}

package com.aiep.dunder.repository;

import com.aiep.dunder.domain.Empleados;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Empleados entity.
 */
@Repository
public interface EmpleadosRepository extends JpaRepository<Empleados, Long> {
    default Optional<Empleados> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Empleados> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Empleados> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select empleados from Empleados empleados left join fetch empleados.departamento left join fetch empleados.jefatura",
        countQuery = "select count(empleados) from Empleados empleados"
    )
    Page<Empleados> findAllWithToOneRelationships(Pageable pageable);

    @Query("select empleados from Empleados empleados left join fetch empleados.departamento left join fetch empleados.jefatura")
    List<Empleados> findAllWithToOneRelationships();

    @Query(
        "select empleados from Empleados empleados left join fetch empleados.departamento left join fetch empleados.jefatura where empleados.id =:id"
    )
    Optional<Empleados> findOneWithToOneRelationships(@Param("id") Long id);
}

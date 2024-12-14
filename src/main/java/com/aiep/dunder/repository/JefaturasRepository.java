package com.aiep.dunder.repository;

import com.aiep.dunder.domain.Jefaturas;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Jefaturas entity.
 */
@Repository
public interface JefaturasRepository extends JpaRepository<Jefaturas, Long> {
    default Optional<Jefaturas> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Jefaturas> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Jefaturas> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select jefaturas from Jefaturas jefaturas left join fetch jefaturas.departamento",
        countQuery = "select count(jefaturas) from Jefaturas jefaturas"
    )
    Page<Jefaturas> findAllWithToOneRelationships(Pageable pageable);

    @Query("select jefaturas from Jefaturas jefaturas left join fetch jefaturas.departamento")
    List<Jefaturas> findAllWithToOneRelationships();

    @Query("select jefaturas from Jefaturas jefaturas left join fetch jefaturas.departamento where jefaturas.id =:id")
    Optional<Jefaturas> findOneWithToOneRelationships(@Param("id") Long id);
}

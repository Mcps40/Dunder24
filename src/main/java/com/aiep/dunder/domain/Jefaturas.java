package com.aiep.dunder.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Jefaturas.
 */
@Entity
@Table(name = "jefaturas")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Jefaturas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_jefe")
    private String nombre_jefe;

    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento departamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Jefaturas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_jefe() {
        return this.nombre_jefe;
    }

    public Jefaturas nombre_jefe(String nombre_jefe) {
        this.setNombre_jefe(nombre_jefe);
        return this;
    }

    public void setNombre_jefe(String nombre_jefe) {
        this.nombre_jefe = nombre_jefe;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Jefaturas departamento(Departamento departamento) {
        this.setDepartamento(departamento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jefaturas)) {
            return false;
        }
        return getId() != null && getId().equals(((Jefaturas) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jefaturas{" +
            "id=" + getId() +
            ", nombre_jefe='" + getNombre_jefe() + "'" +
            "}";
    }
}

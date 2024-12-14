package com.aiep.dunder.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Departamento.
 */
@Entity
@Table(name = "departamento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_depto")
    private String nombre_depto;

    @Column(name = "ubicacion_depto")
    private String ubicacion_depto;

    @Column(name = "presupuesto_depto")
    private Integer presupuesto_depto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Departamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_depto() {
        return this.nombre_depto;
    }

    public Departamento nombre_depto(String nombre_depto) {
        this.setNombre_depto(nombre_depto);
        return this;
    }

    public void setNombre_depto(String nombre_depto) {
        this.nombre_depto = nombre_depto;
    }

    public String getUbicacion_depto() {
        return this.ubicacion_depto;
    }

    public Departamento ubicacion_depto(String ubicacion_depto) {
        this.setUbicacion_depto(ubicacion_depto);
        return this;
    }

    public void setUbicacion_depto(String ubicacion_depto) {
        this.ubicacion_depto = ubicacion_depto;
    }

    public Integer getPresupuesto_depto() {
        return this.presupuesto_depto;
    }

    public Departamento presupuesto_depto(Integer presupuesto_depto) {
        this.setPresupuesto_depto(presupuesto_depto);
        return this;
    }

    public void setPresupuesto_depto(Integer presupuesto_depto) {
        this.presupuesto_depto = presupuesto_depto;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departamento)) {
            return false;
        }
        return getId() != null && getId().equals(((Departamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Departamento{" +
            "id=" + getId() +
            ", nombre_depto='" + getNombre_depto() + "'" +
            ", ubicacion_depto='" + getUbicacion_depto() + "'" +
            ", presupuesto_depto=" + getPresupuesto_depto() +
            "}";
    }
}

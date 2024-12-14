package com.aiep.dunder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Empleados.
 */
@Entity
@Table(name = "empleados")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_empleado")
    private String nombre_empleado;

    @Column(name = "apellido_empleado")
    private String apellido_empleado;

    @Column(name = "telefono_empleado")
    private String telefono_empleado;

    @Column(name = "correo_empleado")
    private String correo_empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento departamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "departamento" }, allowSetters = true)
    private Jefaturas jefatura;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Empleados id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_empleado() {
        return this.nombre_empleado;
    }

    public Empleados nombre_empleado(String nombre_empleado) {
        this.setNombre_empleado(nombre_empleado);
        return this;
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public String getApellido_empleado() {
        return this.apellido_empleado;
    }

    public Empleados apellido_empleado(String apellido_empleado) {
        this.setApellido_empleado(apellido_empleado);
        return this;
    }

    public void setApellido_empleado(String apellido_empleado) {
        this.apellido_empleado = apellido_empleado;
    }

    public String getTelefono_empleado() {
        return this.telefono_empleado;
    }

    public Empleados telefono_empleado(String telefono_empleado) {
        this.setTelefono_empleado(telefono_empleado);
        return this;
    }

    public void setTelefono_empleado(String telefono_empleado) {
        this.telefono_empleado = telefono_empleado;
    }

    public String getCorreo_empleado() {
        return this.correo_empleado;
    }

    public Empleados correo_empleado(String correo_empleado) {
        this.setCorreo_empleado(correo_empleado);
        return this;
    }

    public void setCorreo_empleado(String correo_empleado) {
        this.correo_empleado = correo_empleado;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Empleados departamento(Departamento departamento) {
        this.setDepartamento(departamento);
        return this;
    }

    public Jefaturas getJefatura() {
        return this.jefatura;
    }

    public void setJefatura(Jefaturas jefaturas) {
        this.jefatura = jefaturas;
    }

    public Empleados jefatura(Jefaturas jefaturas) {
        this.setJefatura(jefaturas);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empleados)) {
            return false;
        }
        return getId() != null && getId().equals(((Empleados) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Empleados{" +
            "id=" + getId() +
            ", nombre_empleado='" + getNombre_empleado() + "'" +
            ", apellido_empleado='" + getApellido_empleado() + "'" +
            ", telefono_empleado='" + getTelefono_empleado() + "'" +
            ", correo_empleado='" + getCorreo_empleado() + "'" +
            "}";
    }
}

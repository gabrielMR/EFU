package com.umsa.efu.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Recorrido.
 */
@Entity
@Table(name = "recorrido")
public class Recorrido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "inicio")
    private String inicio;

    @Column(name = "fin")
    private String fin;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "gestion")
    private Integer gestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Recorrido nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInicio() {
        return inicio;
    }

    public Recorrido inicio(String inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public Recorrido fin(String fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Recorrido descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getGestion() {
        return gestion;
    }

    public Recorrido gestion(Integer gestion) {
        this.gestion = gestion;
        return this;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recorrido)) {
            return false;
        }
        return id != null && id.equals(((Recorrido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Recorrido{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", gestion=" + getGestion() +
            "}";
    }
}

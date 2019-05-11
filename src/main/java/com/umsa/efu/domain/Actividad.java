package com.umsa.efu.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Actividad.
 */
@Entity
@Table(name = "actividad")
public class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fechaini")
    private LocalDate fechaini;

    @Column(name = "fechafin")
    private LocalDate fechafin;

    @Column(name = "horaini")
    private ZonedDateTime horaini;

    @Column(name = "horafin")
    private ZonedDateTime horafin;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "gestion")
    private Integer gestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Actividad titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Actividad descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaini() {
        return fechaini;
    }

    public Actividad fechaini(LocalDate fechaini) {
        this.fechaini = fechaini;
        return this;
    }

    public void setFechaini(LocalDate fechaini) {
        this.fechaini = fechaini;
    }

    public LocalDate getFechafin() {
        return fechafin;
    }

    public Actividad fechafin(LocalDate fechafin) {
        this.fechafin = fechafin;
        return this;
    }

    public void setFechafin(LocalDate fechafin) {
        this.fechafin = fechafin;
    }

    public ZonedDateTime getHoraini() {
        return horaini;
    }

    public Actividad horaini(ZonedDateTime horaini) {
        this.horaini = horaini;
        return this;
    }

    public void setHoraini(ZonedDateTime horaini) {
        this.horaini = horaini;
    }

    public ZonedDateTime getHorafin() {
        return horafin;
    }

    public Actividad horafin(ZonedDateTime horafin) {
        this.horafin = horafin;
        return this;
    }

    public void setHorafin(ZonedDateTime horafin) {
        this.horafin = horafin;
    }

    public String getContenido() {
        return contenido;
    }

    public Actividad contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Integer getEstado() {
        return estado;
    }

    public Actividad estado(Integer estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getGestion() {
        return gestion;
    }

    public Actividad gestion(Integer gestion) {
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
        if (!(o instanceof Actividad)) {
            return false;
        }
        return id != null && id.equals(((Actividad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Actividad{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaini='" + getFechaini() + "'" +
            ", fechafin='" + getFechafin() + "'" +
            ", horaini='" + getHoraini() + "'" +
            ", horafin='" + getHorafin() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", estado=" + getEstado() +
            ", gestion=" + getGestion() +
            "}";
    }
}

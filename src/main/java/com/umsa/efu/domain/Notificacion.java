package com.umsa.efu.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Notificacion.
 */
@Entity
@Table(name = "notificacion")
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo_notificacion")
    private String tituloNotificacion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private ZonedDateTime hora;

    @Column(name = "id_fraternidad")
    private Integer idFraternidad;

    @ManyToMany
    @JoinTable(name = "notificacion_nombre",
               joinColumns = @JoinColumn(name = "notificacion_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "nombre_id", referencedColumnName = "id"))
    private Set<Fraternidad> nombres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloNotificacion() {
        return tituloNotificacion;
    }

    public Notificacion tituloNotificacion(String tituloNotificacion) {
        this.tituloNotificacion = tituloNotificacion;
        return this;
    }

    public void setTituloNotificacion(String tituloNotificacion) {
        this.tituloNotificacion = tituloNotificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Notificacion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public Notificacion contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Integer getEstado() {
        return estado;
    }

    public Notificacion estado(Integer estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Notificacion fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ZonedDateTime getHora() {
        return hora;
    }

    public Notificacion hora(ZonedDateTime hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(ZonedDateTime hora) {
        this.hora = hora;
    }

    public Integer getIdFraternidad() {
        return idFraternidad;
    }

    public Notificacion idFraternidad(Integer idFraternidad) {
        this.idFraternidad = idFraternidad;
        return this;
    }

    public void setIdFraternidad(Integer idFraternidad) {
        this.idFraternidad = idFraternidad;
    }

    public Set<Fraternidad> getNombres() {
        return nombres;
    }

    public Notificacion nombres(Set<Fraternidad> fraternidads) {
        this.nombres = fraternidads;
        return this;
    }

    public Notificacion addNombre(Fraternidad fraternidad) {
        this.nombres.add(fraternidad);
        fraternidad.getTituloNotificacions().add(this);
        return this;
    }

    public Notificacion removeNombre(Fraternidad fraternidad) {
        this.nombres.remove(fraternidad);
        fraternidad.getTituloNotificacions().remove(this);
        return this;
    }

    public void setNombres(Set<Fraternidad> fraternidads) {
        this.nombres = fraternidads;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notificacion)) {
            return false;
        }
        return id != null && id.equals(((Notificacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Notificacion{" +
            "id=" + getId() +
            ", tituloNotificacion='" + getTituloNotificacion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", estado=" + getEstado() +
            ", fecha='" + getFecha() + "'" +
            ", hora='" + getHora() + "'" +
            ", idFraternidad=" + getIdFraternidad() +
            "}";
    }
}

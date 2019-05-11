package com.umsa.efu.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fraternidad.
 */
@Entity
@Table(name = "fraternidad")
public class Fraternidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_fraternidad")
    private Integer idFraternidad;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "danza")
    private String danza;

    @Column(name = "instancia")
    private String instancia;

    @Column(name = "fundacion")
    private LocalDate fundacion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private Integer estado;

    @OneToOne
    @JoinColumn(unique = true)
    private Niusta nombreNiusta;

    @OneToOne
    @JoinColumn(unique = true)
    private Delegado nombreDelegado;

    @ManyToMany
    @JoinTable(name = "fraternidad_nombre",
               joinColumns = @JoinColumn(name = "fraternidad_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "nombre_id", referencedColumnName = "id"))
    private Set<Fraterno> nombres = new HashSet<>();

    @ManyToMany(mappedBy = "nombres")
    @JsonIgnore
    private Set<Premio> tituloPremios = new HashSet<>();

    @ManyToMany(mappedBy = "nombres")
    @JsonIgnore
    private Set<Notificacion> tituloNotificacions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdFraternidad() {
        return idFraternidad;
    }

    public Fraternidad idFraternidad(Integer idFraternidad) {
        this.idFraternidad = idFraternidad;
        return this;
    }

    public void setIdFraternidad(Integer idFraternidad) {
        this.idFraternidad = idFraternidad;
    }

    public String getNombre() {
        return nombre;
    }

    public Fraternidad nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDanza() {
        return danza;
    }

    public Fraternidad danza(String danza) {
        this.danza = danza;
        return this;
    }

    public void setDanza(String danza) {
        this.danza = danza;
    }

    public String getInstancia() {
        return instancia;
    }

    public Fraternidad instancia(String instancia) {
        this.instancia = instancia;
        return this;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

    public LocalDate getFundacion() {
        return fundacion;
    }

    public Fraternidad fundacion(LocalDate fundacion) {
        this.fundacion = fundacion;
        return this;
    }

    public void setFundacion(LocalDate fundacion) {
        this.fundacion = fundacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Fraternidad descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstado() {
        return estado;
    }

    public Fraternidad estado(Integer estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Niusta getNombreNiusta() {
        return nombreNiusta;
    }

    public Fraternidad nombreNiusta(Niusta niusta) {
        this.nombreNiusta = niusta;
        return this;
    }

    public void setNombreNiusta(Niusta niusta) {
        this.nombreNiusta = niusta;
    }

    public Delegado getNombreDelegado() {
        return nombreDelegado;
    }

    public Fraternidad nombreDelegado(Delegado delegado) {
        this.nombreDelegado = delegado;
        return this;
    }

    public void setNombreDelegado(Delegado delegado) {
        this.nombreDelegado = delegado;
    }

    public Set<Fraterno> getNombres() {
        return nombres;
    }

    public Fraternidad nombres(Set<Fraterno> fraternos) {
        this.nombres = fraternos;
        return this;
    }

    public Fraternidad addNombre(Fraterno fraterno) {
        this.nombres.add(fraterno);
        fraterno.getCis().add(this);
        return this;
    }

    public Fraternidad removeNombre(Fraterno fraterno) {
        this.nombres.remove(fraterno);
        fraterno.getCis().remove(this);
        return this;
    }

    public void setNombres(Set<Fraterno> fraternos) {
        this.nombres = fraternos;
    }

    public Set<Premio> getTituloPremios() {
        return tituloPremios;
    }

    public Fraternidad tituloPremios(Set<Premio> premios) {
        this.tituloPremios = premios;
        return this;
    }

    public Fraternidad addTituloPremio(Premio premio) {
        this.tituloPremios.add(premio);
        premio.getNombres().add(this);
        return this;
    }

    public Fraternidad removeTituloPremio(Premio premio) {
        this.tituloPremios.remove(premio);
        premio.getNombres().remove(this);
        return this;
    }

    public void setTituloPremios(Set<Premio> premios) {
        this.tituloPremios = premios;
    }

    public Set<Notificacion> getTituloNotificacions() {
        return tituloNotificacions;
    }

    public Fraternidad tituloNotificacions(Set<Notificacion> notificacions) {
        this.tituloNotificacions = notificacions;
        return this;
    }

    public Fraternidad addTituloNotificacion(Notificacion notificacion) {
        this.tituloNotificacions.add(notificacion);
        notificacion.getNombres().add(this);
        return this;
    }

    public Fraternidad removeTituloNotificacion(Notificacion notificacion) {
        this.tituloNotificacions.remove(notificacion);
        notificacion.getNombres().remove(this);
        return this;
    }

    public void setTituloNotificacions(Set<Notificacion> notificacions) {
        this.tituloNotificacions = notificacions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fraternidad)) {
            return false;
        }
        return id != null && id.equals(((Fraternidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fraternidad{" +
            "id=" + getId() +
            ", idFraternidad=" + getIdFraternidad() +
            ", nombre='" + getNombre() + "'" +
            ", danza='" + getDanza() + "'" +
            ", instancia='" + getInstancia() + "'" +
            ", fundacion='" + getFundacion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", estado=" + getEstado() +
            "}";
    }
}

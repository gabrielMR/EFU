package com.umsa.efu.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Premio.
 */
@Entity
@Table(name = "premio")
public class Premio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo_premio")
    private String tituloPremio;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "puesto")
    private Integer puesto;

    @Column(name = "gestion")
    private Integer gestion;

    @Column(name = "id_fraternidad")
    private Integer idFraternidad;

    @ManyToMany
    @JoinTable(name = "premio_nombre",
               joinColumns = @JoinColumn(name = "premio_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "nombre_id", referencedColumnName = "id"))
    private Set<Fraternidad> nombres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloPremio() {
        return tituloPremio;
    }

    public Premio tituloPremio(String tituloPremio) {
        this.tituloPremio = tituloPremio;
        return this;
    }

    public void setTituloPremio(String tituloPremio) {
        this.tituloPremio = tituloPremio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Premio descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public Premio categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getPuesto() {
        return puesto;
    }

    public Premio puesto(Integer puesto) {
        this.puesto = puesto;
        return this;
    }

    public void setPuesto(Integer puesto) {
        this.puesto = puesto;
    }

    public Integer getGestion() {
        return gestion;
    }

    public Premio gestion(Integer gestion) {
        this.gestion = gestion;
        return this;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public Integer getIdFraternidad() {
        return idFraternidad;
    }

    public Premio idFraternidad(Integer idFraternidad) {
        this.idFraternidad = idFraternidad;
        return this;
    }

    public void setIdFraternidad(Integer idFraternidad) {
        this.idFraternidad = idFraternidad;
    }

    public Set<Fraternidad> getNombres() {
        return nombres;
    }

    public Premio nombres(Set<Fraternidad> fraternidads) {
        this.nombres = fraternidads;
        return this;
    }

    public Premio addNombre(Fraternidad fraternidad) {
        this.nombres.add(fraternidad);
        fraternidad.getTituloPremios().add(this);
        return this;
    }

    public Premio removeNombre(Fraternidad fraternidad) {
        this.nombres.remove(fraternidad);
        fraternidad.getTituloPremios().remove(this);
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
        if (!(o instanceof Premio)) {
            return false;
        }
        return id != null && id.equals(((Premio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Premio{" +
            "id=" + getId() +
            ", tituloPremio='" + getTituloPremio() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", puesto=" + getPuesto() +
            ", gestion=" + getGestion() +
            ", idFraternidad=" + getIdFraternidad() +
            "}";
    }
}

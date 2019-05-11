package com.umsa.efu.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fraterno.
 */
@Entity
@Table(name = "fraterno")
public class Fraterno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ci")
    private Integer ci;

    @Column(name = "nombre_fraterno")
    private String nombreFraterno;

    @Column(name = "ru")
    private Integer ru;

    @Column(name = "item")
    private Integer item;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToMany(mappedBy = "nombres")
    @JsonIgnore
    private Set<Fraternidad> cis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCi() {
        return ci;
    }

    public Fraterno ci(Integer ci) {
        this.ci = ci;
        return this;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public String getNombreFraterno() {
        return nombreFraterno;
    }

    public Fraterno nombreFraterno(String nombreFraterno) {
        this.nombreFraterno = nombreFraterno;
        return this;
    }

    public void setNombreFraterno(String nombreFraterno) {
        this.nombreFraterno = nombreFraterno;
    }

    public Integer getRu() {
        return ru;
    }

    public Fraterno ru(Integer ru) {
        this.ru = ru;
        return this;
    }

    public void setRu(Integer ru) {
        this.ru = ru;
    }

    public Integer getItem() {
        return item;
    }

    public Fraterno item(Integer item) {
        this.item = item;
        return this;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Fraterno observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Set<Fraternidad> getCis() {
        return cis;
    }

    public Fraterno cis(Set<Fraternidad> fraternidads) {
        this.cis = fraternidads;
        return this;
    }

    public Fraterno addCi(Fraternidad fraternidad) {
        this.cis.add(fraternidad);
        fraternidad.getNombres().add(this);
        return this;
    }

    public Fraterno removeCi(Fraternidad fraternidad) {
        this.cis.remove(fraternidad);
        fraternidad.getNombres().remove(this);
        return this;
    }

    public void setCis(Set<Fraternidad> fraternidads) {
        this.cis = fraternidads;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fraterno)) {
            return false;
        }
        return id != null && id.equals(((Fraterno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fraterno{" +
            "id=" + getId() +
            ", ci=" + getCi() +
            ", nombreFraterno='" + getNombreFraterno() + "'" +
            ", ru=" + getRu() +
            ", item=" + getItem() +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}

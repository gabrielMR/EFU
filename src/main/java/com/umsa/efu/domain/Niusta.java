package com.umsa.efu.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Niusta.
 */
@Entity
@Table(name = "niusta")
public class Niusta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ci")
    private Integer ci;

    @Column(name = "nombre_niusta")
    private String nombreNiusta;

    @Column(name = "ru")
    private Integer ru;

    @Column(name = "item")
    private Integer item;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "gustos")
    private String gustos;

    @Column(name = "estatura")
    private Float estatura;

    @Column(name = "titulo")
    private String titulo;

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

    public Niusta ci(Integer ci) {
        this.ci = ci;
        return this;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public String getNombreNiusta() {
        return nombreNiusta;
    }

    public Niusta nombreNiusta(String nombreNiusta) {
        this.nombreNiusta = nombreNiusta;
        return this;
    }

    public void setNombreNiusta(String nombreNiusta) {
        this.nombreNiusta = nombreNiusta;
    }

    public Integer getRu() {
        return ru;
    }

    public Niusta ru(Integer ru) {
        this.ru = ru;
        return this;
    }

    public void setRu(Integer ru) {
        this.ru = ru;
    }

    public Integer getItem() {
        return item;
    }

    public Niusta item(Integer item) {
        this.item = item;
        return this;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Integer getEdad() {
        return edad;
    }

    public Niusta edad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getGustos() {
        return gustos;
    }

    public Niusta gustos(String gustos) {
        this.gustos = gustos;
        return this;
    }

    public void setGustos(String gustos) {
        this.gustos = gustos;
    }

    public Float getEstatura() {
        return estatura;
    }

    public Niusta estatura(Float estatura) {
        this.estatura = estatura;
        return this;
    }

    public void setEstatura(Float estatura) {
        this.estatura = estatura;
    }

    public String getTitulo() {
        return titulo;
    }

    public Niusta titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Niusta)) {
            return false;
        }
        return id != null && id.equals(((Niusta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Niusta{" +
            "id=" + getId() +
            ", ci=" + getCi() +
            ", nombreNiusta='" + getNombreNiusta() + "'" +
            ", ru=" + getRu() +
            ", item=" + getItem() +
            ", edad=" + getEdad() +
            ", gustos='" + getGustos() + "'" +
            ", estatura=" + getEstatura() +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}

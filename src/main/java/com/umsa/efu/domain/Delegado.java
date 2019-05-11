package com.umsa.efu.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Delegado.
 */
@Entity
@Table(name = "delegado")
public class Delegado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_delegado")
    private Integer idDelegado;

    @Column(name = "ci")
    private Integer ci;

    @Column(name = "nombre_delegado")
    private String nombreDelegado;

    @Column(name = "ru")
    private Integer ru;

    @Column(name = "item")
    private Integer item;

    @Column(name = "telefono")
    private Long telefono;

    @Column(name = "correo")
    private String correo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDelegado() {
        return idDelegado;
    }

    public Delegado idDelegado(Integer idDelegado) {
        this.idDelegado = idDelegado;
        return this;
    }

    public void setIdDelegado(Integer idDelegado) {
        this.idDelegado = idDelegado;
    }

    public Integer getCi() {
        return ci;
    }

    public Delegado ci(Integer ci) {
        this.ci = ci;
        return this;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public String getNombreDelegado() {
        return nombreDelegado;
    }

    public Delegado nombreDelegado(String nombreDelegado) {
        this.nombreDelegado = nombreDelegado;
        return this;
    }

    public void setNombreDelegado(String nombreDelegado) {
        this.nombreDelegado = nombreDelegado;
    }

    public Integer getRu() {
        return ru;
    }

    public Delegado ru(Integer ru) {
        this.ru = ru;
        return this;
    }

    public void setRu(Integer ru) {
        this.ru = ru;
    }

    public Integer getItem() {
        return item;
    }

    public Delegado item(Integer item) {
        this.item = item;
        return this;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Long getTelefono() {
        return telefono;
    }

    public Delegado telefono(Long telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public Delegado correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Delegado)) {
            return false;
        }
        return id != null && id.equals(((Delegado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Delegado{" +
            "id=" + getId() +
            ", idDelegado=" + getIdDelegado() +
            ", ci=" + getCi() +
            ", nombreDelegado='" + getNombreDelegado() + "'" +
            ", ru=" + getRu() +
            ", item=" + getItem() +
            ", telefono=" + getTelefono() +
            ", correo='" + getCorreo() + "'" +
            "}";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author viniciuspeixoto
 */
@Entity
@Table(name = "boletimDiario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BoletimDiario.findAll", query = "SELECT b FROM BoletimDiario b")
    , @NamedQuery(name = "BoletimDiario.findById", query = "SELECT b FROM BoletimDiario b WHERE b.id = :id")
    , @NamedQuery(name = "BoletimDiario.findByData", query = "SELECT b FROM BoletimDiario b WHERE b.data = :data")
    , @NamedQuery(name = "BoletimDiario.findByParecer", query = "SELECT b FROM BoletimDiario b WHERE b.parecer = :parecer")
    , @NamedQuery(name = "BoletimDiario.findByObservacoes", query = "SELECT b FROM BoletimDiario b WHERE b.observacoes = :observacoes")})
public class BoletimDiario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @Column(name = "parecer")
    private String parecer;
    @Column(name = "observacoes")
    private String observacoes;
    @JoinColumn(name = "tratadorRespons", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tratador tratadorRespons;
    @JoinColumn(name = "animal", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Animal animal;

    public BoletimDiario() {
    }

    public BoletimDiario(Integer id) {
        this.id = id;
    }

    public BoletimDiario(Integer id, Date data, String parecer) {
        this.id = id;
        this.data = data;
        this.parecer = parecer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getParecer() {
        return parecer;
    }

    public void setParecer(String parecer) {
        this.parecer = parecer;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Tratador getTratadorRespons() {
        return tratadorRespons;
    }

    public void setTratadorRespons(Tratador tratadorRespons) {
        this.tratadorRespons = tratadorRespons;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BoletimDiario)) {
            return false;
        }
        BoletimDiario other = (BoletimDiario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.BoletimDiario[ id=" + id + " ]";
    }
    
}

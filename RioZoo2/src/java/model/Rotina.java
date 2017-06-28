/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author viniciuspeixoto
 */
@Entity
@Table(name = "rotina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rotina.findAll", query = "SELECT r FROM Rotina r")
    , @NamedQuery(name = "Rotina.findById", query = "SELECT r FROM Rotina r WHERE r.id = :id")
    , @NamedQuery(name = "Rotina.findByDataValidade", query = "SELECT r FROM Rotina r WHERE r.dataValidade = :dataValidade")
    , @NamedQuery(name = "Rotina.findByTarefa1", query = "SELECT r FROM Rotina r WHERE r.tarefa1 = :tarefa1")
    , @NamedQuery(name = "Rotina.findByCodigo", query = "SELECT r FROM Rotina r WHERE r.codigo = :codigo")
    , @NamedQuery(name = "Rotina.findByTarefa2", query = "SELECT r FROM Rotina r WHERE r.tarefa2 = :tarefa2")
    , @NamedQuery(name = "Rotina.findByTarefa3", query = "SELECT r FROM Rotina r WHERE r.tarefa3 = :tarefa3")})
public class Rotina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "dataValidade")
    @Temporal(TemporalType.DATE)
    private Date dataValidade;
    @Column(name = "tarefa1")
    private String tarefa1;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "tarefa2")
    private String tarefa2;
    @Column(name = "tarefa3")
    private String tarefa3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rotina")
    private List<Animal> animalList;

    public Rotina() {
    }

    public Rotina(Integer id) {
        this.id = id;
    }

    public Rotina(Integer id, Date dataValidade, String codigo) {
        this.id = id;
        this.dataValidade = dataValidade;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getTarefa1() {
        return tarefa1;
    }

    public void setTarefa1(String tarefa1) {
        this.tarefa1 = tarefa1;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTarefa2() {
        return tarefa2;
    }

    public void setTarefa2(String tarefa2) {
        this.tarefa2 = tarefa2;
    }

    public String getTarefa3() {
        return tarefa3;
    }

    public void setTarefa3(String tarefa3) {
        this.tarefa3 = tarefa3;
    }

    @XmlTransient
    public List<Animal> getAnimalList() {
        return animalList;
    }

    public void setAnimalList(List<Animal> animalList) {
        this.animalList = animalList;
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
        if (!(object instanceof Rotina)) {
            return false;
        }
        Rotina other = (Rotina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Rotina[ id=" + id + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author viniciuspeixoto
 */
@Entity
@Table(name = "registroClinico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroClinico.findAll", query = "SELECT r FROM RegistroClinico r")
    , @NamedQuery(name = "RegistroClinico.findById", query = "SELECT r FROM RegistroClinico r WHERE r.id = :id")
    , @NamedQuery(name = "RegistroClinico.findByDiagnostico", query = "SELECT r FROM RegistroClinico r WHERE r.diagnostico = :diagnostico")
    , @NamedQuery(name = "RegistroClinico.findByObservacoes", query = "SELECT r FROM RegistroClinico r WHERE r.observacoes = :observacoes")})
public class RegistroClinico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "diagnostico")
    private String diagnostico;
    @Column(name = "observacoes")
    private String observacoes;
    @JoinColumn(name = "animal", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Animal animal;
    @JoinColumn(name = "vacina", referencedColumnName = "id")
    @ManyToOne
    private Vacina vacina;
    @JoinColumn(name = "veterinario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Veterinario veterinario;

    public RegistroClinico() {
    }

    public RegistroClinico(Integer id) {
        this.id = id;
    }

    public RegistroClinico(Integer id, String diagnostico) {
        this.id = id;
        this.diagnostico = diagnostico;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
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
        if (!(object instanceof RegistroClinico)) {
            return false;
        }
        RegistroClinico other = (RegistroClinico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RegistroClinico[ id=" + id + " ]";
    }
    
}

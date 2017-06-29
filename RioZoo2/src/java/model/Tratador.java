/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author viniciuspeixoto
 */
@Entity
@Table(name = "tratador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tratador.findAll", query = "SELECT t FROM Tratador t")
    , @NamedQuery(name = "Tratador.findById", query = "SELECT t FROM Tratador t WHERE t.id = :id")
    , @NamedQuery(name = "Tratador.findByNome", query = "SELECT t FROM Tratador t WHERE t.nome = :nome")
    , @NamedQuery(name = "Tratador.findByMatricula", query = "SELECT t FROM Tratador t WHERE t.matricula = :matricula")})
public class Tratador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "matricula")
    private int matricula;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tratadorRespons")
    private List<BoletimDiario> boletimDiarioList;
    @JoinColumn(name = "equipe", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Equipe equipe;
    @JoinColumn(name = "endereco", referencedColumnName = "id")
    @ManyToOne
    private Endereco endereco;
    @OneToMany(mappedBy = "tratador")
    private List<TratadorAnimal> tratadorAnimalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tratadorResponsavel")
    private List<Animal> animalList;

    public Tratador() {
        this.endereco = new Endereco();
    }

    public Tratador(Integer id) {
        this.id = id;
    }

    public Tratador(Integer id, int matricula) {
        this.id = id;
        this.matricula = matricula;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    @XmlTransient
    public List<BoletimDiario> getBoletimDiarioList() {
        return boletimDiarioList;
    }

    public void setBoletimDiarioList(List<BoletimDiario> boletimDiarioList) {
        this.boletimDiarioList = boletimDiarioList;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @XmlTransient
    public List<TratadorAnimal> getTratadorAnimalList() {
        return tratadorAnimalList;
    }

    public void setTratadorAnimalList(List<TratadorAnimal> tratadorAnimalList) {
        this.tratadorAnimalList = tratadorAnimalList;
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
        if (!(object instanceof Tratador)) {
            return false;
        }
        Tratador other = (Tratador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Tratador[ id=" + id + " ]";
    }
    
}

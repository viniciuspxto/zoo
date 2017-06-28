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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "veterinario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Veterinario.findAll", query = "SELECT v FROM Veterinario v")
    , @NamedQuery(name = "Veterinario.findByCrmv", query = "SELECT v FROM Veterinario v WHERE v.crmv = :crmv")
    , @NamedQuery(name = "Veterinario.findByDataEmissao", query = "SELECT v FROM Veterinario v WHERE v.dataEmissao = :dataEmissao")
    , @NamedQuery(name = "Veterinario.findById", query = "SELECT v FROM Veterinario v WHERE v.id = :id")
    , @NamedQuery(name = "Veterinario.findByNome", query = "SELECT v FROM Veterinario v WHERE v.nome = :nome")
    , @NamedQuery(name = "Veterinario.findByMatricula", query = "SELECT v FROM Veterinario v WHERE v.matricula = :matricula")})
public class Veterinario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "crmv")
    private int crmv;
    @Basic(optional = false)
    @Column(name = "dataEmissao")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "matricula")
    private int matricula;
    @JoinColumn(name = "endereco", referencedColumnName = "id")
    @ManyToOne
    private Endereco endereco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veterinario")
    private List<RegistroClinico> registroClinicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veterinario")
    private List<Consulta> consultaList;

    public Veterinario() {
    }

    public Veterinario(Integer id) {
        this.id = id;
    }

    public Veterinario(Integer id, int crmv, Date dataEmissao, int matricula) {
        this.id = id;
        this.crmv = crmv;
        this.dataEmissao = dataEmissao;
        this.matricula = matricula;
    }

    public int getCrmv() {
        return crmv;
    }

    public void setCrmv(int crmv) {
        this.crmv = crmv;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @XmlTransient
    public List<RegistroClinico> getRegistroClinicoList() {
        return registroClinicoList;
    }

    public void setRegistroClinicoList(List<RegistroClinico> registroClinicoList) {
        this.registroClinicoList = registroClinicoList;
    }

    @XmlTransient
    public List<Consulta> getConsultaList() {
        return consultaList;
    }

    public void setConsultaList(List<Consulta> consultaList) {
        this.consultaList = consultaList;
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
        if (!(object instanceof Veterinario)) {
            return false;
        }
        Veterinario other = (Veterinario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Veterinario[ id=" + id + " ]";
    }
    
}

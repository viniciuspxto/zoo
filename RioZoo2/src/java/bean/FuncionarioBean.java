/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.EquipeJpaController;
import dao.TratadorJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import model.Administrador;
import model.Equipe;
import model.Tratador;
import model.Veterinario;
import utils.JpaUtils;

@ManagedBean(name = "funcionarioBean")
@ViewScoped
public class FuncionarioBean {
   
    
    private Tratador tratador;
    private Veterinario veterinario;
    private Administrador administrador;
    
    private String selecionado;
    
    private List<Equipe> equipes;
    
    private List<Tratador> tratadores;
    
    private EquipeJpaController equipeJpaController;
    
    private TratadorJpaController tratadorJpaController;
    
    @PostConstruct
    public void init() {
        this.tratador = new Tratador();
        this.administrador = new Administrador();
        this.veterinario = new Veterinario();
        this.equipeJpaController = new EquipeJpaController(JpaUtils.emf);
        this.equipes = equipeJpaController.findEquipeEntities();
        this.tratadorJpaController = new TratadorJpaController(JpaUtils.emf);
        this.tratadores = tratadorJpaController.findTratadorEntities();
    }
    
    public void salvarVeterinario() {
        getVeterinario();
    }
    
    public void salvarAdministrador() {
        getAdministrador();
    }
    
    public void salvarTratador() {
        getTratador();
    }
    
    public void excluir() {
       
    }
    
    
    
    
    public String manterFuncionario () {
        return "/pages/manter-funcionario";
    }

    public String getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(String selecionado) {
        this.selecionado = selecionado;
    }

    public Tratador getTratador() {
        return tratador;
    }

    public void setTratador(Tratador tratador) {
        this.tratador = tratador;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }

    public List<Tratador> getTratadores() {
        return tratadores;
    }

    public void setTratadores(List<Tratador> tratadores) {
        this.tratadores = tratadores;
    }
    
    


    
    
    
}

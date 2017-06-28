/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.EquipeJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Equipe;
import utils.JpaUtils;

@ManagedBean(name = "equipeBean")
@ViewScoped
public class EquipeBean {
    
    private EquipeJpaController equipeJpaController;
    
    private Equipe equipe;
    
    private List<Equipe> equipes;
    
    @PostConstruct
    public void init() {
        equipeJpaController = new EquipeJpaController(JpaUtils.emf);
        this.equipe = new Equipe();
        equipes = equipeJpaController.findEquipeEntities();
    }
    
    public void salvar(){
        equipeJpaController.create(equipe);
        recarregarEquipe();
        recarregarEquipes();
    }
    
    public void excluir(Equipe equipe) {
        try {
            equipeJpaController.destroy(equipe.getId());
            recarregarEquipes();
        } catch (Exception ex) {
            Logger.getLogger(EquipeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recarregarEquipes(){
        this.equipes = this.equipeJpaController.findEquipeEntities();
    }
    
    public void recarregarEquipe(){
        this.equipe = new Equipe();
    }
    
    public String manterEquipe () {
        return "/pages/manter-equipe";
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
    
    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }
    
    
}

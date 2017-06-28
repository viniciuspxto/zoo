/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.RotinaJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Rotina;
import utils.JpaUtils;

@ManagedBean(name = "rotinaBean")
@ViewScoped
public class RotinaBean {
    
    private RotinaJpaController rotinaJpaController;
    
    private Rotina rotina;
    
    private List<Rotina> rotinas;
    
    @PostConstruct
    public void init() {
        rotinaJpaController = new RotinaJpaController(JpaUtils.emf);
        this.rotina = new Rotina();
        rotinas = rotinaJpaController.findRotinaEntities();
    }
    
    public void salvar() {
        rotinaJpaController.create(rotina);
        recarregarRotina();
        recarregarRotinas();
    }
    
    public void excluir(Rotina rotina) {
        try {
            rotinaJpaController.destroy(rotina.getId());
            recarregarRotinas();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(RotinaBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RotinaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recarregarRotina() {
        this.rotina = new Rotina();
    }
    
    public void recarregarRotinas() {
        this.rotinas = rotinaJpaController.findRotinaEntities();
    }
    
    
    public String manterRotina () {
        return "/pages/manter-rotina";
    }

    public Rotina getRotina() {
        return rotina;
    }

    public void setRotina(Rotina rotina) {
        this.rotina = rotina;
    }

    public List<Rotina> getRotinas() {
        return rotinas;
    }

    public void setRotinas(List<Rotina> rotinas) {
        this.rotinas = rotinas;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.VacinaJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Vacina;
import utils.JpaUtils;

@ManagedBean(name = "vacinaBean")
@ViewScoped
public class VacinaBean {
    
    private VacinaJpaController vacinaJpaController;
    
    
    private Vacina vacina;
    
    private int quantVacinas;
    
    private List<Vacina> listavacinas;
    
    @PostConstruct
    public void init() {
        vacinaJpaController = new VacinaJpaController(JpaUtils.emf);
        this.vacina = new Vacina();
        listavacinas = vacinaJpaController.findVacinaEntities();
    }
    
    public void salvar() {
        vacinaJpaController.create(vacina);
        recarregarVacina();
        recarregarVacinas();
    }
    
    public void excluir(Vacina vacina) {
        try {
            vacinaJpaController.destroy(vacina.getId());
            recarregarVacinas();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(VacinaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recarregarVacina() {
        this.vacina = new Vacina();
    }
    
    public void recarregarVacinas() {
        this.listavacinas = this.vacinaJpaController.findVacinaEntities();
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public int getQuantVacinas() {
        return quantVacinas;
    }

    public void setQuantVacinas(int quantVacinas) {
        this.quantVacinas = quantVacinas;
    }

    public List<Vacina> getListavacinas() {
        return listavacinas;
    }

    public void setListavacinas(List<Vacina> listavacinas) {
        this.listavacinas = listavacinas;
    }
    
    public String manterVacina () {
        return "/pages/manter-vacina";
    }
}

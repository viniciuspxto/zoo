/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.MedicamentoJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Medicamento;
import utils.JpaUtils;

@ManagedBean(name = "medicamentoBean")
@ViewScoped
public class MedicamentoBean {
    
    private MedicamentoJpaController medicamentoJpaController;
    
    private Medicamento medicamento;
    
    private List<Medicamento> medicamentos;
    
    @PostConstruct
    public void init() {
        medicamentoJpaController = new MedicamentoJpaController(JpaUtils.emf);
        this.medicamento = new Medicamento();
        medicamentos = medicamentoJpaController.findMedicamentoEntities();
    }
    
    public void salvar() {
        medicamentoJpaController.create(medicamento);
        recarregarMedicamento();
        recarregarMedicamentos();
    }
    
    public void excluir(Medicamento medicamento) {
        try {
            medicamentoJpaController.destroy(medicamento.getId());
            recarregarMedicamentos();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MedicamentoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recarregarMedicamento() {
        this.medicamento = new Medicamento();
    }
    
    public void recarregarMedicamentos() {
        this.medicamentos = medicamentoJpaController.findMedicamentoEntities();
    }
    
    public String manterMedicamento () {
        return "/pages/manter-medicamento";
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }
    
    
}

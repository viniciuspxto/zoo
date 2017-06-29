/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.AnimalJpaController;
import dao.TratadorJpaController;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Animal;
import model.Tratador;
import utils.JpaUtils;

@ManagedBean(name = "animalBean")
@ViewScoped
public class AnimalBean {
    
    private AnimalJpaController animalJpaController;
    
    private Animal animal;
    
    private List<Animal> animais;
    
    private List<Tratador> tratadores;
    
    private TratadorJpaController tratadorJpaController;
    
    
    @PostConstruct
    private void init(){
        this.animal = new Animal();
        animalJpaController = new AnimalJpaController(JpaUtils.emf);
        this.animais = animalJpaController.findAnimalEntities();
        this.tratadorJpaController = new TratadorJpaController(JpaUtils.emf);
        this.tratadores = tratadorJpaController.findTratadorEntities();
    }
    
    public void salvar(){
        animalJpaController.create(animal);
    }
    
    public String manterAnimal () {
        return "/pages/manter-animal";
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public List<Animal> getAnimais() {
        return animais;
    }

    public void setAnimais(List<Animal> animais) {
        this.animais = animais;
    }
    
    
    
    
}

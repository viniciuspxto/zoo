package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Animal;
import model.BoletimDiario;
import model.Endereco;
import model.Equipe;
import model.TratadorAnimal;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-27T21:13:50")
@StaticMetamodel(Tratador.class)
public class Tratador_ { 

    public static volatile ListAttribute<Tratador, BoletimDiario> boletimDiarioList;
    public static volatile ListAttribute<Tratador, Animal> animalList;
    public static volatile ListAttribute<Tratador, TratadorAnimal> tratadorAnimalList;
    public static volatile SingularAttribute<Tratador, Endereco> endereco;
    public static volatile SingularAttribute<Tratador, Integer> matricula;
    public static volatile SingularAttribute<Tratador, String> nome;
    public static volatile SingularAttribute<Tratador, Integer> id;
    public static volatile SingularAttribute<Tratador, Equipe> equipe;

}
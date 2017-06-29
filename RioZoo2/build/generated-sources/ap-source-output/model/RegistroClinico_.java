package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Animal;
import model.Vacina;
import model.Veterinario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-28T20:10:36")
@StaticMetamodel(RegistroClinico.class)
public class RegistroClinico_ { 

    public static volatile SingularAttribute<RegistroClinico, String> observacoes;
    public static volatile SingularAttribute<RegistroClinico, Veterinario> veterinario;
    public static volatile SingularAttribute<RegistroClinico, Vacina> vacina;
    public static volatile SingularAttribute<RegistroClinico, String> diagnostico;
    public static volatile SingularAttribute<RegistroClinico, Animal> animal;
    public static volatile SingularAttribute<RegistroClinico, Integer> id;

}
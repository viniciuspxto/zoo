package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.BoletimDiario;
import model.Consulta;
import model.RegistroClinico;
import model.Rotina;
import model.Tratador;
import model.TratadorAnimal;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-27T21:13:50")
@StaticMetamodel(Animal.class)
public class Animal_ { 

    public static volatile ListAttribute<Animal, BoletimDiario> boletimDiarioList;
    public static volatile SingularAttribute<Animal, String> codigo;
    public static volatile ListAttribute<Animal, RegistroClinico> registroClinicoList;
    public static volatile ListAttribute<Animal, TratadorAnimal> tratadorAnimalList;
    public static volatile SingularAttribute<Animal, Float> peso;
    public static volatile SingularAttribute<Animal, String> origem;
    public static volatile SingularAttribute<Animal, String> nome;
    public static volatile SingularAttribute<Animal, String> especie;
    public static volatile SingularAttribute<Animal, Tratador> tratadorResponsavel;
    public static volatile ListAttribute<Animal, Consulta> consultaList;
    public static volatile SingularAttribute<Animal, Integer> id;
    public static volatile SingularAttribute<Animal, Date> dataNascimento;
    public static volatile SingularAttribute<Animal, Character> sexo;
    public static volatile SingularAttribute<Animal, Rotina> rotina;

}
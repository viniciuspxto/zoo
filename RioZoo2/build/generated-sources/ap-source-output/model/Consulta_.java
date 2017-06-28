package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Animal;
import model.Veterinario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-27T21:13:50")
@StaticMetamodel(Consulta.class)
public class Consulta_ { 

    public static volatile SingularAttribute<Consulta, Veterinario> veterinario;
    public static volatile SingularAttribute<Consulta, Animal> animal;
    public static volatile SingularAttribute<Consulta, Date> dataAgendamento;
    public static volatile SingularAttribute<Consulta, Date> dataConsulta;
    public static volatile SingularAttribute<Consulta, Integer> id;

}
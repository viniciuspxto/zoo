package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Animal;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-28T20:10:36")
@StaticMetamodel(Rotina.class)
public class Rotina_ { 

    public static volatile ListAttribute<Rotina, Animal> animalList;
    public static volatile SingularAttribute<Rotina, String> codigo;
    public static volatile SingularAttribute<Rotina, Date> dataValidade;
    public static volatile SingularAttribute<Rotina, String> tarefa3;
    public static volatile SingularAttribute<Rotina, String> tarefa1;
    public static volatile SingularAttribute<Rotina, String> tarefa2;
    public static volatile SingularAttribute<Rotina, Integer> id;

}
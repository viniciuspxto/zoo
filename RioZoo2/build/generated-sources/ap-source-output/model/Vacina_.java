package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.RegistroClinico;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-28T20:10:36")
@StaticMetamodel(Vacina.class)
public class Vacina_ { 

    public static volatile SingularAttribute<Vacina, String> codigo;
    public static volatile ListAttribute<Vacina, RegistroClinico> registroClinicoList;
    public static volatile SingularAttribute<Vacina, String> nome;
    public static volatile SingularAttribute<Vacina, Integer> id;
    public static volatile SingularAttribute<Vacina, Integer> quantidade;

}
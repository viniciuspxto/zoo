package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Animal;
import model.Tratador;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-28T20:10:36")
@StaticMetamodel(BoletimDiario.class)
public class BoletimDiario_ { 

    public static volatile SingularAttribute<BoletimDiario, String> observacoes;
    public static volatile SingularAttribute<BoletimDiario, Tratador> tratadorRespons;
    public static volatile SingularAttribute<BoletimDiario, Date> data;
    public static volatile SingularAttribute<BoletimDiario, String> parecer;
    public static volatile SingularAttribute<BoletimDiario, Animal> animal;
    public static volatile SingularAttribute<BoletimDiario, Integer> id;

}
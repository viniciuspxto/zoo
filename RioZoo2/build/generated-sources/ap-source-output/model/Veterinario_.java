package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Consulta;
import model.Endereco;
import model.RegistroClinico;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-28T20:10:36")
@StaticMetamodel(Veterinario.class)
public class Veterinario_ { 

    public static volatile ListAttribute<Veterinario, RegistroClinico> registroClinicoList;
    public static volatile ListAttribute<Veterinario, Consulta> consultaList;
    public static volatile SingularAttribute<Veterinario, Endereco> endereco;
    public static volatile SingularAttribute<Veterinario, Integer> crmv;
    public static volatile SingularAttribute<Veterinario, Integer> matricula;
    public static volatile SingularAttribute<Veterinario, String> nome;
    public static volatile SingularAttribute<Veterinario, Integer> id;
    public static volatile SingularAttribute<Veterinario, Date> dataEmissao;

}
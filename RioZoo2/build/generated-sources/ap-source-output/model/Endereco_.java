package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Administrador;
import model.Tratador;
import model.Veterinario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-28T20:10:36")
@StaticMetamodel(Endereco.class)
public class Endereco_ { 

    public static volatile ListAttribute<Endereco, Veterinario> veterinarioList;
    public static volatile SingularAttribute<Endereco, String> uf;
    public static volatile SingularAttribute<Endereco, String> cidade;
    public static volatile SingularAttribute<Endereco, String> telefone;
    public static volatile ListAttribute<Endereco, Administrador> administradorList;
    public static volatile SingularAttribute<Endereco, String> complemento;
    public static volatile SingularAttribute<Endereco, String> numero;
    public static volatile SingularAttribute<Endereco, String> bairro;
    public static volatile SingularAttribute<Endereco, String> logradouro;
    public static volatile ListAttribute<Endereco, Tratador> tratadorList;
    public static volatile SingularAttribute<Endereco, Integer> id;
    public static volatile SingularAttribute<Endereco, String> cep;

}
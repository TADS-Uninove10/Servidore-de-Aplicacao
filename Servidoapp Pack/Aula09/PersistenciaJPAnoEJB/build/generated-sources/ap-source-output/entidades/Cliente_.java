package entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-09-22T11:08:17")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, Long> id;
    public static volatile SingularAttribute<Cliente, Integer> CEP;
    public static volatile SingularAttribute<Cliente, Integer> telefone;
    public static volatile SingularAttribute<Cliente, String> nome;
    public static volatile SingularAttribute<Cliente, String> endereco;
    public static volatile SingularAttribute<Cliente, Date> dataCadastramento;

}
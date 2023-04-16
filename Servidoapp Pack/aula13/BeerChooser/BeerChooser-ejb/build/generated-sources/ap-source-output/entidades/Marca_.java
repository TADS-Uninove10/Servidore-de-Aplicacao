package entidades;

import entidades.Vendedor;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-10-13T11:24:31")
@StaticMetamodel(Marca.class)
public class Marca_ { 

    public static volatile SingularAttribute<Marca, Long> id;
    public static volatile SingularAttribute<Marca, String> fermentacao;
    public static volatile SingularAttribute<Marca, Vendedor> vendedor;
    public static volatile SingularAttribute<Marca, String> cor;
    public static volatile SingularAttribute<Marca, String> marca;
    public static volatile SingularAttribute<Marca, String> fabricante;

}
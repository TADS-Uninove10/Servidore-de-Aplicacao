package entidades;

import entidades.Marca;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-10-13T11:24:31")
@StaticMetamodel(Vendedor.class)
public class Vendedor_ { 

    public static volatile SingularAttribute<Vendedor, Long> id;
    public static volatile SingularAttribute<Vendedor, String> nome;
    public static volatile CollectionAttribute<Vendedor, Marca> marcaCollection;

}
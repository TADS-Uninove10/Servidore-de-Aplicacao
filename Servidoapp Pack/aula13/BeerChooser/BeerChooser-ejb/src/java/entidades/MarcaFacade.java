/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrador
 */
@Stateless
public class MarcaFacade extends AbstractFacade<Marca> {
    @PersistenceContext(unitName = "BeerChooser-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MarcaFacade() {
        super(Marca.class);
    }
    
}

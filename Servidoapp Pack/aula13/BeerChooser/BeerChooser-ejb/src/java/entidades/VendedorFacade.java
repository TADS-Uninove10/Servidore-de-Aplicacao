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
public class VendedorFacade extends AbstractFacade<Vendedor> {
    @PersistenceContext(unitName = "BeerChooser-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public VendedorFacade() {
        super(Vendedor.class);
    }
    
}

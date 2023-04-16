/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import entidades.Cliente;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 *
 * @author Administrador
 */
@Stateless
@LocalBean
public class BeanCliente implements BeanClienteRemote {
    @PersistenceContext(name="PersistenciaJPAnoEJBPU2")
    EntityManager em;
    
    @Override
    public void cadastrarCliente(String nome, String endereco,
            Integer telefone,Integer CEP,Date dataCadastramento){
        
        Cliente cli = new Cliente();
        cli.setNome(nome);
        cli.setEndereco(endereco);
        cli.setTelefone(telefone);
        cli.setCEP(CEP);
        cli.setDataCadastramento(dataCadastramento);
        em.persist(cli);

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
     }
}

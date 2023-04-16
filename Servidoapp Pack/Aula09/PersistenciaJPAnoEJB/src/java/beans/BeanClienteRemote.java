/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;
import javax.ejb.Remote;

/**
 *
 * @author Administrador
 */
@Remote
public interface BeanClienteRemote {
    public void cadastrarCliente(String nome, String endereco, Integer telefone,
            Integer CEP, Date dataCadastramento);
    
}

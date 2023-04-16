/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testapersistencia;
import beans.BeanClienteRemote;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Administrador
 */
public class Main {
    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("jndi.properties"));
            InitialContext ctx = new InitialContext(props);
            BeanClienteRemote testeEJB = (BeanClienteRemote) ctx.lookup("beans.BeanClienteRemote");
            testeEJB.cadastrarCliente("Anselmo","R. dos Arapanés, 135", 8675389, 4521000, new Date(System.currentTimeMillis()));
            System.out.println("Cadastrou Anselmo");
            testeEJB.cadastrarCliente("Duarte","R. dos Apinajés, 35", 887679, 4590700, new Date(System.currentTimeMillis()));
            System.out.println("Cadastrou Duarte");
            testeEJB.cadastrarCliente("Maria","R. da Fofoca, 536", 93848592, 4520900, new Date(System.currentTimeMillis()));
            System.out.println("Cadastrou Maria");
            testeEJB.cadastrarCliente("Roberta","R. do Barulho, 333", 1111111, 9888800, new Date(System.currentTimeMillis()));
            System.out.println("Cadastrou Roberta");
            testeEJB.cadastrarCliente("Marcos","R. dos ?????, 123", 8111389, 4989800, new Date(System.currentTimeMillis()));
            System.out.println("Cadastrou Marcos");
            testeEJB.cadastrarCliente("Aurélia","R. Aurélia, S/N", 8643219, 4512000, new Date(System.currentTimeMillis()));
            System.out.println("Cadastrou Anselmo");
        }catch (NamingException nex) {
            nex.printStackTrace();
        } catch (FileNotFoundException fnfex) {
            fnfex.printStackTrace();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
    

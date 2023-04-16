/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import entidade.exceptions.NonexistentEntityException;
import entidade.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Video video = cliente.getVideo();
            if (video != null) {
                video = em.getReference(video.getClass(), video.getId());
                cliente.setVideo(video);
            }
            em.persist(cliente);
            if (video != null) {
                video.getClientes().add(cliente);
                video = em.merge(video);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            Video videoOld = persistentCliente.getVideo();
            Video videoNew = cliente.getVideo();
            if (videoNew != null) {
                videoNew = em.getReference(videoNew.getClass(), videoNew.getId());
                cliente.setVideo(videoNew);
            }
            cliente = em.merge(cliente);
            if (videoOld != null && !videoOld.equals(videoNew)) {
                videoOld.getClientes().remove(cliente);
                videoOld = em.merge(videoOld);
            }
            if (videoNew != null && !videoNew.equals(videoOld)) {
                videoNew.getClientes().add(cliente);
                videoNew = em.merge(videoNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Video video = cliente.getVideo();
            if (video != null) {
                video.getClientes().remove(cliente);
                video = em.merge(video);
            }
            em.remove(cliente);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Cliente as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Cliente as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

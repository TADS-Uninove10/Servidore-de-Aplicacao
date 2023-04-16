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
import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class VideoJpaController implements Serializable {

    public VideoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Video video) throws RollbackFailureException, Exception {
        if (video.getClientes() == null) {
            video.setClientes(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Cliente> attachedClientes = new ArrayList<Cliente>();
            for (Cliente clientesClienteToAttach : video.getClientes()) {
                clientesClienteToAttach = em.getReference(clientesClienteToAttach.getClass(), clientesClienteToAttach.getId());
                attachedClientes.add(clientesClienteToAttach);
            }
            video.setClientes(attachedClientes);
            em.persist(video);
            for (Cliente clientesCliente : video.getClientes()) {
                Video oldVideoOfClientesCliente = clientesCliente.getVideo();
                clientesCliente.setVideo(video);
                clientesCliente = em.merge(clientesCliente);
                if (oldVideoOfClientesCliente != null) {
                    oldVideoOfClientesCliente.getClientes().remove(clientesCliente);
                    oldVideoOfClientesCliente = em.merge(oldVideoOfClientesCliente);
                }
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

    public void edit(Video video) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Video persistentVideo = em.find(Video.class, video.getId());
            Collection<Cliente> clientesOld = persistentVideo.getClientes();
            Collection<Cliente> clientesNew = video.getClientes();
            Collection<Cliente> attachedClientesNew = new ArrayList<Cliente>();
            for (Cliente clientesNewClienteToAttach : clientesNew) {
                clientesNewClienteToAttach = em.getReference(clientesNewClienteToAttach.getClass(), clientesNewClienteToAttach.getId());
                attachedClientesNew.add(clientesNewClienteToAttach);
            }
            clientesNew = attachedClientesNew;
            video.setClientes(clientesNew);
            video = em.merge(video);
            for (Cliente clientesOldCliente : clientesOld) {
                if (!clientesNew.contains(clientesOldCliente)) {
                    clientesOldCliente.setVideo(null);
                    clientesOldCliente = em.merge(clientesOldCliente);
                }
            }
            for (Cliente clientesNewCliente : clientesNew) {
                if (!clientesOld.contains(clientesNewCliente)) {
                    Video oldVideoOfClientesNewCliente = clientesNewCliente.getVideo();
                    clientesNewCliente.setVideo(video);
                    clientesNewCliente = em.merge(clientesNewCliente);
                    if (oldVideoOfClientesNewCliente != null && !oldVideoOfClientesNewCliente.equals(video)) {
                        oldVideoOfClientesNewCliente.getClientes().remove(clientesNewCliente);
                        oldVideoOfClientesNewCliente = em.merge(oldVideoOfClientesNewCliente);
                    }
                }
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
                Long id = video.getId();
                if (findVideo(id) == null) {
                    throw new NonexistentEntityException("The video with id " + id + " no longer exists.");
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
            Video video;
            try {
                video = em.getReference(Video.class, id);
                video.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The video with id " + id + " no longer exists.", enfe);
            }
            Collection<Cliente> clientes = video.getClientes();
            for (Cliente clientesCliente : clientes) {
                clientesCliente.setVideo(null);
                clientesCliente = em.merge(clientesCliente);
            }
            em.remove(video);
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

    public List<Video> findVideoEntities() {
        return findVideoEntities(true, -1, -1);
    }

    public List<Video> findVideoEntities(int maxResults, int firstResult) {
        return findVideoEntities(false, maxResults, firstResult);
    }

    private List<Video> findVideoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Video as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Video findVideo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Video.class, id);
        } finally {
            em.close();
        }
    }

    public int getVideoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Video as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

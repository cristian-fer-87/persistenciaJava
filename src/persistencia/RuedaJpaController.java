/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Rueda;
import modelo.Vehiculo;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author cristian
 */
public class RuedaJpaController implements Serializable {

    public RuedaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rueda rueda) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculo vehiculo = rueda.getVehiculo();
            if (vehiculo != null) {
                vehiculo = em.getReference(vehiculo.getClass(), vehiculo.getCodigo());
                rueda.setVehiculo(vehiculo);
            }
            em.persist(rueda);
            if (vehiculo != null) {
                vehiculo.getRuedas().add(rueda);
                vehiculo = em.merge(vehiculo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rueda rueda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rueda persistentRueda = em.find(Rueda.class, rueda.getCodigo());
            Vehiculo vehiculoOld = persistentRueda.getVehiculo();
            Vehiculo vehiculoNew = rueda.getVehiculo();
            if (vehiculoNew != null) {
                vehiculoNew = em.getReference(vehiculoNew.getClass(), vehiculoNew.getCodigo());
                rueda.setVehiculo(vehiculoNew);
            }
            rueda = em.merge(rueda);
            if (vehiculoOld != null && !vehiculoOld.equals(vehiculoNew)) {
                vehiculoOld.getRuedas().remove(rueda);
                vehiculoOld = em.merge(vehiculoOld);
            }
            if (vehiculoNew != null && !vehiculoNew.equals(vehiculoOld)) {
                vehiculoNew.getRuedas().add(rueda);
                vehiculoNew = em.merge(vehiculoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = rueda.getCodigo();
                if (findRueda(id) == null) {
                    throw new NonexistentEntityException("The rueda with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rueda rueda;
            try {
                rueda = em.getReference(Rueda.class, id);
                rueda.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rueda with id " + id + " no longer exists.", enfe);
            }
            Vehiculo vehiculo = rueda.getVehiculo();
            if (vehiculo != null) {
                vehiculo.getRuedas().remove(rueda);
                vehiculo = em.merge(vehiculo);
            }
            em.remove(rueda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rueda> findRuedaEntities() {
        return findRuedaEntities(true, -1, -1);
    }

    public List<Rueda> findRuedaEntities(int maxResults, int firstResult) {
        return findRuedaEntities(false, maxResults, firstResult);
    }

    private List<Rueda> findRuedaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rueda.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rueda findRueda(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rueda.class, id);
        } finally {
            em.close();
        }
    }

    public int getRuedaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rueda> rt = cq.from(Rueda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

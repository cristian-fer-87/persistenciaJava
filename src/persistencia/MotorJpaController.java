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
import modelo.Motor;
import modelo.Vehiculo;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author cristian
 */
public class MotorJpaController implements Serializable {

    public MotorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Motor motor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculo vehiculo = motor.getVehiculo();
            if (vehiculo != null) {
                vehiculo = em.getReference(vehiculo.getClass(), vehiculo.getCodigo());
                motor.setVehiculo(vehiculo);
            }
            em.persist(motor);
            if (vehiculo != null) {
                Motor oldMotorOfVehiculo = vehiculo.getMotor();
                if (oldMotorOfVehiculo != null) {
                    oldMotorOfVehiculo.setVehiculo(null);
                    oldMotorOfVehiculo = em.merge(oldMotorOfVehiculo);
                }
                vehiculo.setMotor(motor);
                vehiculo = em.merge(vehiculo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Motor motor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Motor persistentMotor = em.find(Motor.class, motor.getCodigo());
            Vehiculo vehiculoOld = persistentMotor.getVehiculo();
            Vehiculo vehiculoNew = motor.getVehiculo();
            if (vehiculoNew != null) {
                vehiculoNew = em.getReference(vehiculoNew.getClass(), vehiculoNew.getCodigo());
                motor.setVehiculo(vehiculoNew);
            }
            motor = em.merge(motor);
            if (vehiculoOld != null && !vehiculoOld.equals(vehiculoNew)) {
                vehiculoOld.setMotor(null);
                vehiculoOld = em.merge(vehiculoOld);
            }
            if (vehiculoNew != null && !vehiculoNew.equals(vehiculoOld)) {
                Motor oldMotorOfVehiculo = vehiculoNew.getMotor();
                if (oldMotorOfVehiculo != null) {
                    oldMotorOfVehiculo.setVehiculo(null);
                    oldMotorOfVehiculo = em.merge(oldMotorOfVehiculo);
                }
                vehiculoNew.setMotor(motor);
                vehiculoNew = em.merge(vehiculoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = motor.getCodigo();
                if (findMotor(id) == null) {
                    throw new NonexistentEntityException("The motor with id " + id + " no longer exists.");
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
            Motor motor;
            try {
                motor = em.getReference(Motor.class, id);
                motor.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The motor with id " + id + " no longer exists.", enfe);
            }
            Vehiculo vehiculo = motor.getVehiculo();
            if (vehiculo != null) {
                vehiculo.setMotor(null);
                vehiculo = em.merge(vehiculo);
            }
            em.remove(motor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Motor> findMotorEntities() {
        return findMotorEntities(true, -1, -1);
    }

    public List<Motor> findMotorEntities(int maxResults, int firstResult) {
        return findMotorEntities(false, maxResults, firstResult);
    }

    private List<Motor> findMotorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Motor.class));
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

    public Motor findMotor(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Motor.class, id);
        } finally {
            em.close();
        }
    }

    public int getMotorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Motor> rt = cq.from(Motor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

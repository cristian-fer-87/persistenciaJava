/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Motor;
import modelo.Rueda;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Vehiculo;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author cristian
 */
public class VehiculoJpaController implements Serializable {

    public VehiculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehiculo vehiculo) {
        if (vehiculo.getRuedas() == null) {
            vehiculo.setRuedas(new ArrayList<Rueda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Motor motor = vehiculo.getMotor();
            if (motor != null) {
                motor = em.getReference(motor.getClass(), motor.getCodigo());
                vehiculo.setMotor(motor);
            }
            List<Rueda> attachedRuedas = new ArrayList<Rueda>();
            for (Rueda ruedasRuedaToAttach : vehiculo.getRuedas()) {
                ruedasRuedaToAttach = em.getReference(ruedasRuedaToAttach.getClass(), ruedasRuedaToAttach.getCodigo());
                attachedRuedas.add(ruedasRuedaToAttach);
            }
            vehiculo.setRuedas(attachedRuedas);
            em.persist(vehiculo);
            if (motor != null) {
                Vehiculo oldVehiculoOfMotor = motor.getVehiculo();
                if (oldVehiculoOfMotor != null) {
                    oldVehiculoOfMotor.setMotor(null);
                    oldVehiculoOfMotor = em.merge(oldVehiculoOfMotor);
                }
                motor.setVehiculo(vehiculo);
                motor = em.merge(motor);
            }
            for (Rueda ruedasRueda : vehiculo.getRuedas()) {
                Vehiculo oldVehiculoOfRuedasRueda = ruedasRueda.getVehiculo();
                ruedasRueda.setVehiculo(vehiculo);
                ruedasRueda = em.merge(ruedasRueda);
                if (oldVehiculoOfRuedasRueda != null) {
                    oldVehiculoOfRuedasRueda.getRuedas().remove(ruedasRueda);
                    oldVehiculoOfRuedasRueda = em.merge(oldVehiculoOfRuedasRueda);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vehiculo vehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculo persistentVehiculo = em.find(Vehiculo.class, vehiculo.getCodigo());
            Motor motorOld = persistentVehiculo.getMotor();
            Motor motorNew = vehiculo.getMotor();
            List<Rueda> ruedasOld = persistentVehiculo.getRuedas();
            List<Rueda> ruedasNew = vehiculo.getRuedas();
            if (motorNew != null) {
                motorNew = em.getReference(motorNew.getClass(), motorNew.getCodigo());
                vehiculo.setMotor(motorNew);
            }
            List<Rueda> attachedRuedasNew = new ArrayList<Rueda>();
            for (Rueda ruedasNewRuedaToAttach : ruedasNew) {
                ruedasNewRuedaToAttach = em.getReference(ruedasNewRuedaToAttach.getClass(), ruedasNewRuedaToAttach.getCodigo());
                attachedRuedasNew.add(ruedasNewRuedaToAttach);
            }
            ruedasNew = attachedRuedasNew;
            vehiculo.setRuedas(ruedasNew);
            vehiculo = em.merge(vehiculo);
            if (motorOld != null && !motorOld.equals(motorNew)) {
                motorOld.setVehiculo(null);
                motorOld = em.merge(motorOld);
            }
            if (motorNew != null && !motorNew.equals(motorOld)) {
                Vehiculo oldVehiculoOfMotor = motorNew.getVehiculo();
                if (oldVehiculoOfMotor != null) {
                    oldVehiculoOfMotor.setMotor(null);
                    oldVehiculoOfMotor = em.merge(oldVehiculoOfMotor);
                }
                motorNew.setVehiculo(vehiculo);
                motorNew = em.merge(motorNew);
            }
            for (Rueda ruedasOldRueda : ruedasOld) {
                if (!ruedasNew.contains(ruedasOldRueda)) {
                    ruedasOldRueda.setVehiculo(null);
                    ruedasOldRueda = em.merge(ruedasOldRueda);
                }
            }
            for (Rueda ruedasNewRueda : ruedasNew) {
                if (!ruedasOld.contains(ruedasNewRueda)) {
                    Vehiculo oldVehiculoOfRuedasNewRueda = ruedasNewRueda.getVehiculo();
                    ruedasNewRueda.setVehiculo(vehiculo);
                    ruedasNewRueda = em.merge(ruedasNewRueda);
                    if (oldVehiculoOfRuedasNewRueda != null && !oldVehiculoOfRuedasNewRueda.equals(vehiculo)) {
                        oldVehiculoOfRuedasNewRueda.getRuedas().remove(ruedasNewRueda);
                        oldVehiculoOfRuedasNewRueda = em.merge(oldVehiculoOfRuedasNewRueda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = vehiculo.getCodigo();
                if (findVehiculo(id) == null) {
                    throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.");
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
            Vehiculo vehiculo;
            try {
                vehiculo = em.getReference(Vehiculo.class, id);
                vehiculo.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.", enfe);
            }
            Motor motor = vehiculo.getMotor();
            if (motor != null) {
                motor.setVehiculo(null);
                motor = em.merge(motor);
            }
            List<Rueda> ruedas = vehiculo.getRuedas();
            for (Rueda ruedasRueda : ruedas) {
                ruedasRueda.setVehiculo(null);
                ruedasRueda = em.merge(ruedasRueda);
            }
            em.remove(vehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehiculo> findVehiculoEntities() {
        return findVehiculoEntities(true, -1, -1);
    }

    public List<Vehiculo> findVehiculoEntities(int maxResults, int firstResult) {
        return findVehiculoEntities(false, maxResults, firstResult);
    }

    private List<Vehiculo> findVehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vehiculo.class));
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

    public Vehiculo findVehiculo(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vehiculo> rt = cq.from(Vehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

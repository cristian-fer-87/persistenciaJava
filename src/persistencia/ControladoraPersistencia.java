/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author cristian
 */
public abstract class ControladoraPersistencia {
    
    private static EntityManagerFactory emf;
    
    public synchronized EntityManagerFactory getEmf(){
        if(emf==null){
            emf=Persistence.createEntityManagerFactory("ConectandoABDPU");
            
        }
        return emf;
    }
}

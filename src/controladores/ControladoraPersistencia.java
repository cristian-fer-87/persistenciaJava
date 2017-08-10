/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leo
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

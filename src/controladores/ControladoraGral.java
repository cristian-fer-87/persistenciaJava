/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import persistencia.VehiculoJpaController;

/**
 *
 * @author Leo
 */
public class ControladoraGral extends ControladoraPersistencia{
    private VehiculoJpaController vehiculoJPA;
    
    public ControladoraGral(){
        this.vehiculoJPA = new VehiculoJpaController(getEmf());
    }

    public VehiculoJpaController getVehiculoJPA() {
        return vehiculoJPA;
    }

    public void setVehiculoJPA(VehiculoJpaController vehiculoJPA) {
        this.vehiculoJPA = vehiculoJPA;
    }
    
}

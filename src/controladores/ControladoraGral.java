/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import persistencia.ControladoraPersistencia;
import persistencia.EstacionamientoJpaController;
import persistencia.MotorJpaController;
import persistencia.RuedaJpaController;
import persistencia.VehiculoJpaController;

/**
 *
 * @author Leo
 */
public class ControladoraGral extends ControladoraPersistencia{
    private VehiculoJpaController vehiculoJPA;
    private MotorJpaController motorJPA;
    private RuedaJpaController ruedaJpa;
    private EstacionamientoJpaController estacionamientoJPA;
            
    
    public ControladoraGral(){
        this.vehiculoJPA = new VehiculoJpaController(getEmf());
        this.motorJPA = new MotorJpaController(getEmf());
        this.ruedaJpa = new RuedaJpaController(getEmf());
        this.estacionamientoJPA = new EstacionamientoJpaController(getEmf());
    }

    public VehiculoJpaController getVehiculoJPA() {
        return vehiculoJPA;
    }

    public MotorJpaController getMotorJPA() {
        return motorJPA;
    }

    public RuedaJpaController getRuedaJpa() {
        return ruedaJpa;
    }

    public EstacionamientoJpaController getEstacionamientoJPA() {
        return estacionamientoJPA;
    }
    
}

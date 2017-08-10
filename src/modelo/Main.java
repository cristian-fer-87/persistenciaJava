/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controladores.ControladoraGral;
import modelo.Vehiculo;

/**
 *
 * @author Leo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        ControladoraGral contGral = new ControladoraGral();
        //Creo un vehiculo
        Vehiculo ve = new Vehiculo();
        ve.setModelo("fiat2");
        
        //Creo un motor
        Motor motor = new Motor();
        motor.setPotencia("1.8");
        
        //Persisto el Motor
        contGral.getMotorJPA().create(motor);
        //Persisto el vehiculo
        contGral.getVehiculoJPA().create(ve);
        
        //los relaciono
        ve.setMotor(motor);
        
        //Creo dos ruedas
        Rueda ruedaUno = new Rueda();
        ruedaUno.setRodado("15");
        Rueda ruedaDos = new Rueda();
        ruedaDos.setRodado("15");
        
        //Persisto las ruedas
        contGral.getRuedaJpa().create(ruedaUno);
        contGral.getRuedaJpa().create(ruedaDos);
        
        //Vinculo con el vehiculo
        ve.getRuedas().add(ruedaUno);
        ve.getRuedas().add(ruedaDos);
        contGral.getVehiculoJPA().edit(ve);
        
        
        //Creo un estacionamiento
        Estacionamiento estacionamiento = new Estacionamiento();
        estacionamiento.setDireccion("Colón 1983");
        //Persito el estacionamiento
        contGral.getEstacionamientoJPA().create(estacionamiento);
        
        //Agrego un vehiculo a un estacionamiento
        estacionamiento.getVehiculos().add(ve);
        contGral.getEstacionamientoJPA().edit(estacionamiento);
    }
    
}

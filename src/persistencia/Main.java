/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

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
        Vehiculo ve = new Vehiculo();
        ve.setCodigo(1);
        ve.setModelo("fiat");
        contGral.getVehiculoJPA().create(ve);
    }
    
}

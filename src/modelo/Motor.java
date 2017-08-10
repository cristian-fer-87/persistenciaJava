/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author cristian
 */
@Entity
@Table(name="motores")
public class Motor implements Serializable{
    @Id
    @Column(name="codigo")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int codigo;
    
    @Column(name="potencia")
    private String potencia;
    
    @OneToOne(mappedBy = "motor")
    private Vehiculo vehiculo;

    public Motor() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    
}

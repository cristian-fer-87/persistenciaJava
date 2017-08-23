/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author cristian
 */
@Entity
@Table(name="mecanicos")
public class Mecanico extends Persona implements Serializable{
    @Column(name="especialidad")
    private String especialidad;

    public Mecanico() {
    }

    public Mecanico(String especialidad, String nombre) {
        super(nombre);
        this.especialidad = especialidad;
    }


    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
}

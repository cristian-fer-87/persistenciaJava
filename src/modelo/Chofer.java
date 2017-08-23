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
@Table(name="choferes")
public class Chofer extends Persona implements Serializable{
    @Column(name="categoria_carnet")
    private String categoriaCarnet;

    public Chofer() {
    }

    public String getCategoriaCarnet() {
        return categoriaCarnet;
    }

    public void setCategoriaCarnet(String categoriaCarnet) {
        this.categoriaCarnet = categoriaCarnet;
    }
    
}
